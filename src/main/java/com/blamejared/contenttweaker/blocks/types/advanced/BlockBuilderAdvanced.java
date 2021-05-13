package com.blamejared.contenttweaker.blocks.types.advanced;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.BlockTypeBuilder;
import com.blamejared.contenttweaker.blocks.BlockBuilder;
import com.blamejared.contenttweaker.items.types.advance.CoTItemAdvanced;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A special builder that allows you create items with advanced functionality. This build doesn't provide
 * extra methods. You should use advanced item bracket handler to get {@link CoTItemAdvanced} instance
 * and set functions in CraftTweaker scripts later.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderAdvanced>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.advance.BlockBuilderAdvanced")
@Document("mods/contenttweaker/API/block/advance/BlockBuilderAdvanced")
public class BlockBuilderAdvanced extends BlockTypeBuilder {
    public BlockBuilderAdvanced(BlockBuilder blockBuilder) {
        super(blockBuilder);
    }

    @Override
    public void build(ResourceLocation location) {
        VanillaFactory.queueBlockForRegistration(new CoTBlockAdvanced(blockBuilder.getBlockProperties(), blockBuilder.getItemProperties(), location));
    }
}
