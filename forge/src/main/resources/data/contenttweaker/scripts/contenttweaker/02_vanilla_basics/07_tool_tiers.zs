#loader contenttweaker
#modloader forge

/**********************************************************************************************************************/
/*                                                     TOOL TIERS                                                     */
/**********************************************************************************************************************/

// Before delving into tools, let's take a small detour into tool tiers. As you might have noticed, the beginning of the
// files now contains two preprocessor directives (two lines starting with #). This also allows us to introduce another
// aspect of ContentTweaker and how these examples are structured.

// As you might already be aware, ContentTweaker is available on more than one mod loader, to give you more freedom on
// your choice. Nevertheless, the two mod loaders are different from one another and this means that some stuff has to
// be different between them. We strive to make sure our scripts are portable, allowing both mod loaders to run the
// same set of scripts without changes, but unfortunately some things are just different.
// When this is the case, we will mark our examples with the `modloader` preprocessor and the mod loader they are
// intended for.

// Going back to tiers, a tool tier is essentially a set of information that gives the game information relative to the
// material a tool is made from. It encodes data such as speed, bonus to attack damage, enchantment values, and repair
// ingredients. Moreover, tiers are ordered in a linear hierarchy, thus allowing for certain tier level requirements to
// be met in order to mine a block. The classic example is diamond ore, which requires an iron pickaxe or higher to mine
// successfully: this occurs because iron is the lowest tier, and diamond is a higher tier.
// In Forge, this ordering system is accomplished through the use of tier dependencies, with every tier specifying a
// list of lower tiers and a list of higher tiers. Nevertheless, vanilla compatibility is retained with the use of an
// integer level tier specified in the tier itself, although that value is effectively never used.

// Creation of a tier follows the same pattern we have established, with the factory as the first thing to obtain
// through the bracket handler. Much like creative tabs, the factory allows us to create tiers directly, letting us
// specify all tier properties in one sweep.
// Namely, we have to provide a name that uniquely identifies our tier in resource location convention, the
// vanilla-compatible level integer for sorting, the amount of uses a tool with this tier has, its mining speed, a
// bonus to the attack damage, the enchantment value, the resource location of the tag that will be used to know which
// blocks can be mined by this tier, a reference to the item that can be used to repair this tool, and lastly two arrays
// representing the lower and higher tiers this tier should fit in-between.
// Yes, that is a lot of parameters, so I guess that this is better handled with an example. In this case, we will be
// creating a tool tier that fits between iron and diamond, representing the emerald tier. Let's break down the full
// set of information:

<factory:tier>.create( // We grab the factory for tiers and specify we want to create a new tier
    "emerald", // The name of the tier in resource location convention: here we are using emerald to make it clear that
               // this is supposed to be some sort of emerald tier in our revamp
    2, // This is a random number which has to exist only for vanilla compatibility and will effectively be ignored in
       // every situation. In this context, the level should be between iron and diamond. Iron's level number is 2 and
       // diamond's level 3, so we decided to pick the lowest of the two. You can put any value in here, as long as it
       // is not negative
    581, // We specify that a tool made of emerald can be used 581 times before it breaks: this is essentially the
         // durability
    7.0f, // The speed at which the item should mine: this slips in perfectly between iron's 6 and diamond's 8
    2.5f, // A bonus to the damage made by a player when it attacks with any tool made with this tool tier
    16, // The enchantment value is essentially a value that describes the affinity of the tier to enchants. The higher
        // the number is, the more likely good enchants will be chosen by the enchantment table for a tool with this
        // tier
    <resource:forge:needs_emerald_tool>, // The resource location that identifies the tag which will be used to identify
                                         // the set of blocks that can be mined by this tool tier. Note the usage of the
                                         // resource location bracket, probably for the first time in the entirety of
                                         // ContentTweaker. The tag you will be specifying is a block tag, thus you can
                                         // then populate with CraftTweaker in the default or `tags` loader through
                                         // <tag:blocks:forge:needs_emerald_tool>.add
    <item:minecraft:emerald>, // It makes sense to use emeralds to repair an emerald-tier tool
    ["iron"], // The set of tiers that should be less "powerful" that the tier we are creating, so have to sit lower in
              // the hierarchy. You can specify these tiers either as strings (like we did here), as resource locations
              // through the bracket handler (like we will do for the next array), or by specifying a reference to a
              // tier directly
    [<resource:minecraft:diamond>] // The set of tiers that are more "powerful" than the one we are creating. Refer to
                                   // the previous comment for more information
);

// And we're done! I reckon this is a lot of parameters, but tiers store a lot of information. As with like everything
// else, once a tier is created, it can be referenced through a tier reference, as follows:

<tooltier:contenttweaker:emerald>; // This references the tier we have just created.
