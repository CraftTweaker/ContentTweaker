package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class SoundDefinition {
    public enum SoundType {
        SOUND("file"),
        EVENT("event");

        private final String id;

        SoundType(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public static final class SoundBuilder {
        private final Function<Sound, DefinitionBuilder> builder;
        private final ResourceLocation name;
        private SoundType type;
        private Float volume;
        private Float pitch;
        private Integer weight;
        private Boolean stream;
        private Integer attenuation;
        private Boolean preload;

        SoundBuilder(final ResourceLocation name, final Function<Sound, DefinitionBuilder> builder) {
            this.builder = builder;
            this.name = name;
            this.type = null;
            this.volume = null;
            this.pitch = null;
            this.weight = null;
            this.stream = null;
            this.attenuation = null;
            this.preload = null;
        }

        public SoundBuilder type(final SoundType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public SoundBuilder volume(final float volume) {
            this.volume = volume;
            return this;
        }

        public SoundBuilder pitch(final float pitch) {
            this.pitch = pitch;
            return this;
        }

        public SoundBuilder weight(final int weight) {
            this.weight = weight;
            return this;
        }

        public SoundBuilder attenuationDistance(final int attenuation) {
            this.attenuation = attenuation;
            return this;
        }

        public SoundBuilder preload() {
            this.preload = true;
            return this;
        }

        public SoundBuilder stream() {
            this.stream = true;
            return this;
        }

        public DefinitionBuilder finish() {
            final SoundType type = this.type == null? SoundType.SOUND : this.type;
            final float volume = this.volume == null? 1.0F : this.volume;
            final float pitch = this.pitch == null? 1.0F : this.pitch;
            final int weight = this.weight == null? 1 : this.weight;
            final boolean stream = this.stream != null && this.stream;
            final int attenuation = this.attenuation == null? 16 : this.attenuation;
            final boolean preload = this.preload != null && this.preload;
            return this.builder.apply(new Sound(this.name, type, volume, pitch, weight, stream, attenuation, preload));
        }
    }

    public static final class DefinitionBuilder {
        private final Function<Definition, SoundDefinition> definition;
        private final List<Sound> sounds;
        private String subtitle;

        DefinitionBuilder(final Function<Definition, SoundDefinition> definition) {
            this.definition = definition;
            this.sounds = new ArrayList<>();
            this.subtitle = null;
        }

        public DefinitionBuilder subtitle(final String subtitle) {
            Objects.requireNonNull(subtitle);
            this.subtitle = subtitle;
            return this;
        }

        public SoundBuilder sound(final ResourceLocation name) {
            Objects.requireNonNull(name);
            return new SoundBuilder(name, sound -> Util.make(this, def -> def.sounds.add(sound)));
        }

        public SoundDefinition finish() {
            if (this.sounds.isEmpty()) {
                throw new IllegalStateException("No sounds");
            }
            return this.definition.apply(new Definition(this.sounds, this.subtitle));
        }
    }

    private record Sound(ResourceLocation name, SoundType type, float volume, float pitch, int weight, boolean stream, int attenuation, boolean preload) {
        JsonElement serialize() {
            final JsonObject sound = new JsonObject();
            sound.addProperty("name", this.name().toString());
            sound.addProperty("type", this.type().toString());
            sound.addProperty("volume", this.volume());
            sound.addProperty("pitch", this.pitch());
            sound.addProperty("weight", this.weight());
            sound.addProperty("stream", this.stream());
            sound.addProperty("preload", this.preload());
            sound.addProperty("attenuation_distance", this.attenuation());
            return sound;
        }
    }

    private record Definition(List<Sound> sounds, String subtitle) {
        JsonElement serialize() {
            final JsonObject definition = new JsonObject();
            if (this.subtitle() != null) {
                definition.addProperty("subtitle", this.subtitle());
            }
            final JsonArray sounds = new JsonArray();
            this.sounds().stream().map(Sound::serialize).forEach(sounds::add);
            definition.add("sounds", sounds);
            return definition;
        }
    }

    public static final ResourceSerializer<SoundDefinition> SERIALIZER = SoundDefinition::serialize;

    private final Map<String, Definition> definitions;

    private SoundDefinition() {
        this.definitions = new LinkedHashMap<>();
    }

    public static SoundDefinition of() {
        return new SoundDefinition();
    }

    public DefinitionBuilder add(final String name) {
        if (this.definitions.containsKey(Objects.requireNonNull(name))) {
            throw new IllegalArgumentException("Definition for " + name + " already exists");
        }
        return new DefinitionBuilder(it -> Util.make(this, def -> def.definitions.put(name, it)));
    }

    public DefinitionBuilder add(final ResourceLocation name) {
        return this.add(Objects.requireNonNull(name).getPath());
    }

    private byte[] serialize() {
        final JsonObject object = new JsonObject();
        this.definitions.forEach((name, definition) -> object.add(name, definition.serialize()));
        return StandardResourceSerializers.JSON.serialize(object);
    }
}
