package com.blamejared.contenttweaker.blocks.types.machine.item.gui;

import java.awt.*;

public class ItemSlotGuiInformation {
    
    private boolean hidden = false;
    private Point position = new Point(0, 0);
    private int color = 0x00000000;
    
    public void hide() {
        this.hidden = true;
    }
    
    public void setPositionX(int x) {
        position.setLocation(x, position.y);
    }
    
    public void setPositionY(int y) {
        position.setLocation(position.x, y);
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public void draw() {
        if(hidden) {
            return;
        }
    
        System.out.println("Drawing");
    }
}
