package com.blamejared.contenttweaker.blocks.types.machine.gui;

import mcp.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
@MethodsReturnNonnullByDefault
public class CoTScreen extends ContainerScreen<CoTContainer> {
    
    private final CoTContainer coTContainer;
    
    public CoTScreen(CoTContainer coTContainer, PlayerInventory playerInv, ITextComponent titleIn) {
        super(coTContainer, playerInv, titleIn);
        this.coTContainer = coTContainer;
        this.setSize(175, 183);
    }
    
    @Override
    public CoTContainer getContainer() {
        return coTContainer;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float p_render_3_) {
        this.renderBackground();
        super.render(mouseX, mouseY, p_render_3_);
        renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    }
}
