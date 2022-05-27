package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class RuntimeResourceDumpingCommand {

    private static final class ResourceVisitor extends SimpleFileVisitor<Path> {
        private final Path root;
        private final ResourceFragment.Key key;
        private final Consumer<Path> pathConsumer;

        ResourceVisitor(final Path root, final ResourceFragment.Key key, final Consumer<String> stringConsumer) {
            this.root = root;
            this.key = key;
            this.pathConsumer = path -> this.pathToName(path, stringConsumer);
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            final FileVisitResult result = super.visitFile(file, attrs);
            if (result != FileVisitResult.CONTINUE) {
                return result;
            }
            if (!attrs.isRegularFile()) {
                return FileVisitResult.CONTINUE;
            }
            final DosFileAttributes attributes = Files.readAttributes(file, DosFileAttributes.class);
            if (attributes.isHidden()) {
                return FileVisitResult.CONTINUE;
            }
            this.pathConsumer.accept(file);
            return FileVisitResult.CONTINUE;
        }

        private void pathToName(final Path path, final Consumer<String> stringConsumer) {
            final Path targetPath = this.root.relativize(path.toAbsolutePath().normalize()).normalize();
            stringConsumer.accept("%s/%s/%s".formatted(this.key.type().getDirectory(), this.key.id(), targetPath));
        }
    }

    private RuntimeResourceDumpingCommand() {}

    public static void register(final ICommandRegistrationHandler handler) {
        handler.registerDump(
                "contenttweaker_resources",
                new TranslatableComponent(ContentTweakerConstants.ln("command.dump_resources.desc")),
                it -> it.requires(p -> p.hasPermission(2)).executes(RuntimeResourceDumpingCommand::execute)
        );
    }

    private static int execute(final CommandContext<CommandSourceStack> context) {
        Arrays.stream(PackType.values())
                .map(ContentTweakerCore.core().resourceManager()::fragments)
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(RuntimeResourceDumpingCommand::dumpFragment)
                .flatMap(Collection::stream)
                .forEach(CraftTweakerAPI.LOGGER::info);

        CommandUtilities.send(
                CommandUtilities.openingLogFile(
                        new TranslatableComponent(
                                "crafttweaker.command.list.check.log",
                                CommandUtilities.makeNoticeable(new TranslatableComponent(ContentTweakerConstants.ln("command.dump_resources.log"))),
                                CommandUtilities.getFormattedLogFile()
                        ).withStyle(ChatFormatting.GREEN)),
                context.getSource()
        );

        return 1;
    }

    private static List<String> dumpFragment(final RuntimeFragment fragment) {
        final ResourceFragment.Key key = fragment.key();
        final List<String> outer = new ArrayList<>();
        fragment.fs()
                .getRootDirectories()
                .forEach(root -> dumpRoot(root, outer, key));
        return outer;
    }

    private static void dumpRoot(final Path root, final List<String> outer, final ResourceFragment.Key key) {
        try {
            Files.walkFileTree(root, new ResourceVisitor(root, key, outer::add));
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
