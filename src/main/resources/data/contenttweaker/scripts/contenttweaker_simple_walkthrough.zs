#loader contenttweaker
// Since CraftTweaker in 1.14+ will load while the server is up and running, we need a way to load scripts somewhere else.
// That is what the #loader contenttweaker is for!
// You can simply put it somewhere in (preferable the top of) your file and you're set to go.
// All registry stuff are supposed to be run in ContentTweaker scripts, otherwise, nothing will be registered!
// And remember no crafttweaker scripts are allowed in #loader contenttweaker as they run at different phases of the load cycle!

import mods.contenttweaker.item.ItemBuilder;
import mods.contenttweaker.item.tool.ItemBuilderTool;
import mods.contenttweaker.item.advance.ItemBuilderAdvanced;
import mods.contenttweaker.block.BlockBuilder;
import mods.contenttweaker.block.stairs.BlockBuilderStairs;
import mods.contenttweaker.block.advance.BlockBuilderAdvanced;
import mods.contenttweaker.block.basic.BlockBuilderBasic;
import mods.contenttweaker.block.pillar.BlockBuilderPillarRotatable;
import mods.contenttweaker.fluid.FluidBuilder;

// ====== ITEMS =====

// This is the simplest way to creating items
new ItemBuilder().build("generic_item");
new ItemBuilder().build("generic_item_2");
new ItemBuilder().build("generic_item_3");

// With setting some properties
new ItemBuilder()
    .withMaxStackSize(16)
    .build("other_item");

// Creating a tool
new ItemBuilder()
    .withMaxDamage(100) // If you set maxDamage, maxStackSize will be set to 1 hardcode
    .withType<ItemBuilderTool>() // set type, it will transform the item builder to tool builder
    .withToolType(<tooltype:shovel>, 3, 4.0F) //Shovel harvest level 3 and destroy speed 4.0
    .withAttackDamage(10.0F)
    .withAttackSpeed(5.0F)
    .withDurabilityCostAttack(1) //By default: 2
    .build("my_tool");

// Creating an advanced item. See contenttweaker_advanced_function.zs
new ItemBuilder()
    .withType<ItemBuilderAdvanced>()
    .build("inf_flint_and_steel");

// ====== BLOCKS =====

// This is the simplest way to creating blocks, uses <blockmaterial:iron> and basic builder (to create a full cube block)
new BlockBuilder().build("generic_block");

// Set a different block material
new BlockBuilder(<blockmaterial:earth>).build("earth_like_block");

//The Pillar Type is basically the same as logs, one texture on top/bottom and one for the sides.
//Can be rotated on all axes, just like logs.
//Texture names by default will be "block_name" + "end", "_sides"
new BlockBuilder()
    .withType<BlockBuilderPillarRotatable>()
    .build("preset_pillar_rotatable_noarg");

//Stairs.
//Has 3 Textures, top, bottom, sides, by default they will be "block_name" + "_top", "_bottom", "_sides"
new BlockBuilder()
    .withType<BlockBuilderStairs>()
    .build("stairs_noarg");

// Creating an advanced block. See contenttweaker_advanced_function.zs
new ItemBuilder()
    .withType<BlockBuilderAdvanced>()
    .build("test_block");

// ====== FLUIDS =====

// This is the simplest way to creating fluids.
// The builder constructor argument is:
// * `isMolten as bool`: (Is the fluid lava-like or water-like?)
// * `color as int`: The RGB fluid color, highly recommend `0x` hex value.
// The fluid will auto tinted, so in most cases, you needn't care about fluid textures.
new FluidBuilder(true, 0xff0000).build("generic_fluid");

// With setting some properties
new FluidBuilder(true, 0x66ccff)
    .viscosity(3000)
    .density(3000)
    .temperature(1400)
    .luminosity(15)
    .build("generic_fluid_2");

// Setting fluid texture extra
// There still is a color argument, but it is only used for bucket, not applied to the fluid
new FluidBuilder(false, 0x999999, <resource:contenttweaker:fluid/generic_fluid2_still>, <resource:contenttweaker:fluid/generic_fluid2_flow>).build("generic_fluid_2");

// ====== TEXTURES AND NAMES =====
/*
They need a RESOURCE PACK, not SCRIPTS. You are supposed to install a resource manager mod. You can use one of below:
https://www.curseforge.com/minecraft/mc-mods/the-loader
https://www.curseforge.com/minecraft/mc-mods/open-loader
https://www.curseforge.com/minecraft/mc-mods/drp-global-datapack
If ContentTweaker detects one of them is installed, it will try to create model files as well as a
simple no-texture image in your resource folder.
For the generated textures, CoT will not override files if they already exist,
so you can simple replace the existing files with your own ones and CoT will not undo these changes.

So, how would you go about giving items proper names?
For that, you need a lang file.
At the time of this writing CoT did not yet create that one for ya, so you will need to create it yourself.
In your resource pack, find the assets/contenttweaker folder.
In there, create a folder named lang if it does not yet exist, and create a file named en_us.json in there.
I recommend always starting with the en_us one, since that is what the game will fall back to if it cannot
find the name for another language. Afterwards feel free to repeat this with other lang codes as well.

In there you will have to create a Key-Value map for your entries. The keys, also called Translation keys,
or in earlier versions unlocalized Name, are dependent on the name of the block/item you used, and they will look like

"<block|item|fluid>.contenttweaker.<the_name_you_gave_them>"

For the value, you can set how the item is named ingame in there. If you are unsure about the syntax, check the example below, or try a JSON Validator if you got the syntax down.

TLDR: <resoruce_pack>/assets/contenttweaker/lang/en_us.json.

{
  "block.contenttweaker.generic_block": "Generic Block",
  "item.contenttweaker.generic_item": "Generic Item",
  "item.contenttweaker.generic_item_2": "Generic Item the 2nd",
  "item.contenttweaker.generic_item_3": "Generic Item the charmed one",
  "fluid.contenttweaker.generic_fluid": "Generic Fluid",
  "fluid.contenttweaker.generic_fluid_2": "Generic Fluid the 2nd"
  "item.contenttweaker.generic_fluid_bucket": "Generic Fluid Bucket"
  "item.contenttweaker.generic_fluid_2_bucket": "Generic Fluid the 2nd Bucket"
}

*/