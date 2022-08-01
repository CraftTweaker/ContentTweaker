package com.blamejared.contenttweaker.forge.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.rt.ResourceLocationNative;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".TierSortingStruct")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierSortingStruct {

    @ZenCodeType.Expansion(ResourceLocationNative.CLASS_NAME)
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class ResourceLocationCaster {
        private ResourceLocationCaster() {}

        @ZenCodeType.Caster(implicit = true)
        public static TierSortingStruct asTierSortingStruct(final ResourceLocation $this) {
            return TierSortingStruct.of($this);
        }
    }

    @ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".TierReference")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class TierCaster {
        private TierCaster() {}

        @ZenCodeType.Caster(implicit = true)
        public static TierSortingStruct asTierSortingStruct(final TierReference $this) {
            return TierSortingStruct.of($this);
        }
    }

    @ZenCodeType.Expansion("string")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class StringCaster {
        private StringCaster() {}

        @ZenCodeType.Caster(implicit = true)
        public static TierSortingStruct asTierSortingStruct(final String $this) {
            return TierSortingStruct.of($this);
        }
    }

    private final ResourceLocation rl;
    private final String name;
    private final TierReference tier;

    private TierSortingStruct(final ResourceLocation rl, final String name, final TierReference tier) {
        this.rl = rl;
        this.name = name;
        this.tier = tier;
    }

    static TierSortingStruct of(final ResourceLocation rl) {
        return new TierSortingStruct(Objects.requireNonNull(rl), null, null);
    }

    static TierSortingStruct of(final String name) {
        return new TierSortingStruct(null, Objects.requireNonNull(name), null);
    }

    static TierSortingStruct of(final TierReference tier) {
        return new TierSortingStruct(null, null, Objects.requireNonNull(tier));
    }

    public Supplier<Object> get() {
        return () -> Stream.of(this.rl, this.name, Optional.ofNullable(this.tier).map(TierReference::get).orElse(null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseGet(Object::new);
    }
}
