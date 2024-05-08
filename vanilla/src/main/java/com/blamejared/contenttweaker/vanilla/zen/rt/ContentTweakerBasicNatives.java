package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public final class ContentTweakerBasicNatives {

    @NativeTypeRegistration(value = Block.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Block")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class BlockNative {
        private BlockNative() {}
    }

    @NativeTypeRegistration(value = CreativeModeTab.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".CreativeTab")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class CreativeTabNative {
        private CreativeTabNative() {}
    }

    @BracketEnum("minecraft:creativetab/row")
    @NativeTypeRegistration(value = CreativeModeTab.Row.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".CreativeTabRow")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class CreativeTabRowNative {
        private CreativeTabRowNative() {}
    }

    @BracketEnum("minecraft:creativetab/type")
    @NativeTypeRegistration(value = CreativeModeTab.Type.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".CreativeTabType")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class CreativeTabTypeNative {
        private CreativeTabTypeNative() {}
    }

    @NativeTypeRegistration(value = EntityType.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".EntityType")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class EntityTypeNative {
        private EntityTypeNative() {}
        // TODO("Maybe extract?")
    }

    @NativeTypeRegistration(value = FeatureFlagSet.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".FeatureFlagSet")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class FeatureFlagSetNative {
        private FeatureFlagSetNative() {}
    }

    @NativeTypeRegistration(value = Item.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Item")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class ItemNative {
        private ItemNative() {}
    }

    @BracketEnum("minecraft:block/noteblockinstrument")
    @NativeTypeRegistration(value = NoteBlockInstrument.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".NoteBlockInstrument")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class NoteBlockInstrumentNative {
        private NoteBlockInstrumentNative() {}
    }

    @BracketEnum("minecraft:block/offsettype")
    @NativeTypeRegistration(value = BlockBehaviour.OffsetType.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".OffsetType")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class OffsetTypeNative {
        private OffsetTypeNative() {}
    }

    @BracketEnum("minecraft:block/pushreaction")
    @NativeTypeRegistration(value = PushReaction.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".PushReaction")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class PushReactionNative {
        private PushReactionNative() {}
    }

    @BracketEnum("minecraft:item/rarity")
    @NativeTypeRegistration(value = Rarity.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".ItemRarity")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class RarityNative {
        private RarityNative() {}
    }

    @NativeTypeRegistration(value = SoundEvent.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".SoundEvent")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class SoundEventNative {
        private SoundEventNative() {}
    }

    @NativeTypeRegistration(value = SoundType.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".SoundType")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class SoundTypeNative {
        private SoundTypeNative() {}
    }

    @NativeTypeRegistration(value = Tier.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Tier")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class TierNative {
        private TierNative() {}
    }

    private ContentTweakerBasicNatives() {}
}

