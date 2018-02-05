package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;

public class CreativeTabRepresentation implements IRepresentation<CreativeTabs>, ICreativeTab {
    private String unlocalizedName;
    private ItemStack iconStack;
    private CreativeTabContent creativeTabContent;

    private boolean hasSearch = false;
    private String backGroundImage = "";
    private boolean noScrollBar = true;
    private boolean noTitle = false;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public IItemStack getIconStack() {
        return new MCItemStack(iconStack);
    }

    public ItemStack getInternalIconStack() {
        return this.iconStack;
    }

    @Override
    public void setIconStack(IItemStack iconStack) {
        if(iconStack.getInternal() instanceof ItemStack) {
            this.setIconStack((ItemStack) iconStack.getInternal());
        } else {
            CraftTweakerAPI.logError("Could not get ItemStack");
        }
    }

    @Override
    public void setHasSearch() {
        this.hasSearch = true;
    }

    public void setIconStack(ItemStack iconStack) {
        this.iconStack = iconStack;
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "Creative Tab";
    }

    @Override
    public void register() {
        if (creativeTabContent == null) {
            creativeTabContent = new CreativeTabContent(this);
        }
    }

    @Override
    public void setBackgroundImageName(String backgroundImage) {
        this.backGroundImage = backgroundImage;
    }

    @Override
    public int getSearchBarWidth() {
        return 0;
    }

    @Override
    public String getTabLabel() {
        return this.getUnlocalizedName();
    }

    @Override
    public void setNoScrollBar() {
        this.noScrollBar = true;
    }

    @Override
    public void setNoTitle() {
        this.noTitle = true;
    }

    @Override
    public CreativeTabs getInternal() {
        if (creativeTabContent == null) {
            creativeTabContent = new CreativeTabContent(this);
        }
        return creativeTabContent;
    }

    public boolean isHasSearch() {
        return hasSearch;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }

    public boolean isNoScrollBar() {
        return noScrollBar;
    }

    public boolean isNoTitle() {
        return noTitle;
    }
}
