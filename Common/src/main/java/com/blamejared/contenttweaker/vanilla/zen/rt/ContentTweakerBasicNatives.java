package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ContentTweakerBasicNatives {

    @NativeTypeRegistration(value = Item.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".Item")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class ItemNative {
        private ItemNative() {}
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

    private ContentTweakerBasicNatives() {}
}

