package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.ItemProperties;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardItemProperties;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".ItemReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ItemReference extends Reference<Item> {
    private static final ClassArchitect<ItemProperties> ITEM_PROPERTIES_ARCHITECT = ClassArchitect.of(ItemReference.class);

    public static final ItemReference AIR = ItemReference.of(new ResourceLocation("air"));

    private ItemReference(final ResourceLocation id) {
        super(VanillaObjectTypes.ITEM, id);
    }

    @ZenCodeType.Method("of")
    public static ItemReference of(final ResourceLocation id) {
        return new ItemReference(id);
    }

    @ZenCodeType.Getter("properties")
    public StandardItemProperties properties() {
        return this.findProperties(StandardItemProperties.class);
    }

    @ZenCodeType.Method("findProperties")
    public <T extends ItemProperties> T findProperties(final Class<T> reifiedT) {
        return ITEM_PROPERTIES_ARCHITECT.construct(reifiedT, this);
    }
}
