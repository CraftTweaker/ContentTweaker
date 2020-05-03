package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.api.resources.*;

import javax.annotation.*;
import java.util.*;

public interface IHasResourcesToWrite {
    
    @Nonnull
    Collection<WriteableResource> getResourcePackResources();
    
    @Nonnull
    Collection<WriteableResource> getDataPackResources();
    
    
}
