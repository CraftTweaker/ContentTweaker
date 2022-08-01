#loader contenttweaker

/**********************************************************************************************************************/
/*                                                    BASIC BLOCKS                                                    */
/**********************************************************************************************************************/

// It's now time to take a look at the creation of basic blocks. Much like items, there is also a small bit of
// boilerplate we have to get through, but this will feel extremely similar.

// In fact, much like items, ContentTweaker lets you create many types of blocks, with addons being able to expand the
// possibilities. The same concept of "type" is thus borrowed from items and applied to blocks.
// As a little refresher, the "type" of a block essentially represents what kind of things the block is supposed to do
// and what data is required for it to behave correctly. Exactly like items, every "type" requires different information
// to be provided, so you'll have to look at the documentation for the specific type you want to use.
// In this example script, we will only be dealing with the basic block type, so blocks with no special interactions
// with the environment, like any other building block you might have used: stone, cobblestone, etc.
// We thus have to import the class to make it easier to reference it later. This is optional, but I prefer shorter
// names for the sake of clarity.
// We will also reference block properties further ahead, so I'll import classes related to that too.

import contenttweaker.builder.vanilla.block.Basic;
import contenttweaker.object.vanilla.property.StandardBlockProperties;

// We are now ready to start.

// The process used to create blocks is the same as the one for items. The starting point is the block factory, like
// every other object type. We then need to call the `typed` method on the factory, which will let us specify the type
// of block we want to create, as we have explained previously. The syntax follows the same format as for items, with a
// "generic" being specified between angle brackets. Again, for more information, refer to the ZenCode documentation.
// Once specified the type, you can start configuring it. As discussed before, every type has its own specific
// configuration parameters. There is a shared set though, called "block properties", which we'll be discussed in a few
// paragraphs.
// Once everything has been configured the way you want it, you can call `build` to confirm the creation of the block,
// specifying its name with resource location conventions, and you will get back a reference to the newly created block.

// With "block properties", we refer to a set of information that characterizes every block in the game. By default, you
// can access and configure a set of properties called "standard properties".
// Standard properties encode things like the material used by the block, the sound it makes when walked on, the light
// level emitted by it, its explosion resistance, and more.
// Additional mods might also add new set of block properties.

// Out of all block properties, it is **always** mandatory to configure the material, as the game requires all blocks to
// have a material associated to them. When specifying the material, you will see a reference to a `material` bracket
// being used. As you might guess, this is similar to the `tab`, `soundevent`, and `item` brackets we saw previously: it
// is in fact a way to obtain a reference to a material.
// This in turn might make you think that materials, like everything else that has a reference, can be created through a
// factory, and you'd be right: that is exactly the case. We will not be covering material creation in this set of
// examples though, as we deemed it to be a more intermediate-level topic. This means you'll encounter examples
// regarding material creation only further out in this series.

// This is enough exposition for now, let's try to create a block. This will also allow us to introduce an additional
// and important concept on how the game works. We will create a simple block with a metallic material and a light level
// of 14, which means as bright as a torch.

val veryBasicBlock = <factory:block>.typed<Basic>() // Here we are specifying the type of the block: we want it to be of
                                                    // "Basic" type, and we have imported that type at the top already.
    .material(<material:minecraft:metal>) // Here we specify that the material the block is made of is metallic. Note
                                          // the use of a `material` bracket handler to obtain a reference to it.
    .lightLevel(14) // The block will emit as much light as a torch does
    .tab(<tab:examplesTab>) // We set the block to appear in the "examples" creative tab. Note that this explanation is
                            // slightly incorrect, but fine for now. We will introduce the nuance in a little bit.
    .build("very_basic_block"); // Last but not least, we build the block, enqueuing it for registration. Note that we
                                // have specified the name to use, and that we have to follow all resource location
                                // conventions, exactly like with items.

// The block above has been queued for registration and the `veryBasicBlock` variable now acts as a reference to it.

// Much like everything else we have covered, it is also possible to reference blocks through a type-specific bracket
// handler. We have seen items borrow the syntax from CraftTweaker, so it makes sense for blocks to do the same, through
// the `block` bracket handler, with the following being a reference to the block we have just created:

<block:contenttweaker:very_basic_block>;

// Let's now take a look at block properties. Much like items, block properties are available on every block, and
// ContentTweaker gives you a way to reference those properties through block references. The same syntax as the one for
// items applies almost 1:1, except we want to reference "standard block properties", as follows:

val dirtProperties = <block:minecraft:dirt>.findProperties<StandardBlockProperties>();

// The same shortcut we have seen for items works for blocks too, with the `properties` getter allowing us to skip the
// `findProperties` call and the importing of `StandardBlockProperties`:

<block:minecraft:dirt>.properties;

// Much like item properties, we can use them to build a "clone" of the block through the `cloning` method. The clone we
// create, though, will **NOT** inherit any sort of behavior, as that is not determined by properties, but by the type
// of the block. In other words, trying to create a clone of the dirt block will give us a block that breaks like dirt
// and sounds like dirt, but cannot be right clicked with a shovel to turn it into a path.

// Let's try to create our own block with the same set of properties as dirt, using the knowledge we have accumulated:

<factory:block>.typed<Basic>().cloning(dirtProperties).build("our_own_dirt_clone");

// As with items, we might not want to clone a block exactly, though. For example, we might want to have our blocks be
// "enhanced" versions of the original block, having them much more difficult to break and being more resistant to
// explosions. To do that, we simply have to specify the values of the properties normally, and ContentTweaker will
// handle the overriding of the various values automatically.

// Let's try to see that in action: we are going to clone the "very basic block" and have it be almost impossible to
// break.

<factory:block>.typed<Basic>()
    .cloning(veryBasicBlock.properties)
    .strength(5000.0F) // Here we are applying the same strength value to both the destroy time and the explosion
                       // resistance. For reference, obsidian has 50 destroy time and 1200 explosion resistance. Good
                       // luck breaking this block!
    .build("enhanced_basic_block");

// If you have been following these example scripts with the game open, you might have noticed that the last two blocks
// are nowhere to be seen in the creative menu, despite them having their creative tabs specified through properties
// cloning. You can only obtain them through the `/give` command or through a mod like REI or JEI. That is because of
// the nuance mentioned previously.

// In-game, there is a big difference between blocks and items. Blocks, in fact, are those things you place in the world
// aligned somewhere on the world grid. That's the only place blocks can exist in the game! Everything you can hold in
// your hand or browse with those recipe-viewing mods is an **item**. To maintain the illusion of those two different
// elements being the, the items take the appearance of blocks.
// You might have already noticed or experienced this difference when dealing with recipes or tags in CraftTweaker,
// where using blocks in a crafting table recipe gave you a more-or-less cryptic error message. This duality is exactly
// the reason.
// From the above information, we can conclude that the only things that can appear in a creative tab are items, and
// both of those blocks were cloning **block** properties. To have them appear somewhere, we thus have to specify the
// creative tab too:

<factory:block>.typed<Basic>()
    .cloning(<block:contenttweaker:enhanced_basic_block>.properties)
    .tab(<tab:examplesTab>) // We have to specify the creative tab, as that is not part of the block properties
    .build("enhanced_and_visible_basic_block");

// "But wait, you have just said that blocks and items are different, yet I am only creating blocks and I see items
// appear everywhere", you say in a very confused tone, and you're right to be confused. Let me try to clear it up.
// When you register a block in ContentTweaker, we are also automatically creating a default item for you that you can
// use to place the block. The item has all default properties, so stacks up to 64, has no remainder and cannot be
// eaten, among other things. When you specify the creative tab in your block configuration, you are actually telling
// ContentTweaker which tab you want the item to appear in.
// The item that is created is called a "block item", and is a particular type of item you will encounter in a bit. The
// major difference is that it can be right-clicked on the ground to place the block.

// The automatically created block item might be enough for most use-cases, but ContentTweaker allows for the disabling
// of this mechanic, should you choose to do so, with the `noCorrespondingItem` method. Note that if you do so, you also
// have to specify what the block is supposed to drop when broken. To do so, you can either use:
// - `dropsFrom` to specify the ID of the loot table to use;
// - `dropsLike` to specify a block from which drops should be cloned from;
// - `noDrops` to indicate that the block should not drop anything;
// - `dropsItselfRegardless` to force the creation of a loot table that drops the corresponding block item anyway.
// Note that the last option only works if you are creating the corresponding block item manually, and the created item
// has the same registry name as the block. If that's not the case, a data pack error will occur.

// As an example of that feature in action, let's create two blocks: a "lone block" that has no block item and drops
// like dirt, and a "manual block", which has no block item but drops itself regardless. The block item will then be
// created in another script and the linking magic will happen:

<factory:block>.typed<Basic>()
    .material(<material:minecraft:dirt>)
    .noCorrespondingItem()
    .dropsLike(<block:minecraft:dirt>)
    .build("lone_block");

<factory:block>.typed<Basic>()
    .material(<material:minecraft:ice>)
    .noCorrespondingItem()
    .dropsItselfRegardless()
    .build("manual_block");

// This concludes our initial foray into block registration. There are many properties we have not explored, but this
// should serve as a basic introduction.
