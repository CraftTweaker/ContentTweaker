package com.blamejared.contenttweaker.vanilla.api.action.tab;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class CreateCreativeTabAction implements ContentTweakerAction {
    private final String name;
    private final Runnable tabCreator;

    private CreateCreativeTabAction(final String name, final Runnable tabCreator) {
        this.name = name;
        this.tabCreator = tabCreator;
    }

    public static CreateCreativeTabAction of(final String name, final Runnable tabCreator) {
        return new CreateCreativeTabAction(Objects.requireNonNull(name), Objects.requireNonNull(tabCreator));
    }

    @Override
    public void apply() {
        this.tabCreator.run();
    }

    @Override
    public String describe() {
        return "Creating new creative tab with ID " + this.name;
    }

    @Override
    public boolean validate(final Logger logger) {
        return true; // TODO("")
    }
}
