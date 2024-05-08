package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.tab.CreativeTabBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".CreativeTabFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTabFactory implements ObjectFactory<CreativeModeTab> {
    private static final ClassArchitect<CreativeTabBuilder<?>> ARCHITECT = ClassArchitect.of(BiFunction.class);
    private static final BiFunction<ObjectHolder<? extends CreativeModeTab>, Consumer<ResourceManager>, CreativeTabReference> REGISTER = CreativeTabFactory::register;

    public CreativeTabFactory() {}

    private static CreativeTabReference register(final ObjectHolder<? extends CreativeModeTab> holder, final Consumer<ResourceManager> manager) {
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, manager));
        return CreativeTabReference.of(holder.id());
    }

    @Override
    public ObjectType<CreativeModeTab> type() {
        return VanillaObjectTypes.CREATIVE_TAB;
    }

    @ZenCodeType.Method("typed")
    public <T extends CreativeTabBuilder<T>> T typed(final Class<T> reifiedT) {
        return ARCHITECT.construct(reifiedT, REGISTER);
    }
}
