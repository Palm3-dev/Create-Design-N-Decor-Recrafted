package com.palm3.designdecor;

import com.mojang.logging.LogUtils;
import com.palm3.designdecor.register.DnDBlocks;
import com.palm3.designdecor.register.DnDTabs;
import com.simibubi.create.Create;
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
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(DnDMain.MOD_ID)
public class DnDMain { //todo add blockstate gen for windows and revise blockbuilders methods
    public static final String MOD_ID = "design_n_decor";
    public static final String ORIGINAL_MOD_ID = "dndecor";
    public static final String DND_JAR_FILE = "Design-n-Decor-1.21.1-2.1.0.jar";  // Correct dnd jar file for this mod release
    public static final Path LOAD_ASSETS_DIR = FMLPaths.GAMEDIR.get().resolve("load_assets");
    public static final CreateRegistrate DND_REGISTRATE = CreateRegistrate.create(MOD_ID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    public static final Logger LOGGER = LogUtils.getLogger();


    public DnDMain(FMLJavaModLoadingContext context) {
        var modEventBus = context.getModEventBus();
        DND_REGISTRATE.registerEventListeners(modEventBus);
        modEventBus.addListener(this::addPackFinders);
        modEventBus.addListener(this::commonSetup);
        // Registrations
        DnDBlocks.register();
        DnDTabs.DD_TABS.register(modEventBus);
        //DDBlockEntities.register();
    }

    // Get registrate
    public static CreateRegistrate getRegistrate() {
        return DND_REGISTRATE;
    }

    // Resources methods
    /// @return ResourceLocation with this mod namespace and the given path.
    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    /// @return ResourceLocation with given namespace and the given path.
    public static ResourceLocation asNamespaceResource(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    /// @return ResourceLocation with the Design 'N' Decor namespace and the given path.
    public static ResourceLocation asDDResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ORIGINAL_MOD_ID, path);
    }

    // Create load_assets directory.
    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                try {
                    Files.createDirectories(LOAD_ASSETS_DIR);
                    LOGGER.info("Created directory for assets jars: " + LOAD_ASSETS_DIR);//todo add readme
                } catch (IOException e) {
                    LOGGER.error("Could not create load_assets directory: " + e.getMessage());
                }
            });
    }

    // Load Design 'N' Decor assets to resourcepack
    @SubscribeEvent
    public void addPackFinders(AddPackFindersEvent event) {
        LOGGER.info("=======================================================================================");
        LOGGER.info("Loading '" + ORIGINAL_MOD_ID + "' mod assets from jar file...");

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            FileSystem jarFileSystem;
            Path resourcePath = LOAD_ASSETS_DIR.resolve(DND_JAR_FILE);

            if (Files.exists(resourcePath)) {
                LOGGER.info("Found compatible mod jar file (" + DND_JAR_FILE + ")");
                try {
                    // File system creation
                    jarFileSystem = FileSystems.newFileSystem(resourcePath, (ClassLoader) null);
                    Path rootInsideJar = jarFileSystem.getPath("/");
                    LOGGER.info("Mounted jar filesystem: " + DND_JAR_FILE + rootInsideJar);
                    Files.list(jarFileSystem.getPath("/assets")).forEach(path -> LOGGER.info("Found folder in assets dir: " + path));

                    // Pack loading
                    PathPackResources externalPack = new PathPackResources("dndecor", rootInsideJar, false);

                    event.addRepositorySource((infoConsumer) -> {
                        Pack pack = Pack.create(
                                "dd_assets",
                                Component.literal("Design 'N' Decor Assets"),
                                true,  // Cannot be disabled?
                                (id) -> externalPack,
                                new Pack.Info(Component.literal("Resources for Design 'N' Decor: Recrafted"), 15, FeatureFlagSet.of()),
                                PackType.CLIENT_RESOURCES,
                                Pack.Position.TOP,
                                false,  // Cannot change order?
                                PackSource.DEFAULT
                        );
                        infoConsumer.accept(pack);
                    });

                } catch (IOException e) {
                    LOGGER.error("Could not mount the jar file as filesystem: " + e.getMessage());
                }
            } else {
                LOGGER.error("No compatible Design 'N' Decor jar found in" + LOAD_ASSETS_DIR + "directory");
                LOGGER.info("Download the '" + DND_JAR_FILE + "' mod version and put it in the mods folder");
            }
        }
    }
}