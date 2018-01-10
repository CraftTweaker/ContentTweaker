package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.Display;

import com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent.ICTTextComponent;
import crafttweaker.api.item.IItemStack;
import net.minecraft.advancements.FrameType;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
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


    public FrameType getFrame() {
        return frame;
    }

    public void setFrame(FrameType frame) {
        this.frame = frame;
    }

    public void setTitle(ICTTextComponent title) {
        this.title = title;
    }

    public ICTTextComponent getTitle() {
        return title;
    }

    public void setDescription(ICTTextComponent description) {
        this.description = description;
    }

    public ICTTextComponent getDescription() {
        return description;
    }

    public void setIcon(IItemStack icon) {
        this.icon = icon;
    }

    public IItemStack getIcon() {
        return icon;
    }

    public void setBackground(ResourceLocation background) {
        this.background = background;
    }

    public ResourceLocation getBackground() {
        return background;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setAnnounceToChat(boolean announceToChat) {
        this.announceToChat = announceToChat;
    }

    public boolean isAnnounceToChat() {
        return announceToChat;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }
}
