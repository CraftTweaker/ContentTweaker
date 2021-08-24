package com.blamejared.contenttweaker.wrappers;

import com.blamejared.contenttweaker.api.functions.IIconSupplier;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashSet;
import java.util.Set;

/**
 * An item Group (a.k.a. Creative Tab) is a grouping of items based on category.
 *
 * @docParam this <itemgroup:misc>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.MCItemGroup")
@Document("mods/contenttweaker/API/item/MCItemGroup")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemGroup", displayStringFormat = "%s.getCommandString()")
public class MCItemGroup implements CommandStringDisplayable {
    
    private final ItemGroup internal;
    private static final Set<String> USED_GROUP_NAMES = new HashSet<>();
    
    public MCItemGroup(ItemGroup internal) {
        this.internal = internal;
    }
    
    public ItemGroup getInternal() {
        return internal;
    }

    /**
     * Creates a new item group. Throws an exception when trying creating two item groups with the same name.
     * @param name the name of the item group
     * @param iconSupplier the function to get the icon of the item group
     * @return the new item group
     *
     * @docParam name "contenttweaker"
     * @docParam iconSupplier () => <item:minecraft:dragon_egg>
     */
    @ZenCodeType.Method
    public static MCItemGroup create(String name, IIconSupplier iconSupplier) {
        if (USED_GROUP_NAMES.contains(name)) {
            throw new UnsupportedOperationException("cannot create two item groups with the same name");
        }
        USED_GROUP_NAMES.add(name);
        return new MCItemGroup(new ItemGroup(name) {
            @Override
            public ItemStack createIcon() {
                return iconSupplier.supply().getInternal();
            }
        });
    }
    
    /**
     * Gets the path of the item group.
     * The path is what you use in the Bracket Expression after the `<itemGroup:` part.
     */
    @ZenCodeType.Getter("path")
    @ZenCodeType.Method
    public String getPath() {
        return internal.getPath();
    }
    
    
    /**
     * Sets the image name of the Background that is used for this tab in the creative menu
     *
     * @param texture The texture to be used
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setBackgroundImageName(String texture) {
        final ItemGroup itemGroup = internal.setBackgroundImageName(texture);
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    
    /**
     * Removes the title of the item Group in the creative inventory
     *
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setNoTitle() {
        final ItemGroup itemGroup = internal.setNoTitle();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    /**
     * Removes the scrollbar of the item Group in the creative inventory
     *
     * @return This object for chaining
     */
    @ZenCodeType.Method
    public MCItemGroup setNoScrollbar() {
        final ItemGroup itemGroup = internal.setNoScrollbar();
        return itemGroup == internal ? this : new MCItemGroup(itemGroup);
    }
    
    @Override
    public String toString() {
        return internal.toString();
    }
    
    @Override
    public String getCommandString() {
        return "<itemGroup:" + getPath() + ">";
    }
}
