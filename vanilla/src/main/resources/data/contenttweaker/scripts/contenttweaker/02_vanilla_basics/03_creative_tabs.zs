#loader contenttweaker

#onlyIf modloader fabric
import contenttweaker.builder.fabric.tab.Regular;
#endif
#onlyIf modloader forge
import contenttweaker.builder.forge.tab.Regular;
#endif

import contenttweaker.builder.vanilla.tab.CreativeTabBuilder;

val notEmptyTab = <factory:minecraft:creative_mode_tab>
    .typed<Regular>()
    .title("AAAAAA")
    .icon(<item:minecraft:air>)
    .display(<item:minecraft:apple>)
    .build("empty_tab");

val weirdTab = <factory:minecraft:creative_mode_tab>
    .typed<Regular>()
    .cloning(<reference:minecraft:creative_mode_tab:minecraft:spawn_eggs>.properties)
    .build("food_drinks_copy");

val customTab = <factory:minecraft:creative_mode_tab>
    .typed<Regular>()
    .title("My Custom Tab")
    .icon(<reference:item:minecraft:tnt>)
    .display(<reference:item:minecraft:gunpowder>)
    .display(<reference:item:minecraft:sand>)
    .placeAutomatically()
    .build("tnt_components");


