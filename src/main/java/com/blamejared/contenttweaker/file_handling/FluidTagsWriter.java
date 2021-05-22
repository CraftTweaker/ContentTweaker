package com.blamejared.contenttweaker.file_handling;

import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FluidTagsWriter {
    private final Stream<IIsCotFluid> waterFluids;
    private final Stream<IIsCotFluid> lavaFluids;
    private final File containingDirectory;
    private static final Predicate<IIsCotFluid> IS_MOLTEN_PREDICATE = IIsCotFluid::isMolten;

    public FluidTagsWriter(Collection<IIsCotFluid> fluids, File containingDirectory) {
        this.lavaFluids = fluids.stream().filter(IIsCotFluid::isTagged).filter(IS_MOLTEN_PREDICATE);
        this.waterFluids = fluids.stream().filter(IIsCotFluid::isTagged).filter(IS_MOLTEN_PREDICATE.negate());
        this.containingDirectory = containingDirectory;
    }

    public void write() {
        try {
            FileUtils.write(new File(containingDirectory, "data/minecraft/tags/fluids/water.json"), getFluidsJson(waterFluids), StandardCharsets.UTF_8);
            FileUtils.write(new File(containingDirectory, "data/minecraft/tags/fluids/lava.json"), getFluidsJson(lavaFluids), StandardCharsets.UTF_8);
        } catch (IOException e) {
            CraftTweakerAPI.logThrowing("Could not write fluid tags json!", e);
        }
    }

    private String getFluidsJson(Stream<IIsCotFluid> fluids) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);
        JsonArray jsonArray = new JsonArray();
        fluids.forEach(fluid -> jsonArray.add(fluid.getRegistryNameNonNull().toString()));
        root.add("values", jsonArray);
        return gson.toJson(root);
    }
}
