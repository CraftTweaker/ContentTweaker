package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * <p> Internal interface used to create objects. Available instances all available through <code>/ct dump factorys</code></p>
 *
 * <p> As a convention, methods on implementing classes that create objects are exposed to the ZenCode engine under the name
 * <code>typed</code>. </p>
 *
 * <p> ObjectFactory <code>typed</code> methods take an explicit generic bound which indicates the type of the object that is meant to be
 * created. </p>
 *
 * <p>
 * An example can be seen here:
 * <pre>
 * <code class=language-zenscript>&lt;factory:sound_event&gt;
 * .typed&lt;VariableRangeEvent&gt;("generic.ambient.sentence.earth")
 * .build("the_earth");</code></pre>
 *
 * Scripters should not need to import or use this class at all.
 *
 */
@ZenCodeType.Name(ContentTweakerZenConstants.RT_PACKAGE + ".Factory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
@Document("mods/ContentTweaker/internal/ObjectFactory")
public interface ObjectFactory<T> {
    ObjectType<T> type();
}
