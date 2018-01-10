package com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MCTextComponentTranslation implements ICTTextComponent {
    private String text;

    public MCTextComponentTranslation(String text) {
        this.text = text;
    }

    @Override
    public ITextComponent getInternal() {
        return new TextComponentTranslation(text);
    }
}
