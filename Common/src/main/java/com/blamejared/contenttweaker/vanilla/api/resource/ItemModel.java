package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector4f;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ItemModel {
    public enum GuiLight {
        FRONT("front"),
        SIDE("side");

        private final String id;

        GuiLight(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public enum DisplayPosition {
        THIRD_RIGHT("thirdperson_righthand"),
        THIRD_LEFT("thirdperson_lefthand"),
        FIRST_RIGHT("firstperson_righthand"),
        FIRST_LEFT("firstperson_lefthand"),
        GUI("gui"),
        HEAD("head"),
        GROUND("ground"),
        FIXED("fixed");

        private final String id;

        DisplayPosition(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public enum RotationAxis {
        X("x"),
        Y("y"),
        Z("z");

        private final String id;

        RotationAxis(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public enum FacePosition {
        DOWN("down"),
        UP("up"),
        NORTH("north"),
        SOUTH("south"),
        WEST("west"),
        EAST("east");

        private final String id;

        FacePosition(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }

    public static final class ElementBuilder {
        private final ItemModel parent;
        private final Vector3d from;
        private final Vector3d to;
        private final boolean shade;
        private final Map<FacePosition, Element.Face> faces;

        private Element.Rotation rotation;

        ElementBuilder(final ItemModel parent, final Vector3d from, final Vector3d to, final boolean shade) {
            this.parent = parent;
            this.from = from;
            this.to = to;
            this.shade = shade;
            this.faces = new HashMap<>();
            this.rotation = null;
        }

        public ElementBuilder rotate(final Vector3d origin, final RotationAxis axis, float angle) {
            return this.rotate(origin, axis, angle, false);
        }

        public ElementBuilder rotate(final Vector3d origin, final RotationAxis axis, float angle, boolean rescale) {
            Objects.requireNonNull(origin);
            Objects.requireNonNull(axis);
            if (angle != -45 && angle != -22.5 && angle != 0 && angle != 22.5 && angle != 45) {
                throw new IllegalArgumentException("Invalid angle for rotation " + angle);
            }
            this.rotation = new Element.Rotation(origin, axis, angle, rescale);
            return this;
        }

        public ElementBuilder face(final FacePosition position, final String texture) {
            return this.face(position, null, texture);
        }

        public ElementBuilder face(final FacePosition position, final Vector4f uvs, final String texture) {
            return this.face(position, uvs, texture, null);
        }

        public ElementBuilder face(final FacePosition position, final Vector4f uvs, final String texture, final FacePosition cull) {
            return this.face(position, uvs, texture, cull, 0);
        }

        public ElementBuilder face(final FacePosition position, final Vector4f uvs, final String texture, final int rotation) {
            return this.face(position, uvs, texture, null, rotation);
        }

        public ElementBuilder face(final FacePosition position, final Vector4f uvs, final String texture, final FacePosition cull, final int rotation) {
            return this.face(position, uvs, texture, cull, rotation, null);
        }

        public ElementBuilder face(final FacePosition position, final Vector4f uvs, final String texture, final FacePosition cull, final int rotation, final Integer tintIndex) {
            Objects.requireNonNull(position);
            Objects.requireNonNull(texture);
            if (rotation != 0 && rotation != 90 && rotation != 180 && rotation != 270) {
                throw new IllegalArgumentException("Invalid angle for rotation " + rotation);
            }
            if (this.faces.containsKey(position)) {
                throw new IllegalArgumentException("Face for position " + position + " was already specified");
            }
            this.faces.put(position, new Element.Face(uvs, texture, cull, rotation, tintIndex));
            return this;
        }

        public ItemModel finish() {
            return this.parent.element(new Element(this.from, this.to, this.rotation, this.shade, this.faces));
        }
    }

    public static final class OverrideBuilder {
        private final ItemModel parent;
        private final ResourceLocation model;
        private final List<ModelOverride.Predicate> predicates;

        OverrideBuilder(final ItemModel parent, final ResourceLocation model) {
            this.parent = parent;
            this.model = model;
            this.predicates = new ArrayList<>();
        }

        public OverrideBuilder when(final String name, final float value) {
            Objects.requireNonNull(name);
            this.predicates.add(new ModelOverride.Predicate(name, value));
            return this;
        }

        public ItemModel finish() {
            return this.parent.override(new ModelOverride(this.predicates, this.model));
        }
    }

    @FunctionalInterface
    private interface VectorSerializer<T> {
        void serializeVector(final JsonObject object, final String propertyName, final T vector);
    }

    private record DisplayData(Vector3d rotation, Vector3d translation, Vector3d scale) {}

    private record Element(Vector3d from, Vector3d to, Rotation rotation, boolean shade, Map<FacePosition, Face> faces) {
        record Face(Vector4f uv, String texture, FacePosition cull, int rotation, Integer tintIndex) {
            JsonElement serialize(final VectorSerializer<Vector4f> fourSerializer) {
                final JsonObject object = new JsonObject();
                fourSerializer.serializeVector(object, "uv", this.uv);
                object.addProperty("texture", "#%s".formatted(this.texture()));
                if (this.cull() != null) {
                    object.addProperty("cullface", this.cull().toString());
                }
                object.addProperty("rotation", this.rotation());
                if (this.tintIndex() != null) {
                    object.addProperty("tintindex", this.tintIndex());
                }
                return object;
            }
        }

        record Rotation(Vector3d origin, RotationAxis axis, float angle, boolean scale) {}

        JsonElement serialize(final VectorSerializer<Vector3d> threeSerializer, final VectorSerializer<Vector4f> fourSerializer) {
            final JsonObject object = new JsonObject();
            threeSerializer.serializeVector(object, "from", this.from());
            threeSerializer.serializeVector(object, "to", this.to());
            if (this.rotation() != null) {
                final JsonObject rotation = new JsonObject();
                threeSerializer.serializeVector(rotation, "origin", this.rotation().origin());
                rotation.addProperty("axis", this.rotation().axis().toString());
                rotation.addProperty("angle", this.rotation().angle());
                rotation.addProperty("rescale", this.rotation().scale());
                object.add("rotation", rotation);
            }
            object.addProperty("shade", this.shade());
            if (!this.faces().isEmpty()) {
                final JsonObject faces = new JsonObject();
                this.faces().forEach((position, face) -> faces.add(position.toString(), face.serialize(fourSerializer)));
                object.add("faces", faces);
            }
            return object;
        }
    }

    private record ModelOverride(List<Predicate> predicates, ResourceLocation model) {
        record Predicate(String name, float value) {}

        JsonElement serialize() {
            final JsonObject object = new JsonObject();
            final JsonObject predicates = new JsonObject();
            this.predicates().forEach(predicate -> predicates.addProperty(predicate.name(), predicate.value()));
            object.add("predicate", predicates);
            object.addProperty("model", this.model().toString());
            return object;
        }
    }

    public static final ResourceSerializer<ItemModel> SERIALIZER = ItemModel::serialize;

    private final ResourceLocation parent;
    private final Map<DisplayPosition, DisplayData> positions;
    private final Map<String, ResourceLocation> textures;
    private final List<Element> elements;
    private final List<ModelOverride> overrides;
    private GuiLight light;

    private ItemModel(final ResourceLocation parent) {
        this.parent = parent;
        this.positions = new HashMap<>();
        this.textures = new HashMap<>();
        this.elements = new ArrayList<>();
        this.overrides = new ArrayList<>();
        this.light = GuiLight.SIDE;
    }

    public static ItemModel of(final ResourceLocation parent) {
        return new ItemModel(parent);
    }

    public static ItemModel ofGenerated() {
        return new ItemModel(new ResourceLocation("item/generated"));
    }

    public ItemModel display(final DisplayPosition position, final Vector3d rotation, final Vector3d translation, final Vector3d scale) {
        Objects.requireNonNull(position);
        if (rotation == null && translation == null && scale == null) {
            throw new IllegalArgumentException("At least one between rotation, translation, and scale must be set");
        }
        if (this.positions.containsKey(position)) {
            throw new IllegalArgumentException("Position " + position + " is already set");
        }
        this.positions.put(position, new DisplayData(this.clone(rotation), this.clone(translation), this.clone(scale)));
        return this;
    }

    public ItemModel guiLight(final GuiLight light) {
        this.light = Objects.requireNonNull(light);
        return this;
    }

    public ElementBuilder element(final Vector3d from, final Vector3d to) {
        return this.element(from, to, true);
    }

    public ElementBuilder element(final Vector3d from, final Vector3d to, final boolean shade) {
        return new ElementBuilder(this, Objects.requireNonNull(from), Objects.requireNonNull(to), shade);
    }

    public ItemModel texture(final String name, final ResourceLocation path) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(path);
        if (this.textures.containsKey(name)) {
            throw new IllegalArgumentException("Texture was already specified for " + name);
        }
        this.textures.put(name, path);
        return this;
    }

    public ItemModel particle(final ResourceLocation path) {
        return this.texture("particle", path);
    }

    public ItemModel layer(final int id, final ResourceLocation path) {
        if (id < 0) {
            throw new IllegalStateException("Invalid id " + id + " for layer");
        }
        return this.texture("layer" + id, path);
    }

    public OverrideBuilder override(final ResourceLocation model) {
        return new OverrideBuilder(this, Objects.requireNonNull(model));
    }


    private ItemModel element(final Element element) {
        this.elements.add(element);
        return this;
    }

    private ItemModel override(final ModelOverride override) {
        this.overrides.add(override);
        return this;
    }

    private Vector3d clone(final Vector3d original) {
        return original != null? new Vector3d(original.x, original.y, original.z) : null;
    }

    private byte[] serialize() {
        final JsonObject object = new JsonObject();
        if (this.parent != null) {
            object.addProperty("parent", this.parent.toString());
        }
        if (!this.positions.isEmpty()) {
            final JsonObject positions = new JsonObject();
            this.positions.forEach((position, data) -> {
                final JsonObject jsonData = new JsonObject();
                this.serializeVector(jsonData, "rotation", data.rotation());
                this.serializeVector(jsonData, "translation", data.translation());
                this.serializeVector(jsonData, "scale", data.scale());
                positions.add(position.toString(), jsonData);
            });
            object.add("display", positions);
        }
        if (!this.textures.isEmpty()) {
            final JsonObject textures = new JsonObject();
            this.textures.forEach((name, id) -> textures.addProperty(name, id.toString()));
            object.add("textures", textures);
        }
        object.addProperty("gui_light", this.light.toString());
        if (!this.elements.isEmpty()) {
            final JsonArray elements = new JsonArray();
            this.elements.forEach(element -> elements.add(element.serialize(this::serializeVector, this::serializeVector)));
            object.add("elements", elements);
        }
        if (!this.overrides.isEmpty()) {
            final JsonArray overrides = new JsonArray();
            this.overrides.forEach(override -> overrides.add(override.serialize()));
            object.add("overrides", overrides);
        }
        return StandardResourceSerializers.JSON.serialize(object);
    }

    private void serializeVector(final JsonObject object, final String name, final Vector3d vector) {
        if (vector == null) return;
        final JsonArray array = new JsonArray();
        array.add(new JsonPrimitive(vector.x));
        array.add(new JsonPrimitive(vector.y));
        array.add(new JsonPrimitive(vector.z));
        object.add(name, array);
    }

    private void serializeVector(final JsonObject object, final String name, final Vector4f vector) {
        if (vector == null) return;
        final JsonArray array = new JsonArray();
        array.add(new JsonPrimitive(vector.x()));
        array.add(new JsonPrimitive(vector.y()));
        array.add(new JsonPrimitive(vector.z()));
        array.add(new JsonPrimitive(vector.w()));
        object.add(name, array);
    }
}
