#loader contenttweaker

import contenttweaker.builder.vanilla.item.Axe;
import contenttweaker.builder.vanilla.item.CustomTool;
import contenttweaker.builder.vanilla.item.Hoe;
import contenttweaker.builder.vanilla.item.Pickaxe;
import contenttweaker.builder.vanilla.item.Shovel;
import contenttweaker.builder.vanilla.item.Sword;

val emerald = <tooltier:contenttweaker:emerald>;

<factory:item>.typed<Axe>() // Let's get started with an axe
    .tier(emerald) // We are using the tier we created before and referenced through a variable
    .baseAttackDamage(6.0f)
    .attackSpeed(-3.1f)
    .build("emerald_axe");

<factory:item>.typed<Hoe>()
    .tier(emerald)
    .baseAttackDamage(-2.0f)
    .attackSpeed(1.0f)
    .build("emerald_hoe");

<factory:item>.typed<Pickaxe>()
    .tier(emerald)
    .baseAttackDamage(1.0f)
    .attackSpeed(-2.8f)
    .build("emerald_pickaxe");

<factory:item>.typed<Shovel>()
    .tier(emerald)
    .baseAttackDamage(1.5f)
    .attackSpeed(-3.0f)
    .build("emerald_shovel");


<factory:item>.typed<Sword>()
    .tier(emerald)
    .baseAttackDamage(3) // IMPORTANT NOTE: Swords only support whole numbers as base attack damage for some reason
    .attackSpeed(-2.4f)
    .build("emerald_sword");

<factory:item>.typed<CustomTool>()
    .tag(<resource:contenttweaker:mineable/chisel>)
    .tier(emerald)
    .baseAttackDamage(2.5f) // It can hurt pretty bad if handled wrongly
    .attackSpeed(0.0f) // 0 speed means that we want to use the base player speed
    .build("emerald_chisel");

// And with this, we have managed to create an entire set of custom tools. Pretty neat, right?
