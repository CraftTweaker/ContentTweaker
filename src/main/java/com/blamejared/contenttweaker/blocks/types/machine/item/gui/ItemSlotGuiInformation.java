package com.blamejared.contenttweaker.blocks.types.machine.item.gui;

import java.awt.*;

public class ItemSlotGuiInformation {
    
    private boolean hidden = false;
    private final Point position = new Point(0, 0);
    
    public void hide() {
        this.hidden = true;
    }
    
    public void setPositionX(int x) {
        position.setLocation(x, position.y);
    }
    
    public void setPositionY(int y) {
        position.setLocation(position.x, y);
    }
    
    public Point getPosition() {
        return position;
    }
    
    public boolean isVisible() {
        return !hidden;
    }
}
