#loader contenttweaker

/**********************************************************************************************************************/
/*                                                    CREATIVE TABS                                                   */
/**********************************************************************************************************************/

// "Yet more boring stuff" I hear you say. Fear not, as here we will not only learn to create creative tabs, but we will
// also explain one more important relationship between references and created objects.

// Now, a creative tab is one of those groups that appear in the creative inventory and that let you pick items from a
// particular category. Examples are the "Miscellaneous" or the "Tools" tab in vanilla, whereas mods usually have one or
// more mod-specific tabs.

// The creation process is similar yet different: we always need to obtain the creative tab factory, but this time
// around, the factory allows us to **create** items directly, without having to go through an intermediary. As we
// explained previously, this occurs only with factories that offer a `create` method.
// To bring to life a creative tab we need two pieces of information: the ID of the creative tab and a reference to an
// item which will act as the icon.
// Creative tabs are also subject to a very annoying exception though, because the game decided so. Their ID is **not**
// a resource location and does not follow resource location conventions, thus you **must not** use `snake_case` when
// specifying the ID. Rather, you have to use `camelCase`.
// For an example, the ID `shadow_of_the_tomb_raider` would become `shadowOfTheTombRaider`. Do NOT use snake case, as
// that is prone to cause issues.
// With that out of the way, let's look at an example:

val emptyTab = <factory:creative_tab>.create("emptyTab", <item:minecraft:diamond>);

// We have just created a creative tab, with ID `emptyTab` and using the vanilla Diamond as an icon. Note that we are
// referencing an item through the standard `item` bracket handler: don't worry too much about it, as it will be
// explained next in the item section of this series of examples. The value returned by the method is a reference to the
// creative tab.

// Now, an empty creative tab will not appear in the game as creative tabs are created when they are necessary. This
// means that we have to add at least one item to the creative tab to make it show up. We will also do this later.

// And for the tricky part! What if you wanted to use an item you have yet to register as the creative tab icon? After
// all, this cyclic dependency is inevitable: to create a creative tab you need to specify an item as the icon, but to
// have an item, you need to specify the creative tab it is in. This eventually leads to a cycle.
// ContentTweaker has already solved the issue though, thanks to its use of references. Remember how references are
// promises that something will exist? This allows us to break out of this cycle by using an item reference! Let's look
// at this example showing that in action:

<factory:creative_tab>.create("examplesTab", <item:contenttweaker:very_basic_item>);

// Note how we are referencing an item called "very_basic_item" added by us, but we have not yet added anything! This is
// the power of references. We will be creating this item in one of the coming example scripts, so fear not.

// To be complete, we also need to look at how to obtain a reference to a creative tab. You might be thinking that you
// can use the `reference` bracket we have explored before, but that is **not the case**!
// Due to the special exception with IDs we have covered before, the **only** way you are supposed to obtain a creative
// tab is through specifying that ID in the special `tab` bracket handler. For example, to get the creative tab we have
// created previously we'd have to use the following bracket handler:

<tab:examplesTab>;

// Note the lack of namespacing, so be extremely wary with your creative tab ID: you don't want to create conflicts.
