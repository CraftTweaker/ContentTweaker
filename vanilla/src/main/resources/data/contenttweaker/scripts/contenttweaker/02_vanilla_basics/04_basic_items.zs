#loader contenttweaker

/**********************************************************************************************************************/
/*                                                    BASIC ITEMS                                                     */
/**********************************************************************************************************************/

// We'll now be looking at the creation of items. First, though, we have to get through a bit of boilerplate, so this
// bit will feel a little bit out of order.

// ContentTweaker let's you create many different types of items, and allows CraftTweaker addons to provide additional
// personalization options. For this reason, creating items is slightly less straight-forward than the previous two
// examples and requires you to specify a "type".
// The "type" of an item essentially represents what kind of things the items is supposed to do and what data is
// required for it to behave correctly. Each "type" might require different information, so you have to look at the
// documentation for the type you desire.
// In this script, we will only be dealing with basic item types, meaning items that have no special behavior except for
// existing and, maybe, allowing the player to eat them. A basic item is thus something like a stick, or bread.
// We thus have to import the class to make it easier to reference it later. This is optional, but I prefer shorter
// names for the sake of clarity.
// We will also reference item properties further ahead, so I'll import classes related to that too.

import contenttweaker.builder.vanilla.item.Basic;
import contenttweaker.object.vanilla.property.FoodItemProperties;
import contenttweaker.object.vanilla.property.StandardItemProperties;

// For brevity, I will also reference the creative tabs that were created in the previous examples, so that we can use
// them here:

val emptyTab = <tab:emptyTab>;
val examplesTab = <tab:examplesTab>;

// We are now ready to start.

// The process which you use to create an item starts like everything else, with the item factory. The factory exposes
// only a single method, called `typed`. This is because, as we have explained before, each item has a type. You have to
// specify the type between angle brackets, as if it were a bracket handler. This is called a "generic", but you do not
// have to worry about that at the moment. If you want more information, refer to the ZenCode documentation.
// Once specified the type, you can then start configuring it. As discussed before, every type has its own specific
// configuration parameters, but they all share a set, called "item properties". We will discuss them shortly.
// At the end, you can call `build` to confirm the creation of the item, specifying its name with resource location
// conventions, and you will get back a reference to the newly built item.

// With "item properties", we refer to a set of information that characterizes every item in the game. By default, you
// can access and configure a set of properties called "standard properties".
// Standard properties encode things like the maximum stack size, the creative tab the item sits in, its rarity, whether
// it is resistant against fire, etc.
// Some items also have "food properties", which encode behavior such as the amount of nutrition and saturation that a
// particular piece of food provides. Obviously, only items that can be eaten have this set of properties.
// Additional mods might also add new kinds of item properties.

// Enough blabbering around, though, let's try to create some items. We will start simple, with default values for
// all properties, except for the creative tab, which we will set to our examples tab, and an uncommon rarity.

val veryBasicItem = <factory:item>.typed<Basic>() // Here we are specifying the type of the item: we want it to be of
                                                  // "Basic" type, and we have imported that type at the top already.
    .tab(examplesTab) // We want the item to appear in our "examples" creative tab
    .rarity(<constant:minecraft:item/rarity:uncommon>) // The rarity of our item is set to "uncommon": this means the
                                                       // item name will be colored differently in the tooltip.
    .build("very_basic_item"); // And here we build the item, enqueuing it for registration. Note that we have to
                               // specify the name to use, and that we have to follow all resource location conventions.
                               // Differently from sound events, this name is mandatory, as we have cannot specify any
                               // other alternative IDs anywhere else.

// The item above has now been queued for registration and the `veryBasicItem` variable is a reference to it. Notice how
// the name of the item matches the one we have referenced before in the creative tab script: the reference will know to
// use this item as the icon when the time comes.
// With the same line of reasoning, we can also find out how to reference our items or vanilla items: we have seen that
// in creative tabs we have used the `item` bracket handler, so we can do the same here. The following is a reference to
// the item we have just created:

<item:contenttweaker:very_basic_item>;

// Note how it looks exactly like we'd do if we were in a CraftTweaker script. This is done on purpose to make it much
// easier to learn to use ContentTweaker effectively!

// Let's now try to create a food item and then use the `item` bracket handler to get a reference to it:

<factory:item>.typed<Basic>() // As we said before, food items are also "Basic", as every item has the
                              // ability to become food.
    .tab(emptyTab) // Let's place this in the empty tab, just to make it different
    .nutrition(3)
    .saturationMod(2.5f)
    .canAlwaysEat(true) // We can eat this even if we do not have to eat: useful for testing purposes
    .build("mori_mori");

val moriMori = <item:contenttweaker:mori_mori>; // This reference is the same type of reference we would get from the
                                                // `build` method. There is no reason to do this other than for pure
                                                // example value.

// Time for one more thing. Do you remember when we talked about such properties being available on every item?
// ContentTweaker gives you a way to reference those properties through the item references themselves! This can be
// combined with the `cloning` method to create an item that has the same set of properties as another already existing
// item. To access these properties, you can use the `findProperties` method on an item reference. For example, let's
// try to get the standard set of properties of a water bucket and the food properties of a carrot:

val bucketProperties = <item:minecraft:water_bucket>.findProperties<StandardItemProperties>();
val carrotProperties = <item:minecraft:carrot>.findProperties<FoodItemProperties>();

// If we were to specify these properties in a `cloning` method when we are building an item, as we will see soon, we
// would be creating an item that can be eaten like a carrot and behaves like a water bucket in terms of stack size,
// container item, and other custom properties. This does **NOT** mean we could place water with right clicks though,
// as that is behavior and is thus encoded by the "type" of the item, instead of its properties.

// Not only that, but the standard set of properties is so common to reference, that it also has a shortcut:

<item:minecraft:water_bucket>.properties;

// The above would get the same set of properties as the `findProperties` call, except that it is much shorter and also
// does not require us to import `StandardItemProperties`.
// Let's try to combine this knowledge to create an item that shares both behaviors:

<factory:item>.typed<Basic>().cloning(bucketProperties, carrotProperties).build("carrot_bucket");

// We might not want to clone an item exactly, though. For example, we might want to have our items stack to 64 anyway,
// instead of being restricted into a stack size of 1, or we might want to change the rarity. To do that, we simply have
// to specify the values of the properties normally, and ContentTweaker will handle the overriding of the various values
// automatically.

// Let's try to see that in action: we are going to clone the "very basic item" and have the new item be epic.

<factory:item>.typed<Basic>()
    .cloning(veryBasicItem.properties) // Note how we do not have to specify which type of properties we want to clone,
                                       // as ContentTweaker will automatically determine the correct type and behave
                                       // accordingly
   .rarity(<constant:minecraft:item/rarity:epic>)
   .build("epic_basic_item");

// And with this, we have the basics of item creation in ContentTweaker done.
