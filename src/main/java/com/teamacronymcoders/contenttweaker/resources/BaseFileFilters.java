package com.teamacronymcoders.contenttweaker.resources;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.FileFilter;

public class BaseFileFilters {
    public static FileFilter jsonFilter = FileFilterUtils.suffixFileFilter(".json");
    public static FileFilter langFilter = FileFilterUtils.suffixFileFilter(".lang");
    public static IOFileFilter pngFilter = FileFilterUtils.suffixFileFilter(".png");
    public static IOFileFilter mcmetaFilter = FileFilterUtils.suffixFileFilter(".mcmeta");
    public static IOFileFilter textureFilter = FileFilterUtils.or(pngFilter, mcmetaFilter);
}
