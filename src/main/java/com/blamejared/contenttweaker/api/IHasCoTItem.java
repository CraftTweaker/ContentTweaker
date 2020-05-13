package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.api.items.*;

import javax.annotation.*;

public interface IHasCoTItem extends IHasMCResourceLocation {
    @Nonnull
    IIsCotItem getItem();
}
