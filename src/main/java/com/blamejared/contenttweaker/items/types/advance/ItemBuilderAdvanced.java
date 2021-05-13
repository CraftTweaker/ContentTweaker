package com.blamejared.contenttweaker.items.types.advance;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.items.ItemTypeBuilder;
import com.blamejared.contenttweaker.items.ItemBuilder;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A special builder that allows you create items with advanced functionality. This build doesn't provide
 * extra methods. You should use advanced item bracket handler to get {@link CoTItemAdvanced} instance
 * and set functions in CraftTweaker scripts later.
 *
 * @docParam this new ItemBuilder().withType<ItemBuilderAdvanced>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.advance.ItemBuilderAdvanced")
@Document("mods/contenttweaker/API/item/advance/ItemBuilderAdvanced")
public class ItemBuilderAdvanced extends ItemTypeBuilder {
    public ItemBuilderAdvanced(ItemBuilder itemBuilder) {
        super(itemBuilder);
    }

    @Override
    public void build(ResourceLocation location) {
        VanillaFactory.queueItemForRegistration(new CoTItemAdvanced(itemBuilder.getItemProperties(), location));
    }
}
