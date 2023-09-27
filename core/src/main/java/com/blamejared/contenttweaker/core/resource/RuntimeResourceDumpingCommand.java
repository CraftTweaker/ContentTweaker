package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.service.ServiceManager;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RuntimeResourceDumpingCommand {
    private record VisitResult(Path path, String name) {}

    private static final class ResourceVisitor extends SimpleFileVisitor<Path> {
        private final Path root;
        private final ResourceFragment.Key key;
        private final Consumer<Path> pathConsumer;

        ResourceVisitor(final Path root, final ResourceFragment.Key key, final Consumer<VisitResult> resultConsumer) {
            this.root = root;
            this.key = key;
            this.pathConsumer = path -> this.pathToName(path, resultConsumer);
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

        private void pathToName(final Path path, final Consumer<VisitResult> resultConsumer) {
            final Path absolute = path.toAbsolutePath().normalize();
            final Path targetPath = this.root.relativize(path.toAbsolutePath().normalize()).normalize();
            resultConsumer.accept(new VisitResult(absolute, "%s/%s/%s".formatted(this.key.type().getDirectory(), this.key.id(), targetPath)));
        }
    }

    // TODO("Find a suitable directory")
    private static final Supplier<Path> DUMP_ROOT = Suppliers.memoize(() -> ServiceManager.platform().gameDirectory().resolve("./ct_dumps/contenttweaker_resources"));

    private RuntimeResourceDumpingCommand() {}

    public static void register(final ICommandRegistrationHandler handler) {
        handler.registerDump(
                "contenttweaker_resources",
                Component.translatable(ContentTweakerConstants.ln("command.dump_resources.desc")),
                RuntimeResourceDumpingCommand::buildCommand
        );
    }

    private static void buildCommand(final LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.requires(p -> p.hasPermission(2))
                .then(Commands.literal("log").executes(RuntimeResourceDumpingCommand::executeLog))
                .then(Commands.literal("file").executes(RuntimeResourceDumpingCommand::executeFile))
                .executes(RuntimeResourceDumpingCommand::executeLog);
    }

    private static int executeLog(final CommandContext<CommandSourceStack> context) {
        return execute(context, CraftTweakerAPI.LOGGER::info);
    }

    private static int executeFile(final CommandContext<CommandSourceStack> context) {
        return execute(context, RuntimeResourceDumpingCommand::write);
    }

    private static int execute(final CommandContext<CommandSourceStack> context, final Consumer<VisitResult> executor) {
        Arrays.stream(PackType.values())
                .map(ContentTweakerCore.core().resourceManager()::fragments)
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(RuntimeResourceDumpingCommand::gatherFragment)
                .flatMap(Collection::stream)
                .forEach(executor);

        CommandUtilities.send(
                CommandUtilities.openingLogFile(
                        Component.translatable(
                                "crafttweaker.command.list.check.log",
                                CommandUtilities.makeNoticeable(Component.translatable(ContentTweakerConstants.ln("command.dump_resources.log"))),
                                CommandUtilities.getFormattedLogFile()
                        ).withStyle(ChatFormatting.GREEN)),
                context.getSource()
        );

        return Command.SINGLE_SUCCESS;
    }

    private static List<VisitResult> gatherFragment(final RuntimeFragment fragment) {
        final ResourceFragment.Key key = fragment.key();
        final List<VisitResult> outer = new ArrayList<>();
        fragment.fs()
                .getRootDirectories()
                .forEach(root -> gatherFromRoot(root, outer, key));
        return outer;
    }

    private static void gatherFromRoot(final Path root, final List<VisitResult> outer, final ResourceFragment.Key key) {
        try {
            Files.walkFileTree(root, new ResourceVisitor(root, key, outer::add));
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void write(final VisitResult result) {
        try {
            final Path dumpPath = DUMP_ROOT.get().resolve(result.name());
            final Path parent = dumpPath.getParent();
            Files.createDirectories(parent);
            Files.copy(result.path(), dumpPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to correctly dump resource %s due to an error".formatted(result.name()), e);
        }
    }
}
