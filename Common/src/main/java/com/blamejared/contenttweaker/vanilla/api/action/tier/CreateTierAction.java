package com.blamejared.contenttweaker.vanilla.api.action.tier;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.contenttweaker.vanilla.api.zen.util.TierReference;

import java.util.Objects;

public final class CreateTierAction implements ContentTweakerAction {
    private final TierReference reference;
    private final Runnable creationAction;

    private CreateTierAction(final TierReference reference, final Runnable creationAction) {
        this.reference = reference;
        this.creationAction = creationAction;
    }

    public static CreateTierAction of(final TierReference reference, final Runnable creationAction) {
        return new CreateTierAction(Objects.requireNonNull(reference), Objects.requireNonNull(creationAction));
    }

    @Override
    public void apply() {
        this.creationAction.run();
    }

    @Override
    public String describe() {
        return "Creating a new tier named %s%s".formatted(this.reference.name(), this.reference.level() < 0? "" : " with level " + this.reference.level());
    }
}
