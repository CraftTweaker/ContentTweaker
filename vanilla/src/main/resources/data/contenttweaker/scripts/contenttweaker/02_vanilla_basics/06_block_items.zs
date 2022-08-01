#loader contenttweaker

/**********************************************************************************************************************/
/*                                                    BLOCK ITEMS                                                     */
/**********************************************************************************************************************/

// In the previous example, as we were talking about blocks, we introduced the concept of block items. To recap, a block
// item is a special type of item that bridges blocks and items together, allowing blocks to be stored in containers,
// used in recipes, and placed via use actions.
// We also discussed how you can create custom block items with ContentTweaker should you prefer not to use the default
// generation provided by the mod. In this short example we will see how to do exactly that.
// If you want more information related to block items, refer to the block example script.

// Since block items are a type, we want to import the class that will let us create them so that we do not have to
// use the fully qualified name.

import contenttweaker.builder.vanilla.item.BlockItem;

// Since this is a type, we can combine the knowledge we already have on how to create an item and configure it, and the
// new requirements added by this type to understand what we have to do.
// In particular, creating a block item requires a reference to the block that will be placed down when the item is
// used and from which the item model will be derived. All other properties can also be specified, but they are
// optional.

// Armed with this knowledge, let's try to create an alternative block item for a vanilla diamond block:

val alternativeDiamond = <factory:item>.typed<BlockItem>() // The usual beginning
    .tab(<tab:examplesTab>) // Let's place it in our usual example tab
    .block(<block:minecraft:diamond_block>) // And here we pass a reference to the block that this block item should
                                            // place down. Note how the reference is used directly, like with the
                                            // creative tab.
    .build("alternative_diamond_block_item"); // And let's build the block item, and enqueue it for registration.

// This should make a diamond block appear in our examples tab and place down a diamond block when right clicked. Note
// that breaking the block does **not** drop our custom block item, as that behavior is encoded by the loot table. A
// CraftTweaker loot modifier should suffice for that, though.

// We can also create block items for blocks we have already registered and for which we have disabled block item
// creation. This allows us to have more control over the block item. Let's try that out: we have registered a block
// called "manual_block" in the previous script, and we want its block item to appear in our usual creative tab. We also
// want it to have a stack size of 1, and when crafted with something, it should leave back our alternative diamond in
// the table.

<factory:item>.typed<BlockItem>()
    .block(<block:contenttweaker:manual_block>)
    .tab(<tab:examplesTab>)
    .stacksTo(1)
    .craftRemainder(alternativeDiamond)
    .build("manual_block"); // By specifying the same name as the block, the item and block item are linked together,
                            // allowing ContentTweaker to correctly generate the loot table, as we have specified that
                            // the block should  `dropsItselfRegardless`.

// There is not much else to talk about relative to block items, and this is very likely going to be niche.
// Nevertheless, we wanted to take the occasion to explore another non-basic type.
