package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceBracketExpressionParser;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.registry.CreativeTabRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.object.BlockReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MaterialColorReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ContentTweakerVanillaBrackets {
    private record ReferenceBracketData<T, U extends Reference<T>>(
            String name,
            ObjectType<T> type,
            String format,
            TypeToken<U> token,
            Function<ResourceLocation, String> converter,
            boolean doDump
    ) {}

    private static final Collection<ReferenceBracketData<?, ?>> BRACKETS = List.of(
            b("block", VanillaObjectTypes.BLOCK, null, new TypeToken<BlockReference>() {}, null, false),
            b("item", VanillaObjectTypes.ITEM, null, new TypeToken<ItemReference>() {}, null, false),
            b("material", VanillaObjectTypes.MATERIAL, null, new TypeToken<SimpleReference<Material>>() {}, null, true),
            b("materialcolor", VanillaObjectTypes.MATERIAL_COLOR, "material color", new TypeToken<MaterialColorReference>() {}, null, true),
            b("soundevent", VanillaObjectTypes.SOUND_EVENT, "sound event", new TypeToken<SimpleReference<SoundEvent>>() {}, null, true),
            b("soundtype", VanillaObjectTypes.SOUND_TYPE, "sound type", new TypeToken<SimpleReference<SoundType>>() {}, null, true),
            b("tooltier", VanillaObjectTypes.TIER, "tool tier", new TypeToken<TierReference>() {}, null, true)
    );

    private ContentTweakerVanillaBrackets() {}

    public static void register(final CustomBracketRegistration registration) {
        BRACKETS.forEach(data -> register(registration, data));
        registration.registerBracket("tab", new CreativeTabBracketExpressionParser(), BracketHelper.dumpAllOf("tab", VanillaObjectTypes.CREATIVE_TAB, CreativeTabRegistry::toId));
    }

    private static <T, U extends Reference<T>> void register(final CustomBracketRegistration registration, final ReferenceBracketData<T, U> data) {
        final String name = data.name();
        final ObjectType<T> type = data.type();

        final BracketExpressionParser parser = ReferenceBracketExpressionParser.of(data.format(), type, data.token());

        if (data.doDump()) {
            final Function<ResourceLocation, String> converter;
            final Supplier<Stream<String>> dumper = (converter = data.converter()) != null? BracketHelper.dumpAllOf(name, type, converter) : BracketHelper.dumpAllOf(name, type);

            registration.registerBracket(name, parser, dumper);
        } else {
            registration.registerBracket(name, parser);
        }
    }

    private static <T, U extends Reference<T>> ReferenceBracketData<T, U> b(
            final String name,
            final ObjectType<T> type,
            final String friendlyName,
            final TypeToken<U> token,
            @SuppressWarnings("SameParameterValue") final Function<ResourceLocation, String> conv,
            final boolean doDump
    ) {
        final String format = "Expected a " + (friendlyName == null? name : friendlyName) + " in the form <" + name + ":modid:name>, but found <" + name + ":%s>";
        return new ReferenceBracketData<>(name, type, format, token, conv, doDump);
    }
}
