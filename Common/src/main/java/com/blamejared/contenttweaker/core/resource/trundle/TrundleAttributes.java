package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

final class TrundleAttributes {
    private TrundleAttributes() {}

    static boolean hidden(final TrundlePathResolutionResult path) throws IOException {
        return path.originalPath().attributes(DosFileAttributes.class).isHidden();
    }

    static <A extends BasicFileAttributes> A attributes(
            final TrundlePathResolutionResult path,
            final Class<A> type,
            final LinkOption... options
    ) throws IOException {
        final TrundlePath original = path.originalPath();

        if (type == BasicFileAttributes.class) {
            return reinterpret(original.fileAttributeView(BasicFileAttributeView.class, options).readAttributes());
        } else if (type == DosFileAttributes.class) {
            return reinterpret(original.fileAttributeView(DosFileAttributeView.class, options).readAttributes());
        } else {
            throw new UnsupportedOperationException("Attributes of type " + type.getName() + " are not supported");
        }
    }

    static Map<String, Object> attributes(
            final TrundlePathResolutionResult path,
            final String attributes,
            final LinkOption... options
    ) throws IOException {
        final TrundlePath origin = path.originalPath();

        final int colon;
        final String view = (colon = attributes.indexOf(':')) != -1? attributes.substring(0, colon) : "basic";

        if (!origin.fileStore().supportsFileAttributeView(view)) {
            throw new UnsupportedOperationException("View " + view + " is not available");
        }

        final String[] attributeList = (colon != -1? attributes.substring(colon + 1) : attributes).split(Pattern.quote(","));
        final int listLength = attributeList.length;
        final boolean isWildcard = listLength == 1 && "*".equals(attributeList[0]);
        if (listLength == 0) {
            throw new IllegalArgumentException("At least one attribute must be specified");
        }

        final Collection<String> allChosenAttributes = isWildcard? TrundleFileAttributes.SUPPORTED_ATTRIBUTES : Arrays.asList(attributeList);
        final TrundleFileAttributes dos = (TrundleFileAttributes) origin.attributes(DosFileAttributes.class, options);

        final Map<String, Object> attributesMap = new HashMap<>();
        for (final String attribute : allChosenAttributes) {
            if (!TrundleFileAttributes.SUPPORTED_ATTRIBUTES.contains(attribute)) {
                throw new IllegalArgumentException();
            }
            final Object value = dos.attribute(attribute);
            attributesMap.put(attribute, value);
        }

        return attributesMap;
    }

    static <V extends FileAttributeView> V attributeView(
            final TrundlePathResolutionResult path,
            final Class<V> type,
            @SuppressWarnings("unused") final LinkOption... options
    ) {
        final TrundleResource resource = path.element();
        if (resource == null) {
            return null;
        }

        if (type == BasicFileAttributeView.class || type == DosFileAttributeView.class) {
            return reinterpret(TrundleFileAttributesView.of(resource, type));
        }

        return null;
    }

    static Void attribute(
            final TrundlePathResolutionResult path,
            final String attribute,
            final Object value,
            final LinkOption... options
    ) throws IOException {
        final TrundlePath origin = path.originalPath();

        final int colon;
        final String view = (colon = attribute.indexOf(':')) != -1? attribute.substring(0, colon) : "basic";

        if (!origin.fileStore().supportsFileAttributeView(view)) {
            throw new UnsupportedOperationException("View " + view + " is not available");
        }

        final String targetAttribute = colon != -1? attribute.substring(colon + 1) : attribute;

        if (!TrundleFileAttributes.SUPPORTED_ATTRIBUTES.contains(targetAttribute)) {
            throw new IllegalArgumentException("Unknown attribute " + targetAttribute);
        }

        final TrundleFileAttributes dos = (TrundleFileAttributes) origin.attributes(DosFileAttributes.class, options);
        dos.attribute(targetAttribute, value);

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T, U> U reinterpret(final T t) {
        return (U) t;
    }
}
