package com.teamacronymcoders.contenttweaker.modules.chickens;

import com.google.common.collect.Lists;
import com.setycz.chickens.handler.SpawnType;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenRegister
@ModOnly("chickens")
@ZenClass("mods.contenttweaker.ChickenFactory")
public class ChickenFactory {
    public static final List<ChickenRepresentation> CHICKEN_REPRESENTATIONS = Lists.newArrayList();

    @ZenMethod
    public static ChickenRepresentation createChicken(String name, CTColor color, IItemStack iItemStack) {
        return new ChickenRepresentation(name, color, iItemStack);
    }

    public static void registerChickens() {
        for (ChickenRepresentation chickenRepresentation : CHICKEN_REPRESENTATIONS) {
            ChickensRegistryItem parentOneItem = chickenRepresentation.parentOne != null ?
                    ChickensRegistry.getByResourceLocation(chickenRepresentation.parentOne.getInternal()) : null;
            ChickensRegistryItem parentTwoItem = chickenRepresentation.parentTwo != null ?
                    ChickensRegistry.getByResourceLocation(chickenRepresentation.parentTwo.getInternal()) : null;

            ChickensRegistryItem item = new ChickensRegistryItem(
                    new ResourceLocation(ContentTweaker.MOD_ID, chickenRepresentation.name),
                    chickenRepresentation.name,
                    chickenRepresentation.textureLocation.getInternal(),
                    CraftTweakerMC.getItemStack(chickenRepresentation.layItem),
                    chickenRepresentation.backgroundColor.getIntColor(),
                    chickenRepresentation.foregroundColor.getIntColor(),
                    parentOneItem,
                    parentTwoItem
            );

            if (chickenRepresentation.dropItem != null) {
                item.setDropItem(CraftTweakerMC.getItemStack(chickenRepresentation.dropItem));
            }

            if (chickenRepresentation.spawnType != null) {
                SpawnType actualSpawnType = null;
                for (SpawnType spawnTypeEnum : SpawnType.values()) {
                    if (spawnTypeEnum.toString().toLowerCase().equals(chickenRepresentation.spawnType.toLowerCase())) {
                        actualSpawnType = spawnTypeEnum;
                    }
                }
                if (actualSpawnType != null) {
                    item.setSpawnType(actualSpawnType);
                } else {
                    CraftTweakerAPI.logError("Failed to find SpawnType for String: " + chickenRepresentation.spawnType);
                }
            }

            item.setLayCoefficient(chickenRepresentation.layCoefficient);

            ChickensRegistry.register(item);
        }

    }
}
