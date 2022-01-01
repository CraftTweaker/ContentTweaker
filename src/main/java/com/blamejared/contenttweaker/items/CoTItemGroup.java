package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.api.functions.IIconSupplier;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashSet;
import java.util.Set;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.ItemGroup")
@Document("mods/contenttweaker/API/item/CustomItemGroup")
public class CoTItemGroup extends ItemGroup {
    private static final Set<String> USED_GROUP_NAMES = new HashSet<>();

    /**
     * Creates a new custom item group. Throws an exception when trying creating two item groups with the same name.
     * @param name the name of the item group
     * @param iconSupplier the function to get the icon of the item group
     * @return the new item group
     *
     * @docParam name "contenttweaker"
     * @docParam iconSupplier () => <item:minecraft:dragon_egg>
     */
    @ZenCodeType.StaticExpansionMethod
    public static ItemGroup create(String name, IIconSupplier iconSupplier) {
        if (USED_GROUP_NAMES.contains(name)) {
            throw new UnsupportedOperationException("cannot create two item groups with the same name");
        }
        USED_GROUP_NAMES.add(name);
        return new CoTItemGroup(name, iconSupplier);
    }

    private IIconSupplier iconSupplier;

    public CoTItemGroup(String label, IIconSupplier iconSupplier) {
        super(label);
        this.iconSupplier = iconSupplier;
    }

    @Override
    public ItemStack createIcon() {
        ItemStack icon = iconSupplier.supply().getInternal();
        iconSupplier = null;
        return icon;
    }
}
