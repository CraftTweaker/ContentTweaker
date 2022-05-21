package com.blamejared.contenttweaker.vanilla.api.zen.builder;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.ITEM_BUILDER_PACKAGE + ".Basic")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BasicItemBuilder extends ItemBuilder<BasicItemBuilder> {
    public BasicItemBuilder(final BiFunction<ObjectHolder<? extends Item>, Consumer<ResourceManager>, ItemReference> registrationManager) {
        super(registrationManager);
    }

    @Override
    public ObjectHolder<? extends Item> create(final ResourceLocation name, final Supplier<Item.Properties> builtProperties) {
        return ObjectHolder.of(VanillaObjectTypes.ITEM, name, () -> this.build(builtProperties.get()));
    }

    @Override
    public void provideResources(final ResourceLocation name, final ResourceManager manager) {
        manager.fragment(com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS)
                .provide(
                        "models/item/" + name.getPath() + ".json",
                        """
                                {
                                  "parent": "minecraft:item/generated",
                                  "textures": {
                                    "layer0": "minecraft:item/redstone"
                                  }
                                }
                                """,
                        com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers.STRING
                );
        // TODO("")
    }

    private Item build(final Item.Properties properties) {
        return new Item(properties);
    }
}
