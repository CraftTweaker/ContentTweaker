#loader contenttweaker

/**********************************************************************************************************************/
/*                                                       TOOLS                                                        */
/**********************************************************************************************************************/

// After that brief dip into loader-specific classes, we're back into common territory, and we'll be taking a look at
// tools. Tools are another type of item that ContentTweaker allows you to create, in many different fashions.
// ContentTweaker supports in fact all tool types that vanilla provides (axes, pickaxes, shovels, and hoes), but it also
// allows you to create your completely custom tool. As you might have noticed, the previous list is missing swords.
// That is because the game does not consider swords tools, but we have decided to put them in this example script
// regardless, as the community considers swords as a tool. For this reason, most of what we will discuss in this
// instance applies to swords too.

// Without further ado, let's discuss what makes sure that the game considers an item a tool. In this discussion we will
// leave out swords.
// The game considers a tool any item that has a tool tier and a tag referencing which blocks the tool is effective
// against. For example, a diamond pickaxe is a tool as it has a tool tier of "diamond" and it is effective against
// blocks in the tag `minecraft:mineable/pickaxe`. Any tool also specifies two additional pieces of information, namely
// the base attack damage caused by the tool and the attack speed, which are combined with information from the tool
// tier to build the effective tool properties.
// This in turn means that to create a custom tool, it is necessary to only specify these four properties: the tier, the
// base attack damage, the attack speed, and the tag. The builtin tool types avoid having to specify the tag as the game
// already knows which tag to look at for pickaxes, axes, etc.

// The same can also be said for swords, except that swords are not tied to a tag as they are not able to mine blocks
// effectively in vanilla, being simply used for attacking.

// Now that we have explained how tools work, let's try to create a complete tool set for the emerald tier we have
// created previously. To simplify, we will use the same values used by the iron tool-set for base attack damage and
// speed. We will also create a custom tool called "chisel".
// First things first, let's import the set of types we will be using in this script. Again, this is not mandatory and
// is mostly used for convenience

import contenttweaker.builder.vanilla.item.Axe;
import contenttweaker.builder.vanilla.item.CustomTool;
import contenttweaker.builder.vanilla.item.Hoe;
import contenttweaker.builder.vanilla.item.Pickaxe;
import contenttweaker.builder.vanilla.item.Shovel;
import contenttweaker.builder.vanilla.item.Sword;

// Let's also reference the emerald tier and the creative tab we have created in the previous scripts, so we do not have
// to type too much boilerplate code.

val examples = <tab:examplesTab>;
val emerald = <tooltier:contenttweaker:emerald>;

// Okay, now we are ready to begin. As normal tools are items, we will be using the same process we outlined when
// dealing with basic items. What is important to note is that for **ALL** tool types, the tier, base attack damage, and
// attack speed are mandatory, meaning that you have to specify them. If you don't, then your script will fail to
// validate.

<factory:item>.typed<Axe>() // Let's get started with an axe
    .tier(emerald) // We are using the tier we created before and referenced through a variable
    .baseAttackDamage(6.0f)
    .attackSpeed(-3.1f) // The negative value here means that the player's attack speed should be slowed down by this
                        // factor when using this tool. A positive value, like with the hoe, means that the speed is
                        // faster
    .tab(examples)
    .build("emerald_axe");

<factory:item>.typed<Hoe>()
    .tier(emerald)
    .baseAttackDamage(-2.0f)
    .attackSpeed(1.0f)
    .tab(examples)
    .build("emerald_hoe");

<factory:item>.typed<Pickaxe>()
    .tier(emerald)
    .baseAttackDamage(1.0f)
    .attackSpeed(-2.8f)
    .tab(examples)
    .build("emerald_pickaxe");

<factory:item>.typed<Shovel>()
    .tier(emerald)
    .baseAttackDamage(1.5f)
    .attackSpeed(-3.0f)
    .tab(examples)
    .build("emerald_shovel");

// For the sword, as we said, we consider it a tool, so all previous considerations apply. We need to specify the tier,
// the base attack damage, and the speed:

<factory:item>.typed<Sword>()
    .tier(emerald)
    .baseAttackDamage(3) // IMPORTANT NOTE: Swords only support whole numbers as base attack damage for some reason
    .attackSpeed(-2.4f)
    .tab(examples)
    .build("emerald_sword");

// As for the custom tool, the process is essentially the same, except we also have to specify the tag which applies to
// the tool. We will follow conventions and put the tag into our own namespace, as mineable/chisel.

<factory:item>.typed<CustomTool>()
    .tag(<resource:contenttweaker:mineable/chisel>)
    .tier(emerald)
    .baseAttackDamage(2.5f) // It can hurt pretty bad if handled wrongly
    .attackSpeed(0.0f) // 0 speed means that we want to use the base player speed
    .tab(examples)
    .build("emerald_chisel");

// And with this, we have managed to create an entire set of custom tools. Pretty neat, right?
