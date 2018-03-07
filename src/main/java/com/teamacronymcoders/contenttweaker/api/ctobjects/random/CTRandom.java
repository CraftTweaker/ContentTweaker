package com.teamacronymcoders.contenttweaker.api.ctobjects.random;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Random;

@ZenRegister
@ZenClass("mods.contenttweaker.Random")
public class CTRandom {
    private Random random;

    public CTRandom(Random random) {
        this.random = random;
    }

    @ZenMethod
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @ZenMethod
    public int nextInt(int upperBound) {
        return random.nextInt(upperBound);
    }
}
