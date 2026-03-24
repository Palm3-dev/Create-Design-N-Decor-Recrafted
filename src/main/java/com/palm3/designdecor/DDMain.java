package com.palm3.designdecor;

import com.mojang.logging.LogUtils;
import com.palm3.designdecor.register.DDBlocks;
import com.palm3.designdecor.register.DDTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(DDMain.MOD_ID)
public class DDMain {
    public static final String MOD_ID = "dndecor";
    public static final String DND_JAR_VERSION = "Design-n-Decor-1.21.1-2.1.0.jar";  // Correct dnd jar file for this mod release
    public static final CreateRegistrate DD_REGISTRATE = CreateRegistrate.create(MOD_ID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    public static final Logger LOGGER = LogUtils.getLogger();


    public DDMain(FMLJavaModLoadingContext context) {
        var modEventBus = context.getModEventBus();
        DD_REGISTRATE.registerEventListeners(modEventBus);
        modEventBus.addListener(this::addPackFinders);
        // Registrations
        DDBlocks.register();
        DDTabs.DD_TABS.register(modEventBus);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation asExternalResource(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    // Design 'N' Decor Assets loading
    @SubscribeEvent
    public void addPackFinders(AddPackFindersEvent event) {
        LOGGER.info("=======================================================================================");
        LOGGER.info("Loading 'dndecor' mod assets from jar...");

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            FileSystem jarFileSystem = null;
            Path resourcePath = FMLPaths.GAMEDIR.get().resolve("mods/" + DND_JAR_VERSION);

            if (Files.exists(resourcePath)) {
                LOGGER.info("Found correct mod jar file (" + DND_JAR_VERSION + ")");
                try {
                    // File system creation
                    jarFileSystem = FileSystems.newFileSystem(resourcePath, (ClassLoader) null);
                    Path rootInsideJar = jarFileSystem.getPath("/");
                    LOGGER.info("Mounted jar filesystem: " + DND_JAR_VERSION + "/" + rootInsideJar);

                    Files.list(jarFileSystem.getPath("/assets")).forEach(path -> LOGGER.info("Found folder in assets dir: " + path));

                    // Pack loading
                    PathPackResources externalPack = new PathPackResources("dndecor", rootInsideJar, false);

                    event.addRepositorySource((infoConsumer) -> {
                        Pack pack = Pack.create(
                                "dd_assets",
                                Component.literal("Design 'N' Decor Assets"),
                                true,  // Cannot be disabled?
                                (id) -> externalPack,
                                new Pack.Info(Component.literal("Resources for Design 'N' Decor Backported"), 15, FeatureFlagSet.of()),
                                PackType.CLIENT_RESOURCES,
                                Pack.Position.TOP,
                                false,  // Cannot change order?
                                PackSource.DEFAULT
                        );
                        if (pack != null) infoConsumer.accept(pack);
                    });

                } catch (IOException e) {
                    LOGGER.error("Can't mount the jar file as filesystem: " + e.getMessage());
                }
            } else {
                LOGGER.error("No compatible Design 'N' Decor jar found in mods directory");
                LOGGER.info("Download the 1.21.1 mod version (release 2.1.0) and put it in the mods folder");
            }
        }
    }
}
