#loader contenttweaker

import contenttweaker.builder.vanilla.sound.SoundEventBuilder;
import contenttweaker.builder.vanilla.sound.FixedRangeEvent;
import contenttweaker.builder.vanilla.sound.VariableRangeEvent;

val electricBoogaloo = <factory:sound_event>
    .typed<FixedRangeEvent>("electric.boogaloo")
    .range(16.0)
    .build();

// Here's another couple of examples:

<factory:sound_event>
    .typed<VariableRangeEvent>("generic.ambient.sentence.earth")
    .build("the_earth");

<factory:sound_event>
.typed<VariableRangeEvent>("custom.modpack.voice.question")
.build();

val vanillaEvent = <soundevent:minecraft:entity.puffer_fish.blow_out>;
val customEvent = <soundevent:contenttweaker:custom.modpack.voice.question>;

<soundevent:contenttweaker:electric.boogaloo>;
