package com.blamejared.contenttweaker.core.resource.trundle;

import org.jetbrains.annotations.Nullable;

record TrundlePathResolutionResult(TrundlePath originalPath, TrundleResource parent, String elementName, @Nullable TrundleResource element) {}
