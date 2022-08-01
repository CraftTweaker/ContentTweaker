#loader contenttweaker
#modloader fabric

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
// In Fabric, this ordering system is accomplished through the use of the vanilla level, which is an integer number that
// allows for sorting.

// Creation of a tier follows the same pattern we have established, with the factory as the first thing to obtain
// through the bracket handler. Much like creative tabs, the factory allows us to create tiers directly, letting us
// specify all tier properties in one sweep.
// Namely, we have to provide a name that uniquely identifies our tier in resource location convention, the level
// integer for sorting, the amount of uses a tool with this tier has, its mining speed, a bonus to the attack damage,
// and the enchantment value.
// Yes, that is a lot of parameters, so I guess that this is better handled with an example. In this case, we will be
// creating an additional tool tier that acts as an alternative to iron: the emerald tier. Let's break down the full
// set of information:

<factory:tier>.create( // We grab the factory for tiers and specify we want to create a new tier
    "emerald", // The name of the tier in resource location convention: here we are using emerald to make it clear that
               // this is supposed to be some sort of emerald tier in our revamp
    2, // This is the level which will be used for sorting. Since we want this tier to act as an alternative to the
       // existing iron tier, we have to use its level number of 2. Note that it is **NOT** possible to add a tier
       // between two existing tiers in Fabric
    581, // We specify that a tool made of emerald can be used 581 times before it breaks: this is essentially the
         // durability
    5.0f, // The speed at which the item should mine: this is slightly worse than iron's 6
    1.5f, // A bonus to the damage made by a player when it attacks with any tool made with this tool tier
    16, // The enchantment value is essentially a value that describes the affinity of the tier to enchants. The higher
        // the number is, the more likely good enchants will be chosen by the enchantment table for a tool with this
        // tier
    <item:minecraft:emerald> // It makes sense to use emeralds to repair an emerald-tier tool
);

// And we're done! I reckon this is a lot of parameters, but tiers store a lot of information. As with like everything
// else, once a tier is created, it can be referenced through a tier reference, as follows:

<tooltier:contenttweaker:emerald>; // This references the tier we have just created.
