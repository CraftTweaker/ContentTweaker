package com.blamejared.contenttweaker.api.resources;

import com.blamejared.contenttweaker.*;
import com.blamejared.crafttweaker.impl.util.*;
import it.unimi.dsi.fastutil.bytes.*;
import net.minecraft.util.*;

import java.io.*;

public class WriteableResourceImage extends WriteableResource {
    
    public WriteableResourceImage(ImageType imageType, MCResourceLocation location) {
        super(ResourceType.ASSETS, FileExtension.PNG, location, "textures", imageType.getFolderName());
    }
    
    public WriteableResourceImage(MCResourceLocation location) {
        super(ResourceType.ASSETS, FileExtension.PNG, location, "textures");
    }
    
    /**
     * Creates the default image (red X on white background) for the given location/type
     */
    public static WriteableResourceImage noImage(ImageType imageType, MCResourceLocation location) {
        return new WriteableResourceImage(imageType, location).setImageToCopy(new ResourceLocation(ContentTweaker.MOD_ID, "textures/generic/no_image"));
    }
    
    public WriteableResourceImage setImageToCopy(ResourceLocation location) {
        final String format = "/assets/%s/templates/%s.png";
        final String path = String.format(format, location.getNamespace(), location.getPath());
        final InputStream resourceAsStream = WriteableResourceImage.class.getResourceAsStream(path);
        if(resourceAsStream == null) {
            ContentTweaker.LOG.error("Invalid Template resource: '" + path + '\'');
            withContent(new byte[0]);
            return this;
        }
        
        final ByteArrayList out = new ByteArrayList();
        //Try-with resources to make sure stream is closed properly
        try(final InputStream stream = resourceAsStream) {
            do {
                int b = stream.read();
                if(b == -1) {
                    break;
                }
                out.add((byte) b);
            } while(true);
        } catch(IOException e) {
            ContentTweaker.LOG.warn("Could not read image at " + location + ":", e);
        }
        this.withContent(out.toByteArray());
        return this;
    }
}
