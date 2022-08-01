#loader contenttweaker

/**********************************************************************************************************************/
/*                                                     THE BASICS                                                     */
/**********************************************************************************************************************/

// Let's start with the basics, shall we? Now, as you might have noticed, both of the scripts you have seen up until now
// started with `#loader contenttweaker` as their first line. The same applies to every other script you'll see in this
// series.
// This line is used to identify all scripts that should be loaded by ContentTweaker instead of CraftTweaker.
// Differently from other addons, like JEITweaker, for example, we require this line because ContentTweaker scripts run
// at a different time and provide a different set of functionalities from regular CraftTweaker scripts. For example,
// recipe editing is **NOT** supported in ContentTweaker scripts, due to timing issues. For the same reason, creation of
// custom content cannot occur outside scripts loaded with the ContentTweaker loader.
// It is also for this reason that `/reload` does **NOT** work with ContentTweaker scripts, so you have to resort to
// `/ct syntax` to check the script validity and a game restart to apply changes. Always make sure that your script
// properly compiles before starting the game, otherwise you might risk losing content you've previously created!

// Once we have established that, let's focus on what is still available: you can still print to the log like you'd
// normally would, and all library functions are also available. Loops and if statements also work the same way.

val exampleMessage = "Hello from the example ContentTweaker scripts! If you see this line, they are currently enabled";
println(exampleMessage);

for i in 0 .. 4 {
    if (i == 1) {
        println("You will see this message 1 more time");
    } else {
        println("You will see this message " + i + " more times");
    }
}

// You can check out the CraftTweaker log with `/ct log`: you'll notice the strings above appearing. Feel free to tinker
// around with them and see what the results are. Remember that to apply ContentTweaker scripts, you **have to** restart
// the game.

// The resource location bracket handler is also retained, allowing you to create resource locations the same way you
// would do with CraftTweaker:

<resource:minecraft:hello>;
