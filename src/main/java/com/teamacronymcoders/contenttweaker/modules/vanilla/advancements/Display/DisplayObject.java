package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.display;

import com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent.ICTTextComponent;
import crafttweaker.api.item.IItemStack;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.contenttweaker.DisplayObject")
public class DisplayObject {
    @ZenProperty
    private ICTTextComponent title;
    @ZenProperty
    private ICTTextComponent description;
    @ZenProperty
    private IItemStack icon;
    @ZenProperty
    private ResourceLocation background;
    @ZenProperty
    private FrameType frame;
    @ZenProperty
    private boolean showToast;
    @ZenProperty
    private boolean announceToChat;
    @ZenProperty
    private boolean hidden;

    public DisplayObject(ICTTextComponent title, IItemStack icon){
        this.title = title;
        this.icon = icon;
    }

    @ZenMethod
    public FrameType getFrame() {
        return frame;
    }

    @ZenMethod
    public void setFrame(FrameType frame) {
        this.frame = frame;
    }

    @ZenMethod
    public void setTitle(ICTTextComponent title) {
        this.title = title;
    }

    @ZenMethod
    public ICTTextComponent getTitle() {
        return title;
    }

    @ZenMethod
    public void setDescription(ICTTextComponent description) {
        this.description = description;
    }

    @ZenMethod
    public ICTTextComponent getDescription() {
        return description;
    }

    @ZenMethod
    public void setIcon(IItemStack icon) {
        this.icon = icon;
    }

    @ZenMethod
    public IItemStack getIcon() {
        return icon;
    }

    @ZenMethod
    public void setBackground(ResourceLocation background) {
        this.background = background;
    }

    @ZenMethod
    public ResourceLocation getBackground() {
        return background;
    }

    @ZenMethod
    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    @ZenMethod
    public boolean isShowToast() {
        return showToast;
    }

    @ZenMethod
    public void setAnnounceToChat(boolean announceToChat) {
        this.announceToChat = announceToChat;
    }

    @ZenMethod
    public boolean isAnnounceToChat() {
        return announceToChat;
    }

    @ZenMethod
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @ZenMethod
    public boolean isHidden() {
        return hidden;
    }
}
