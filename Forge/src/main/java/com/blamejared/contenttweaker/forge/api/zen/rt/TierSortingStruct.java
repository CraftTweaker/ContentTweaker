package com.blamejared.contenttweaker.forge.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.rt.ResourceLocationNative;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
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

    @ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".Tier")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class TierCaster {
        private TierCaster() {}

        @ZenCodeType.Caster(implicit = true)
        public static TierSortingStruct asTierSortingStruct(final Tier $this) {
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
    private final Tier tier;

    private TierSortingStruct(final ResourceLocation rl, final String name, final Tier tier) {
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

    static TierSortingStruct of(final Tier tier) {
        return new TierSortingStruct(null, null, Objects.requireNonNull(tier));
    }

    public Object get() {
        return Stream.of(this.rl, this.name, this.tier).filter(Objects::nonNull).findFirst().orElseGet(Object::new);
    }
}
