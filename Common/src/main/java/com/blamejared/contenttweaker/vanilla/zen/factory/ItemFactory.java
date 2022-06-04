package com.blamejared.contenttweaker.vanilla.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.item.ItemBuilder;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".ItemFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ItemFactory implements ObjectFactory<Item> {
    private static final ClassArchitect<ItemBuilder<?>> ARCHITECT = ClassArchitect.of(BiFunction.class);
    private static final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> CONVERT_AND_REGISTER = ItemFactory::convertAndRegister;

    public ItemFactory() {}

    private static <T extends Item> ItemReference convertAndRegister(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, resourceProvider));
        return ItemReference.of(holder.id());
    }

    @Override
    public ObjectType<Item> type() {
        return VanillaObjectTypes.ITEM;
    }

    @ZenCodeType.Method("typed")
    public <T extends ItemBuilder<T>> T typed(final Class<T> reifiedT) {
        return ARCHITECT.construct(reifiedT, CONVERT_AND_REGISTER);
    }
}
