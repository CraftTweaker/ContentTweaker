package com.blamejared.contenttweaker.vanilla.api.zen.builder.tab;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.CreativeTabProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.CreativeTabPropertyFunctions;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardCreativeTabProperties;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@ZenCodeType.Name(ContentTweakerVanillaConstants.TAB_BUILDER_PACKAGE + ".CreativeTabBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class CreativeTabBuilder<T extends CreativeTabBuilder<T>> {
    protected record GenerateFlags(Position position) {
        public record Position(CreativeModeTab.Row row, int column) {}

        public boolean placeAutomatically() {
            return this.position() == null;
        }
    }

    protected record CloningPropertiesReference<P extends CreativeTabProperties>(P properties) {
        <V> V resolve(final Function<P, V> getter) {
            final P properties = this.properties();
            return properties == null? null : getter.apply(properties);
        }
    }

    protected record BuilderPropertyApplier<B extends CreativeModeTab.Builder, R extends CreativeTabProperties>(B builder, CloningPropertiesReference<R> properties) {
        BuilderPropertyApplier<B, R> applyBoolean(
                final Boolean property,
                final Function<R, Boolean> alternativeGetter,
                final boolean target,
                final Function<B, ? extends CreativeModeTab.Builder> applierIfTarget
        ) {
            return this.apply(property, alternativeGetter, (b, v) -> v == Boolean.valueOf(target)? applierIfTarget.apply(b) : b);
        }

        <P> BuilderPropertyApplier<B, R> apply(
                final P property,
                final Function<R, P> alternativeGetter,
                final BiFunction<B, P, ? extends CreativeModeTab.Builder> builderApplier
        ) {
            return this.apply(property, alternativeGetter, builderApplier, Function.identity());
        }

        <P, V> BuilderPropertyApplier<B, R> apply(
                final P property,
                final Function<R, P> alternativeGetter,
                final BiFunction<B, V, ? extends CreativeModeTab.Builder> builderApplier,
                final Function<P, V> toMinecraftConverter
        ) {
            final P target = this.resolve(property, alternativeGetter);
            final B newBuilder = this.applyToBuilder(builderApplier, toMinecraftConverter.apply(target));
            return new BuilderPropertyApplier<>(newBuilder, this.properties());
        }

        private <P> P resolve(final P property, final Function<R, P> alternativeGetter) {
            return property != null? property : this.properties().resolve(alternativeGetter);
        }

        private <V> B applyToBuilder(final BiFunction<B, V, ? extends CreativeModeTab.Builder> builderApplier, final V value) {
            final B oldBuilder = this.builder();
            final CreativeModeTab.Builder newBuilder = builderApplier.apply(oldBuilder, value);
            return oldBuilder == newBuilder? oldBuilder : this.castBuilder(oldBuilder, newBuilder);
        }

        @SuppressWarnings("unchecked")
        private <C extends CreativeModeTab.Builder> B castBuilder(final B oldBuilder, final C newBuilder) {
            final Class<? extends CreativeModeTab.Builder> bClass = oldBuilder.getClass();
            final Class<? extends CreativeModeTab.Builder> cClass = newBuilder.getClass();

            if (bClass != cClass) {
                if (!bClass.isAssignableFrom(cClass)) {
                    final String bName = bClass.getName();
                    final String cName = cClass.getName();
                    throw new IllegalStateException("Unable to recursively apply properties to builder: typing lost from " + bName + " to " + cName);
                }
            }

            // Either the same or C can be assigned to B, so safely cast
            return (B) bClass.cast(newBuilder);
        }
    }

    private static final class BuilderHandles {
        private static final MethodHandle TYPE = Handles.trusted().linkMethod(
                HandleAccess.virtualAccess(),
                CreativeModeTab.Builder.class,
                Handles.Names.of("method_47318", "m_257623_", "type"),
                CreativeModeTab.Builder.class,
                CreativeModeTab.Type.class
        );

        private BuilderHandles() {}

        static <B extends CreativeModeTab.Builder> CreativeModeTab.Builder type(final B builder, final CreativeModeTab.Type type) {
            return Handles.invoke(() -> (CreativeModeTab.Builder) TYPE.invokeExact((CreativeModeTab.Builder) builder, type));
        }
    }

    private static final class AncillaryHandles {
        private static final Class<?> TAB_VISIBILITY_ENUM = Util.make(() -> {
            final String baseName = CreativeModeTab.class.getName() + '$';
            final String packageName = CreativeModeTab.class.getPackageName() + '.';
            final String[] names = new String[]{
                    baseName + "TabVisibility",
                    baseName + "class_7705",
                    baseName + "C_243419_",
                    packageName + "class_7705",
                    packageName + "C_243419_"
            };

            for (final String name : names) {
                try {
                    return Class.forName(name);
                } catch (final ClassNotFoundException ignored) {}
            }

            throw new RuntimeException("Unable to find visibility class");
        });

        private static final MethodHandle TAB_VISIBILITY_VALUES = Util.make(() -> {
            final MethodHandle basicHandle = Handles.publicOnly().linkMethod(HandleAccess.staticAccess(), TAB_VISIBILITY_ENUM, "values", TAB_VISIBILITY_ENUM.arrayType());
            return basicHandle.asType(MethodType.methodType(Object.class));
        });

        private static final MethodHandle OUTPUT_ACCEPT = Util.make(() -> {
            final Method target = Arrays.stream(CreativeModeTab.Output.class.getDeclaredMethods())
                    .filter(it -> Modifier.isAbstract(it.getModifiers()))
                    .filter(it -> it.getParameterCount() == 2)
                    .filter(it -> it.getReturnType() == void.class)
                    .filter(it -> it.getParameterTypes()[0] == ItemStack.class)
                    .filter(it -> it.getParameterTypes()[1] == TAB_VISIBILITY_ENUM)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to find functional method for functional interface"));
            return Handles.publicOnly().linkMethod(HandleAccess.virtualAccess(), CreativeModeTab.Output.class, target.getName(), target.getReturnType(), target.getParameterTypes());
        });

        private AncillaryHandles() {}

        static <T> T visibility(final int ordinal) {
            final T[] values = GenericUtil.uncheck(Handles.invoke(() -> (Object) TAB_VISIBILITY_VALUES.invokeExact()));
            return values[ordinal];
        }

        static <T> void accept(final CreativeModeTab.Output output, final ItemStack stack, final T visibility) {
            Handles.invokeVoid(() -> OUTPUT_ACCEPT.invoke(output, stack, visibility));
        }
    }

    private final BiFunction<ObjectHolder<? extends CreativeModeTab>, Consumer<ResourceManager>, CreativeTabReference> registrationHandler;

    private StandardCreativeTabProperties cloningProperties;

    private String title; // TODO("Move to Component")
    private String backgroundSuffix;
    private Boolean canScroll;
    private Boolean showTitle;
    private Boolean alignedRight;
    private CreativeModeTab.Row row;
    private Integer column;
    private CreativeModeTab.Type type;
    private ItemReference icon; // TODO("ItemStack?")
    private CreativeTabPropertyFunctions.DisplayItemsGatherer displayItems;

    // Automatic placement requires (row = null, column = -1) on Fabric and (row = TOP, column = 0) on Forge
    private boolean autoPlace;

    protected CreativeTabBuilder(final BiFunction<ObjectHolder<? extends CreativeModeTab>, Consumer<ResourceManager>, CreativeTabReference> registrationHandler) {
        this.registrationHandler = registrationHandler;
        this.cloningProperties = null;
        this.title = null;
        this.backgroundSuffix = "items.png"; //TODO remove hardcoding
        this.canScroll = null;
        this.showTitle = null;
        this.alignedRight = null;
        this.row = null;
        this.column = null;
        this.type = CreativeModeTab.Type.CATEGORY; //TODO remove hardcoding
        this.icon = null;
        this.displayItems = null;
        this.autoPlace = true;
    }

    @ZenCodeType.Method("cloning")
    public T cloning(final CreativeTabProperties properties) {
        Objects.requireNonNull(properties);
        final StandardCreativeTabProperties standard = "standard".equals(properties.type())? (StandardCreativeTabProperties) properties : null;
        if (standard == null) {
            throw new IllegalArgumentException("Unknown set of properties " + properties.type() + " to clone from");
        }
        if (this.cloningProperties != null) {
            throw new IllegalStateException("Already specified properties to clone from");
        }
        this.cloningProperties = standard;
        return this.self();
    }

    @ZenCodeType.Method("title")
    public T title(final String title) {
        this.title = title;
        return this.self();
    }

    @ZenCodeType.Method("backgroundSuffix")
    public T backgroundSuffix(final String backgroundSuffix) {
        this.backgroundSuffix = backgroundSuffix;
        return this.self();
    }

    @ZenCodeType.Method("canScroll")
    public T canScroll(final boolean canScroll) {
        this.canScroll = canScroll;
        return this.self();
    }

    @ZenCodeType.Method("allowScrolling")
    public T allowScrolling() {
        return this.canScroll(true);
    }

    @ZenCodeType.Method("noScrolling")
    public T noScrolling() {
        return this.canScroll(false);
    }

    @ZenCodeType.Method("showTitle")
    public T showTitle(final boolean showTitle) {
        this.showTitle = showTitle;
        return this.self();
    }

    @ZenCodeType.Method("showTitle")
    public T showTitle() {
        return this.showTitle(true);
    }

    @ZenCodeType.Method("hideTitle")
    public T hideTitle() {
        return this.showTitle(false);
    }

    @ZenCodeType.Method("alignedRight")
    public T alignedRight(final boolean alignedRight) {
        this.alignedRight = alignedRight;
        return this.self();
    }

    @ZenCodeType.Method("alignRight")
    public T alignRight() {
        return this.alignedRight(true);
    }

    @ZenCodeType.Method("row")
    public T row(final CreativeModeTab.Row row) {
        this.row = row;
        return this.self();
    }

    @ZenCodeType.Method("column")
    public T column(final int column) {
        // No negative check: some negative values might be special cases (e.g. -1 in Fabric)
        this.column = column;
        return this.self();
    }

    @ZenCodeType.Method("type")
    public T type(final CreativeModeTab.Type type) {
        this.type = type;
        return this.self();
    }

    @ZenCodeType.Method("icon")
    public T icon(final ItemReference reference) {
        this.icon = reference;
        return this.self();
    }

    @ZenCodeType.Method("displayItems")
    public T displayItems(final CreativeTabPropertyFunctions.DisplayItemsGatherer gatherer) {
        this.displayItems = gatherer;
        return this.self();
    }

    @ZenCodeType.Method("display")
    public T appendItem(final ItemReference item, final CreativeTabProperties.ItemTabVisibility visibility) {
        final CreativeTabPropertyFunctions.DisplayItemsGatherer gatherer = Objects.requireNonNullElseGet(this.displayItems, () -> (a, b, c, d) -> {});
        return this.displayItems((a, b, c, d) -> {
            gatherer.gather(a, b, c, d);
            d.accept(item, visibility);
        });
    }

    @ZenCodeType.Method("display")
    public T appendItem(final ItemReference item) {
        return this.appendItem(item, CreativeTabProperties.ItemTabVisibility.PARENT_AND_SEARCH);
    }

    @ZenCodeType.Method("placeAutomatically")
    public T autoPlace(final boolean autoPlace) {
        this.autoPlace = autoPlace;
        return this.self();
    }

    @ZenCodeType.Method("placeAutomatically")
    public T placeAutomatically() {
        return this.autoPlace(true);
    }

    @ZenCodeType.Method("build")
    public final CreativeTabReference build(final String name) {
        this.verifyProperties();
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final GenerateFlags flags = this.flags();
        return this.registrationHandler.apply(this.create(id, flags), manager -> this.provideResources(id, flags, manager));
    }

    protected void verifyProperties() {
        if (this.autoPlace && (this.row != null || this.column != null)) {
            throw new IllegalStateException("Row and column cannot be set if automatic placement is enabled");
        }
        if (!this.autoPlace && (this.row == null || this.column == null)) {
            throw new IllegalStateException("Automatic placement is disabled, but no coordinates are provided");
        }
        if (this.title == null && this.cloningProperties == null) {
            throw new IllegalStateException("Title for the creative tab must be provided");
        }
        if (this.displayItems == null && this.cloningProperties == null) {
            throw new IllegalStateException("Creative tab must contain at least one item to be visible");
        }
        if (this.icon == null && this.cloningProperties == null) {
            throw new IllegalStateException("Creative tab must have an icon");
        }
    }

    protected abstract ObjectHolder<? extends CreativeModeTab> create(final ResourceLocation name, final GenerateFlags flags);

    protected abstract void provideResources(final ResourceLocation name, final GenerateFlags flags, final ResourceManager manager);

    protected final <B extends CreativeModeTab.Builder> B fillBuilder(final B builder) {
        return new BuilderPropertyApplier<>(Objects.requireNonNull(builder), new CloningPropertiesReference<>(this.cloningProperties))
                .apply(this.castProperty(this.title, Component::translatableWithFallback), StandardCreativeTabProperties::displayName, CreativeModeTab.Builder::title)
                .apply(this.backgroundSuffix, StandardCreativeTabProperties::backgroundSuffix, CreativeModeTab.Builder::backgroundSuffix)
                .applyBoolean(this.canScroll, StandardCreativeTabProperties::canScroll, false, CreativeModeTab.Builder::noScrollBar)
                .applyBoolean(this.showTitle, StandardCreativeTabProperties::showTitle, false, CreativeModeTab.Builder::hideTitle)
                .applyBoolean(this.alignedRight, StandardCreativeTabProperties::alignedRight, true, CreativeModeTab.Builder::alignedRight)
                .apply(this.type, StandardCreativeTabProperties::tabType, BuilderHandles::type)
                .apply(this.icon, StandardCreativeTabProperties::icon, CreativeModeTab.Builder::icon, ref -> () -> BuiltInRegistries.ITEM.get(ref.id()).getDefaultInstance())
                .apply(this.displayItems, StandardCreativeTabProperties::displayItemsGenerator, CreativeModeTab.Builder::displayItems, this::toItemGenerator)
                .builder();
    }

    private GenerateFlags flags() {
        return new GenerateFlags(
                this.autoPlace? null : new GenerateFlags.Position(this.row, this.column)
        );
    }

    private <C, V> V castProperty(final C property, final BiFunction<C, C, V> caster) {
        return this.castProperty(property, it -> caster.apply(it, it));
    }

    private <C, V> V castProperty(final C property, final Function<C, V> caster) {
        return property == null? null : caster.apply(property);
    }

    private CreativeModeTab.DisplayItemsGenerator toItemGenerator(final CreativeTabPropertyFunctions.DisplayItemsGatherer gatherer) {
        return (itemDisplayParameters, output) -> gatherer.gather(
                itemDisplayParameters.enabledFeatures(),
                itemDisplayParameters.hasPermissions(),
                itemDisplayParameters.holders(),
                (item, visibility) -> AncillaryHandles.accept(output, item.get().getDefaultInstance(), AncillaryHandles.visibility(visibility.ordinal()))
        );
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

}
