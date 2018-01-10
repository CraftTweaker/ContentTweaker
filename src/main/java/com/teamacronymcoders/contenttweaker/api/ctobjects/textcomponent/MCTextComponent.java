package com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class MCTextComponent implements ICTTextComponent {
    private String text;

    public MCTextComponent(String text) {
        this.text = text;
    }

    @Override
    public ITextComponent getInternal() {
        return new TextComponentString(text);
    }
}
