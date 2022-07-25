#loader contenttweaker

/**********************************************************************************************************************/
/*                                                      FACTORIES                                                     */
/**********************************************************************************************************************/

// Another important concept in ContentTweaker is the "factory". Just like the name implies, a factory is responsible
// for creating objects of a certain type. Much like in real life, each factory needs a different set of processes to
// create something.

// First of all, to obtain a factory, you have to use the `factory` bracket handler. This bracket handler is similar to
// the ones you are already used to in CraftTweaker, requiring you to specify the type of objects you want to create.
// The syntax is `<factory:type>`, where `type` can be either in the format "modid:name" or simply "name" if you are
// referencing something builtin into the base game.
// Let's look at some examples:

<factory:minecraft:item>; // This obtains a factory for items.
<factory:item>; // Same as above: notice the lack of 'minecraft' as that's implied.

<factory:block>; // The factory to create blocks.

<factory:creative_tab>; // The factory to create custom creative tabs.

// As you can see, this is much easier than references. Every time you use a bracket handler, a new factory is created.
// This means you can create as many as you want for as many different objects as you want, and each factory will be
// completely separate from the others.

// After you obtain a factory, what you have to do changes depending on the factory itself.
// By convention, though, every factory will eventually return a reference (yes, the thing we've talked about in the
// previous script) to the object you've created. This is useful in case you want to reuse that object in your script.
// The reference is returned by a method called either `create` or `build`. The difference is that `create` is called
// directly on the factory, whereas `build` appears after a chain of methods.
// To try and make myself clearer, creating an object essentially follows one of these two patterns:

// val thing = <factory:example>.create("object_name", some other property);

// val thing = <factory:another_example>.doSomething().somethingElse(some values).moreStuff().build("object_name");

// We will see some real-world applications of these examples in the future.
