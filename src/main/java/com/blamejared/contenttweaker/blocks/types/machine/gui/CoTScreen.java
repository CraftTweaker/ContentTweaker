package com.blamejared.contenttweaker.blocks.types.machine.gui;

import mcp.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
@MethodsReturnNonnullByDefault
public class CoTScreen extends ContainerScreen<CoTContainer> {
    
    private final CoTContainer coTContainer;
    private final List<RenderCallback> renderCallbacks = new ArrayList<>();
    private final List<RenderCallback> backgroundRenderCallbacks = new ArrayList<>();
    
    public CoTScreen(CoTContainer coTContainer, PlayerInventory playerInv, ITextComponent titleIn) {
        super(coTContainer, playerInv, titleIn);
        this.coTContainer = coTContainer;
    }
    
    public void setPosition(int guiLeft, int guiTop) {
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
    }
    
    @Override
    public CoTContainer getContainer() {
        return coTContainer;
    }
    
    public void addRenderCallBack(RenderCallback callback){
        this.renderCallbacks.add(callback);
    }
    
    public void addBackgroundRenderCallBack(RenderCallback callback){
        this.backgroundRenderCallbacks.add(callback);
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        for(RenderCallback renderCallback : this.renderCallbacks) {
            renderCallback.render(minecraft, mouseX, mouseY, partialTicks);
        }
        renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        for(RenderCallback backgroundRenderCallback : this.backgroundRenderCallbacks) {
            backgroundRenderCallback.render(minecraft, mouseX, mouseY, partialTicks);
        }
    }
}
