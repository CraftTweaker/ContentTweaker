package com.blamejared.contenttweaker.blocks.types.machine.gui.capability.builder;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

import java.util.*;

public class GuiGuiInformation {
    
    private final int indentX = 0;
    private final int indentY = 0;
    private int height = 183;
    private int width = 172;
    private int posX;
    private int posY;
    private MCResourceLocation backgroundLocation;
    
    void setWidth(int width) {
        this.width = width;
    }
    
    void setHeight(int height) {
        this.height = height;
    }
    
    void setPosX(int posX) {
        this.posX = posX;
    }
    
    void setPosY(int posY) {
        this.posY = posY;
    }
    
    void setBackgroundLocation(MCResourceLocation backgroundLocation) {
        this.backgroundLocation = backgroundLocation;
    }
    
    public void addToScreen(CoTScreen screen) {
        screen.setSize(width, height);
        final String path = "textures/gui/" + backgroundLocation.getPath() + ".png";
        final ResourceLocation textureLocation = new ResourceLocation(backgroundLocation.getNamespace(), path);
        screen.addBackgroundRenderCallBack((minecraft, mouseX, mouseY, partialTicks) -> {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            final TextureManager textureManager = minecraft.getTextureManager();
            textureManager.bindTexture(textureLocation);
            final int blitPosX = screen.getGuiLeft() + posX;
            final int blitPosY = screen.getGuiTop() + posY;
            screen.blit(blitPosX, blitPosY, indentX, indentY, width, height);
        });
    }
    
    public Collection<WriteableResource> getResourcePackResources(MCResourceLocation blockName) {
        final WriteableResourceImage image;
        if(backgroundLocation != null) {
            image = new WriteableResourceImage(ImageType.GUI, backgroundLocation);
        }else {
            image = new WriteableResourceImage(ImageType.GUI, blockName);
        }
        return Collections.singleton(image);
    
    }
}
