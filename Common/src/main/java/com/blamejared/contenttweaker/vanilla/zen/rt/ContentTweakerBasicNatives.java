package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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

    @NativeTypeRegistration(value = Item.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Item")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class ItemNative {
        private ItemNative() {}
    }

    @NativeTypeRegistration(value = Material.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Material")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class MaterialNative {
        private MaterialNative() {}
    }

    @NativeTypeRegistration(value = MaterialColor.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".MaterialColor")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class MaterialColorNative {
        private MaterialColorNative() {}
    }

    @BracketEnum("minecraft:material/pushreaction")
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

