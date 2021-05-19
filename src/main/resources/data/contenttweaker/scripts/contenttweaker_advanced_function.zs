#loader crafttweaker
// Advanced functions is called on game runtime.
// So set function actions should be run in loader crafttweaker and reloadable.
// Since the default loader is crafttweaker, you can omit the loader preprocessor, just clarify it here.

import mods.contenttweaker.util.ActionResultType;

// Uses BEP to get an CoTItemAdvanced and CoTBlockAdvanced instance.
// <advanceditem:test_item>
// <advancedblock:test_block>

// Makes the item like a infinite duration flint and steel
// The on item use function is called whenever the item is used on a block.
// Before it, you should use ItemBuilder in CoT scripts to add an advanced item named "inf_flint_and_steel"
// For more info, see documentation.
<advanceditem:inf_flint_and_steel>.setOnItemUse((context) => {
    val pos = context.pos;
    val direction = context.direction;
    val firePos = pos.offset(direction);
    val world = context.world;
    if (world.isAir(firePos)) {
        world.setBlockState(firePos, <blockstate:minecraft:fire>);
    }
    return ActionResultType.SUCCESS;
});
