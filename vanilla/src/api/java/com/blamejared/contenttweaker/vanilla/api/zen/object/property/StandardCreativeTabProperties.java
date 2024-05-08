package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.VarHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.StandardCreativeTabProperties")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class StandardCreativeTabProperties extends CreativeTabProperties {
    private static final class PropertyReferences {
        static final VarHandle DISPLAY_ITEMS_GENERATOR = Handles.trusted().linkField(
                HandleAccess.directAccess(),
                CreativeModeTab.class,
                Handles.Names.of("field_41037", "f_256824_", "displayItemsGenerator"),
                CreativeModeTab.DisplayItemsGenerator.class
        );

        private PropertyReferences() {}
    }

    private static final class HackyProtectedVisibilityOutputLambdaProxyMaker {
        private record Invoker(
                CreativeTabPropertyFunctions.DisplayItemsGatheringStream stream,
                Method target,
                Function<ItemStack, ItemReference> itemConverter,
                Function<? extends Enum<?>, ItemTabVisibility> visibilityConverter
        ) implements InvocationHandler {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                if (method.isDefault()) {
                    return InvocationHandler.invokeDefault(proxy, method, args);
                }
                if (method == this.target()) {
                    final var reference = this.itemConverter().apply(GenericUtil.uncheck(args[0]));
                    final var visibility = this.visibilityConverter().apply(GenericUtil.uncheck(args[1]));
                    this.stream().accept(reference, visibility);
                    return null;
                }

                final String name = method.getName();
                if (name.equals("toString") && args.length == 0) {
                    return this.stream().toString() + "[Proxied]";
                }
                if (name.equals("equals") && args.length == 1) {
                    return proxy == args[0];
                }
                if (name.equals("hashCode") && args.length == 0) {
                    return System.identityHashCode(proxy);
                }

                throw new NoSuchElementException("Unable to mock " + method.getName() + " with " + args.length + " args");
            }
        }

        private static final Supplier<Method> TARGET_METHOD = Suppliers.memoize(() -> {
            final Class<?> tabVisibility = Util.make(() -> {
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
            return Arrays.stream(CreativeModeTab.Output.class.getDeclaredMethods())
                    .filter(it -> Modifier.isAbstract(it.getModifiers()))
                    .filter(it -> it.getParameterCount() == 2)
                    .filter(it -> it.getParameterTypes()[0] == ItemStack.class)
                    .filter(it -> it.getParameterTypes()[1] == tabVisibility)
                    .filter(it -> it.getReturnType() == void.class)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No accept method found in Output"));
        });

        private HackyProtectedVisibilityOutputLambdaProxyMaker() {}

        static CreativeModeTab.Output create(
                final CreativeTabPropertyFunctions.DisplayItemsGatheringStream stream,
                final Function<ItemStack, ItemReference> itemConverter,
                final Function<? extends Enum<?>, ItemTabVisibility> visibilityConverter
        ) {
            final ClassLoader loader = HackyProtectedVisibilityOutputLambdaProxyMaker.class.getClassLoader();
            final Class<?>[] interfaces = new Class<?>[] { CreativeModeTab.Output.class };
            final InvocationHandler invoker = new Invoker(stream, TARGET_METHOD.get(), itemConverter, visibilityConverter);
            return (CreativeModeTab.Output) Proxy.newProxyInstance(loader, interfaces, invoker);
        }
    }

    public StandardCreativeTabProperties(final CreativeTabReference reference) {
        super(reference, "standard");
    }

    public Component displayName() {
        return this.resolve().getDisplayName();
    }

    public String backgroundSuffix() {
        return this.resolve().getBackgroundSuffix();
    }

    public boolean canScroll() {
        return this.resolve().canScroll();
    }

    public boolean showTitle() {
        return this.resolve().showTitle();
    }

    public boolean alignedRight() {
        return this.resolve().isAlignedRight();
    }

    public CreativeModeTab.Row row() {
        return this.resolve().row();
    }

    public int column() {
        return this.resolve().column();
    }

    public CreativeModeTab.Type tabType() {
        return this.resolve().getType();
    }

    public ItemReference icon() {
        final var stack = this.resolve().getIconItem();
        return this.ref(stack);
    }

    public CreativeTabPropertyFunctions.DisplayItemsGatherer displayItemsGenerator() {
        final CreativeModeTab.DisplayItemsGenerator generator = (CreativeModeTab.DisplayItemsGenerator) PropertyReferences.DISPLAY_ITEMS_GENERATOR.get(this.resolve());
        return (set, hasPermission, provider, stream) -> {
            final CreativeModeTab.ItemDisplayParameters flags = new CreativeModeTab.ItemDisplayParameters(set, hasPermission, GenericUtil.uncheck(provider));
            final CreativeModeTab.Output output = HackyProtectedVisibilityOutputLambdaProxyMaker.create(stream, this::ref, it -> ItemTabVisibility.byIndex(it.ordinal()));
            generator.accept(flags, output);
        };
    }

    private ItemReference ref(final ItemStack stack) {
        final var name = ContentTweakerApi.get().registry().findResolver(VanillaObjectTypes.ITEM).nameOf(stack.getItem());
        return ItemReference.of(name);
    }
}
