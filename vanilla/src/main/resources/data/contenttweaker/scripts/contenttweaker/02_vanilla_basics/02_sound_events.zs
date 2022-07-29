#loader contenttweaker

/**********************************************************************************************************************/
/*                                                    SOUND EVENTS                                                    */
/**********************************************************************************************************************/

// Yeah, I know, sound events are not really the most exciting thing to create or to use, but they are extremely simple
// and thus are perfect for us to deal with to get started. I strongly advise against skipping this section, unless you
// are already familiar with the API.

// Now, a sound event essentially represents something to which the game can associate one or more sounds. Anything that
// makes sound in the game is tied to a specific sound event. The player walking or swimming, music discs being played,
// zombies groaning, breaking a block... Each of these occurrences is tied to a specific sound event. The sound event
// is then tied to one or more sounds, as determined by the sound definitions file (`sounds.json`).
// Here, ContentTweaker is responsible solely for the creation of the sound event itself.

// As we have explained previously, to create something we all need a factory. The sound event factory can be obtained
// unsurprisingly through `<factory:sound_event>`.
// The factory exposes us a single method to create an event, asking us for an ID. The ID is a string that identifies
// the name we want to give to the event. It has to follow all resource location conventions.
// Last but not least, we have to `build` the created sound event. This will give us a reference to use later.
// Let me now show you some commented examples.

val electricBoogaloo = <factory:sound_event> // Let's grab the factory for creation, while also creating a variable
                                             // where we will store the reference after building.
    .event("electric.boogaloo") // This will start creating an event with an ID of "electric.boogaloo"
                                // Note the presence of a period (.) instead of a lowercase (_) to separate words:
                                // this is a convention that is **specific** to sound events.
    .build(); // And now we say that we want to build the sound event, confirming all properties we have specified.
              // This will queue the event for registration and give us a reference to the sound event.

// Here's another couple of examples:

<factory:sound_event>
    .event("generic.ambient.sentence.earth")
    .build("the_earth"); // Note how we specified another string in `build`. This means we want to register the event
                         // with a different name than the one we specified in the `event` method. While this is
                         // possible, you will still be referring to the event with the name specified in `event` most
                         // of the time. Specifying a different name is thus discouraged and only creates confusion.

<factory:sound_event>.event("custom.modpack.voice.question").build();

// Note how in both of those we haven't stored a reference to the built sound event. That is not going to impact the
// registration process: it is simply a way to optimize to avoid having variables around which we do not plan on using.
// Moreover, we can always build a reference ourselves (as explained previously) as required.

// Speaking of references, it is also possible to reference any sound event through the `soundevent` bracket handler, as
// follows.

val vanillaEvent = <soundevent:minecraft:entity.puffer_fish.blow_out>;
val customEvent = <soundevent:contenttweaker:custom.modpack.voice.question>;

<soundevent:contenttweaker:electrict.boogaloo>; // We can always reference stuff in-line, without requiring a variable.
                                                // This will come in handy if we need to pass a reference to a sound
                                                // event as a variable, but we do not have any already-made references
                                                // on-hand.

// All clear? Great, then it's time to move on to the next topic!
