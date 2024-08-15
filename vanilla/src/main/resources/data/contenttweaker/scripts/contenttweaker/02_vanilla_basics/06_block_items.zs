#loader contenttweaker

import contenttweaker.builder.vanilla.item.BlockItem;

val alternativeDiamond = <factory:item>.typed<BlockItem>()
    .block(<block:minecraft:diamond_block>)
    .build("alternative_diamond_block_item");


<factory:item>.typed<BlockItem>()
    .block(<block:contenttweaker:manual_block>)
    .stacksTo(1)
    .craftRemainder(alternativeDiamond)
    .build("manual_block");


