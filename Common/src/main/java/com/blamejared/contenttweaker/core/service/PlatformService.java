package com.blamejared.contenttweaker.core.service;

import java.nio.file.Path;

public interface PlatformService {
    Path gameDirectory();
    Path locateResource(final String... components);
}
