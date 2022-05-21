package com.blamejared.contenttweaker.vanilla.api.action.tab;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.contenttweaker.vanilla.api.util.ContentTweakerCreativeTab;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTab;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class SetCreativeTabIconAction implements ContentTweakerAction {
    private final CreativeTab tab;
    private final ItemReference icon;

    private SetCreativeTabIconAction(final CreativeTab tab, final ItemReference icon) {
        this.tab = tab;
        this.icon = icon;
    }

    public static SetCreativeTabIconAction of(final CreativeTab tab, final ItemReference icon) {
        return new SetCreativeTabIconAction(Objects.requireNonNull(tab), Objects.requireNonNull(icon));
    }

    @Override
    public void apply() {
        ((ContentTweakerCreativeTab) this.tab.unwrap()).setDisplayItem(this.icon);
    }

    @Override
    public String describe() {
        return "Setting icon of tab %s to %s".formatted(this.tab, this.icon.id());
    }

    @Override
    public boolean validate(final Logger logger) {
        if (!(this.tab.unwrap() instanceof ContentTweakerCreativeTab)) {
            logger.error("Creative tab " + this.tab + " is not customizable");
            return false;
        }
        return true;
    }
}
