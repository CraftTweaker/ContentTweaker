package com.blamejared.contenttweaker.vanilla.api.action.tab;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTabReference;

import java.util.Objects;
import java.util.function.Consumer;

public final class SetCreativeTabIconAction implements ContentTweakerAction {
    private final CreativeTabReference tab;
    private final ItemReference icon;
    private final Consumer<ItemReference> iconSetter;

    private SetCreativeTabIconAction(final CreativeTabReference tab, final ItemReference icon, final Consumer<ItemReference> iconSetter) {
        this.tab = tab;
        this.icon = icon;
        this.iconSetter = iconSetter;
    }

    public static SetCreativeTabIconAction of(final CreativeTabReference tab, final ItemReference icon, final Consumer<ItemReference> iconSetter) {
        return new SetCreativeTabIconAction(Objects.requireNonNull(tab), Objects.requireNonNull(icon), Objects.requireNonNull(iconSetter));
    }

    @Override
    public void apply() {
        this.iconSetter.accept(this.icon);
    }

    @Override
    public String describe() {
        return "Setting icon of tab %s to %s".formatted(this.tab.id(), this.icon.id());
    }
}
