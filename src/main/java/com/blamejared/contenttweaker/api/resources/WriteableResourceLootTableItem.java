package com.blamejared.contenttweaker.api.resources;

import com.blamejared.crafttweaker.impl.util.*;

public class WriteableResourceLootTableItem extends WriteableResource {
    
    public WriteableResourceLootTableItem(MCResourceLocation block) {
        super(ResourceType.DATA, FileExtension.JSON, block, "loot_tables", "blocks");
        this.withContent("{\n" + "  \"type\": \"minecraft:block\",\n" + "  \"pools\": [\n" + "    {\n" + "      \"rolls\": 1,\n" + "      \"entries\": [\n" + "        {\n" + "          \"type\": \"minecraft:item\",\n" + "          \"name\": \"%s\"\n" + "        }\n" + "      ],\n" + "      \"conditions\": [\n" + "        {\n" + "          \"condition\": \"minecraft:survives_explosion\"\n" + "        }\n" + "      ]\n" + "    }\n" + "  ]\n" + "}", block
                .getInternal()
                .toString());
    }
    
    @Override
    public void onWrite() {
    }
}
