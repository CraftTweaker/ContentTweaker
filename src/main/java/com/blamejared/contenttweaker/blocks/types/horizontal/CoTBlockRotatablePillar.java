package com.blamejared.contenttweaker.blocks.types.horizontal;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;

import javax.annotation.*;
import java.util.*;

final class CoTBlockRotatablePillar extends RotatedPillarBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final MCResourceLocation end;
    private final MCResourceLocation sides;
    
    public CoTBlockRotatablePillar(BuilderPillarRotatable builderPillarRotatable, MCResourceLocation location) {
        super(builderPillarRotatable.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location.getInternal());
        item = new CoTBlockItem(this, builderPillarRotatable.getBlockBuilder().getItemProperties());
        end = builderPillarRotatable.getEnd(location);
        sides = builderPillarRotatable.getSides(location);
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final Collection<WriteableResource> out = new ArrayList<>();
        out.add(new WriteableResourceImage(ImageType.BLOCK, end));
        if(!end.equals(sides)) {
            out.add(new WriteableResourceImage(ImageType.BLOCK, sides));
        }
        
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "models/block")
                .withContent("{\n" + "    \"parent\": \"block/cube_column\",\n" + "    \"textures\": {\n" + "        \"end\": \"%s:block/%s\",\n" + "        \"side\": \"%s:block/%s\"\n" + "    }\n" + "}", end
                        .getNamespace(), end.getPath(), sides.getNamespace(), sides.getPath()));
        
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "blockstates")
                .withContent("{\n" + "    \"variants\": {\n" + "        \"axis=y\":  { \"model\": \"%1$s:block/%2$s\" },\n" + "        \"axis=z\":   { \"model\": \"%1$s:block/%2$s\", \"x\": 90 },\n" + "        \"axis=x\":   { \"model\": \"%1$s:block/%2$s\", \"x\": 90, \"y\": 90 }\n" + "    }\n" + "}", location
                        .getNamespace(), location.getPath()));
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getMCResourceLocation()));
    }
}
