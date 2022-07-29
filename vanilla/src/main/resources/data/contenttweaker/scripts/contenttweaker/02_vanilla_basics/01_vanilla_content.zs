#loader contenttweaker

/**********************************************************************************************************************/
/*                                                   VANILLA CONTENT                                                  */
/**********************************************************************************************************************/

// One of the main goals of ContentTweaker is to let you create new "vanilla" content. By vanilla content, we mean
// blocks, items, sound events, and essentially elements that might appear in the vanilla game.
// Everything that by default is not part of vanilla, such as gases, is not going to be part of the Vanilla creation
// capabilities of the mod. Addons might add this capability, in which case you should refer to their documentation.

// More in detail, at the moment ContentTweaker is able to create objects of the following types:
// - sound events
// - blocks, namely only plain blocks at the moment
// - items, including plain items and tools
// - creative tabs

// Since you are currently running a beta version of ContentTweaker, we recognize that the amount of things you can do
// is pretty limited. We nevertheless wanted to push out a version that people could test, so that we can get feedback
// relative to the API quality.
// Obviously, new capabilities will be added in the future, including but not limited to slabs, pillars, simple machines
// and containers, and more. Stay tuned for updates.

// With that out of the way, the creation process is similar for a lot of objects. In this set of examples, we will
// only cover the basics. You are referred to the documentation to know the full capabilities of the mod.
// We will start with the basics with sound events, and then move on to more complex stuff later.
