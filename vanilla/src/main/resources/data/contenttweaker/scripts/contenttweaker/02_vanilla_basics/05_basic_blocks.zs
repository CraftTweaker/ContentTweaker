#loader contenttweaker

import contenttweaker.builder.vanilla.block.Cube;
import contenttweaker.object.vanilla.property.StandardBlockProperties;

val veryBasicBlock = <factory:block>.typed<Cube>()
    .lightLevel(14)
    .build("very_basic_block");

val dirtProperties = <block:minecraft:dirt>.findProperties<StandardBlockProperties>();

<factory:block>.typed<Cube>().cloning(dirtProperties).build("our_own_dirt_clone");

<factory:block>.typed<Cube>()
    .cloning(veryBasicBlock.properties)
    .strength(5000.0F)
    .build("enhanced_basic_block");

<factory:block>.typed<Cube>()
    .cloning(<block:contenttweaker:enhanced_basic_block>.properties)
    .build("enhanced_and_visible_basic_block");


<factory:block>.typed<Cube>()
    .noCorrespondingItem()
    .dropsLike(<block:minecraft:dirt>)
    .build("lone_block");

<factory:block>.typed<Cube>()
    .noCorrespondingItem()
    .dropsItselfRegardless()
    .build("manual_block");

