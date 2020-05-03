package com.blamejared.contenttweaker.api.resources;

import com.blamejared.crafttweaker.impl.util.*;

import java.util.*;

public class WriteableResourceImage extends WriteableResource {
    
    private static final byte[] NO_ICON = Base64.getDecoder()
            .decode("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAACgSURBVDhPhZLRCYAwDAWD3/lwCJGuILj/Kg7gAPE10dK0IR4Fo96BLZIwy3nKfUsOBGjMJKUI0U9jNrRSSK7rp+lsyFQfJY238UADEDaTDb4ADE1kgy4ArTmOuiYb+ADg9b5XFQuDt8FCAzjpdX1nDLgdeEOjffe21YWhPwOlC4Zdtv345gvCM4kaDULbmBrKbMM3eklso2v0985twxrmBzQGNQUhq3LZAAAAAElFTkSuQmCC");
    
    public WriteableResourceImage(ImageType imageType, MCResourceLocation location) {
        super(ResourceType.ASSETS, FileExtension.PNG, location, "textures", imageType.getFolderName());
        this.withContent(NO_ICON);
    }
    
    public WriteableResourceImage(MCResourceLocation location) {
        super(ResourceType.ASSETS, FileExtension.PNG, location, "textures");
        this.withContent(NO_ICON);
    }
}
