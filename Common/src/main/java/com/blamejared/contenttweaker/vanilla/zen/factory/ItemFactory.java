package com.blamejared.contenttweaker.vanilla.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.ItemBuilder;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".ItemFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ItemFactory implements ObjectFactory<Item> {
    private static final ClassArchitect<ItemBuilder<?>> ARCHITECT = ClassArchitect.of(BiFunction.class);
    private static final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> REFERENCE_TO_ITEM_FUNCTION = ItemFactory::convertAndRegister;

    public ItemFactory() {}

    private static <T extends Item> ItemReference convertAndRegister(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        // TODO("Convert to actions")
        Objects.requireNonNull(holder);
        Objects.requireNonNull(resourceProvider);
        RegistryButler.get().register(holder);
        resourceProvider.accept(ResourceManager.get());
        return ItemReference.of(holder.id());
    }

    @Override
    public ObjectType<Item> type() {
        return VanillaObjectTypes.ITEM;
    }

    @ZenCodeType.Method("typed")
    public static <T extends ItemBuilder<T>> T typed(final Class<T> reifiedT) {
        return ARCHITECT.construct(reifiedT, REFERENCE_TO_ITEM_FUNCTION);
    }
}
