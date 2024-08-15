#loader contenttweaker

import contenttweaker.builder.vanilla.item.Simple;
import contenttweaker.object.vanilla.property.FoodItemProperties;
import contenttweaker.object.vanilla.property.StandardItemProperties;

val veryBasicItem = <factory:item>.typed<Simple>()
    .rarity(<constant:minecraft:item/rarity:uncommon>)
    .build("very_basic_item");

<factory:item>.typed<Simple>()
    .nutrition(3)
    .saturationMod(2.5f)
    .canAlwaysEat(true)
    .build("mori_mori");

val bucketProperties = <item:minecraft:water_bucket>.findProperties<StandardItemProperties>();
val carrotProperties = <item:minecraft:carrot>.findProperties<FoodItemProperties>();

<factory:item>.typed<Simple>().cloning(bucketProperties, carrotProperties).build("carrot_bucket");

<factory:item>.typed<Simple>()
    .cloning(veryBasicItem.properties)
    .rarity(<constant:minecraft:item/rarity:epic>)
    .build("epic_basic_item");


