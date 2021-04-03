#loader crafttweaker
// Advanced functions is called on game runtime.
// So set function actions should be run in loader crafttweaker and reloadable.
// Since the default loader is crafttweaker, you can omit the loader preprocessor, just clarify it here.

// Uses BEP to get an IIsCotItem and IIsCotBlock instance.
// <cotitem:generic_item>
// <cotblock:generic_block>

// Makes the item like a infinite duration flint and steel
// The on item use function is called whenever the item is used on a block.
// For more info, see documentation.
<cotitem:generic_item>.setOnItemUse((context) => {
    val pos = context.pos;
    val direction = context.face;
    val firePos = pos.offset(direction);
    val world = context.world;
    if (world.isAir(firePos)) {
        world.setBlockState(firePos, <blockstate:minecraft:fire>);
    }
    return "SUCCESS";
});
