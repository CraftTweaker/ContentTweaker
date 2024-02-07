package com.blamejared.contenttweaker.core.resource.trundle;

import com.dwarveddonuts.neverwinter.fs.FileSystemInjector;

public final class TrundleFileSystemProviderInjector {
    private TrundleFileSystemProviderInjector() {}

    public static void inject() {
        FileSystemInjector.injectFileSystem(new TrundleFileSystemProvider());
    }
}
