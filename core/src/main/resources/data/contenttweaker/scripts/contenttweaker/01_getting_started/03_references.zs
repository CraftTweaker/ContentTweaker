#loader contenttweaker

/**********************************************************************************************************************/
/*                                                     REFERENCES                                                     */
/**********************************************************************************************************************/

// ContentTweaker builds on a set of core concepts that will be used across all scripts and expanded upon to provide
// functionality. It is thus fundamental to have a clear understanding of these core concepts.

// The first concept we will explore is references. A reference is an object that acts as a pointer to another object,
// allowing you to reference it, hence why the name. The reference can then be used instead of the actual object for
// most operations.
// Let's try to make this concept clearer with an example. Imagine you are baking a cake, your favorite cake. You want
// to tell your friends about it, so you snap a photo and send it to them. The photo in this context is not the cake you
// baked, but your friends can still see it and talk about the cake. The photo is thus the **reference** to the cake you
// made. Your friends can use it to talk about the cake, even if they do not have physical access to it.

// This is a very simplified analogy, but it is a good start. Now, we need to do a little step further to have a clearer
// and more accurate view of what a reference is.
// In ContentTweaker, in fact, a reference does not "point" towards an object that already exists, but an object that
// *might* exist in the future. In other words, you get a reference to something that will appear only in a second
// moment, namely after your scripts have been executed.
// This might sound even weirder, but another example will make this easier to grasp. Let's say you are a fan of the
// Tomb Raider series of video games, and you've heard that "Path of the Tomb Raider" is going to come out in a few
// months on PC. You hurry to a game store and preorder a copy. You pay either full price or a percentage, depending on
// the conditions, and in exchange you get a receipt stating that you **will** own the game when it comes out. What you
// bought is essentially a reference to the copy of the game. It does not exist yet, but it will.
// Suppose now that for some reason, the game gets cancelled because of some corporate shenanigans. You still have a
// reference, but that reference now will never be satisfied, as the game will never exist. Hopefully you can get a
// refund.

// In order to create a reference, the easiest way is to use the `reference` bracket handler. The syntax is extremely
// simple: `<reference:type:id>`.
// `type` indicates what type of object the reference references, whereas `id` is meant to represent the name. The
// syntax for both of these portions is extremely flexible: they are both resource locations (i.e. strings in the form
// "modid:name"), but they both allow you to skip the mod ID if it's the default minecraft one.
// For example, these are all valid references:

<reference:minecraft:item:minecraft:diamond>; // References the vanilla diamond.
<reference:item:minecraft:diamond>; // Same as above: notice the missing specifier for the `type`.
<reference:item:diamond>; // Same as above: notice how both `minecraft` specifiers are skipped.
// <reference:minecraft:item:diamond>; // THIS IS NOT ALLOWED! Due to ambiguity, this means you're trying to reference
                                       // something of type `minecraft` with id `item:diamond`, which is NOT what you
                                       // want (unless it is).

<reference:block:hahamod:hello>; // A reference to a block called `hahamod:hello`: notice how we can create the
                                 // reference even if such a block does not exist.

<reference:minecraft:sound_type:minecraft:shroomlight>; // A reference to the set of sounds made by shroomlights.

// Note that this syntax might look a bit verbose, and that's because it is. As you progress with these scripts, though,
// you will discover ways to make this syntax a bit easier to use. As of know, you are using the most general bracket
// handler, that allows you to reference pretty much everything. There will be more specialized ones later on.
