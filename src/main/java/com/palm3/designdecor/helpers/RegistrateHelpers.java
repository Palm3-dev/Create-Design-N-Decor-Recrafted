/* Feel free to use this class (or methods) in your mod if you want! */

/* The block builders need the .register() method. */

/* This */

package com.palm3.designdecor.helpers;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.RotatedPillarCTBehaviour;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

import static com.palm3.designdecor.DDMain.*;
import static com.palm3.designdecor.helpers.DnDecorCTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

@SuppressWarnings("removal")
public class RegistrateHelpers {


    /*@SafeVarargs
    /// Transformer that creates an item + tag(s), model and a blockstate from DnD assets. See method declaration for details.
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> simpleBlockDDBlockStateModelAndItem(TagKey<Item>... tags) {
        return b -> b.blockstate((c, p) -> {
                    p.models().withExistingParent(c.getName(), asDDResource(c.getName()));
                    p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
                })
                .item().tag(tags).build();
    }

    @SafeVarargs
    /// Transformer that creates an item + tag(s), model and a blockstate from DnD assets. See method declaration for details.
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> wallBlockDDBlockStateItemAndModel(TagKey<Item>... tags) {
        return b -> b.blockstate((c, p) -> {
                    var post = p.models().withExistingParent(c.getName() + "_post", asDDResource("block/" + c.getName() + "_post"));
                    var side = p.models().withExistingParent(c.getName() + "_side", asDDResource("block/" + c.getName() + "_side"));
                    var sideTall = p.models().withExistingParent(c.getName() + "_side_tall", asDDResource("block/" + c.getName() + "_side_tall"));

                    p.wallBlock((WallBlock) c.get(), post, side, sideTall);
                })
                .transform(itemWithoutModel(tags));
    }

    @SafeVarargs
    /// Transformer that creates an item + tag(s), model and a blockstate from DnD assets. See method declaration for details.
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> stairBlockDDBlockStateModelAndItem(TagKey<Item>... tags) {
        return b -> b.blockstate((c, p) -> {
                    var stairs = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName()));
                    var stairsInner = p.models().withExistingParent(c.getName() + "_inner", asDDResource("block/" + c.getName() + "_inner"));
                    var stairsOuter = p.models().withExistingParent(c.getName() + "_outer", asDDResource("block/" + c.getName() + "_outer"));

                    p.stairsBlock((StairBlock) c.get(), stairs, stairsInner, stairsOuter);
                })
                .item().tag(tags).build();
    }

    @SafeVarargs
    /// Transformer that creates an item + tag(s), model and a blockstate from DnD assets. See method declaration for details.
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<SlabBlock, P>> slabBlockDDBlockStateModelAndItem(TagKey<Item>... tags) {
        return b -> b.blockstate((c, p) -> {
                    String parentBlockName = c.getName().substring(0, c.getName().length() - 5);

                    var slabBottom = p.models().withExistingParent(c.getName() + "_bottom", asDDResource("block/" + c.getName()));
                    var slabTop = p.models().withExistingParent(c.getName() + "_top_gen", asDDResource("block/" + c.getName() + "_top"));
                    var parentBlock = p.models().getExistingFile(asDDResource("block/" + parentBlockName));

                    p.getVariantBuilder((SlabBlock)c.get())
                            .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM)
                            .addModels(new ConfiguredModel(slabBottom))
                            .partialState().with(SlabBlock.TYPE, SlabType.TOP)
                            .addModels(new ConfiguredModel(slabTop))
                            .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE)
                            .addModels(new ConfiguredModel(parentBlock));
                })
                .item().tag(tags).build();
    }

    /*@SafeVarargs
    @ParametersAreNonnullByDefault
    /// Transformer that creates an item + tag(s), model (given model type) and a blockstate from DnD assets. See method declaration for details.
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> customDDBlockStateModelAndItem(String modelType, TagKey<Item>... tags) {
        //  w, wall --> wall model type
        //  s, slab --> slab model type
        if (modelType == "w" || modelType == "wall") {
            return b -> b.blockstate((c, p) -> {
                        var post = p.models().withExistingParent(c.getName() + "_post", asDDResource("block/" + c.getName() + "_post"));
                        var side = p.models().withExistingParent(c.getName() + "_side", asDDResource("block/" + c.getName() + "_side"));
                        var sideTall = p.models().withExistingParent(c.getName() + "_side_tall", asDDResource("block/" + c.getName() + "_side_tall"));

                        p.wallBlock((WallBlock) c.get(), post, side, sideTall);
                    })
                    .transform(itemWithoutModel(tags));
        }/* else if (modelType == "s" || modelType == "slab") {
            return b -> b.blockstate((c, p) -> {
                        var slab = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName()));
                        var slabDouble = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName() + "_double"));
                        var slabTop = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName() + "_top"));

                        p.slabBlock((SlabBlock) c.get(), slab, slabTop, slabDouble);
                    })
                    .item().tag(tags).build();
        } /*else if (modelType == "S_ND" || modelType == "s_nd" || modelType == "SLAB_NO_DOUBLE" || modelType == "slab_no_double") {
            return b -> b.blockstate((c, p) -> {
                        var slab = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName()));
                        var slabTop = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName() + "_top"));

                        p.slabBlock((SlabBlock) c.get(), slab, slabTop, slabTop);
                    })
                    .item().tag(tags).build();
        } else {
            return b -> b;
        }*/


    public static class Transformers {
        // ---------------- DATAGEN DISABLERS -----------------
        public static class DatagenDisablers {
            /// Disables blockstate DataGen
            public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> noBlockState() {
                return b -> b.blockstate((c, p) -> {
                });
            }

            /// Creates a simple item without the model.
            public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> itemWithoutModel() {
                return b -> b.item().model((c, p) -> {
                }).build();
            }

            /// Creates a simple item without the model and adds it to the given tag(s).
            @SafeVarargs
            public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> itemWithoutModel(TagKey<Item>... tag) {
                return b -> b.item().tag(tag).model((c, p) -> {
                }).build();
            }
        }

        // ---------------- DATAGEN GENERATORS -----------------
        public static class DatagenGenerators {
            //empty
        }


        /*public static class BlockBuilding {
            /// Adds render layer (the method is deprecated though)
            public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> renderType(Supplier<RenderType> renderType) {
                return b -> b.addLayer(() -> renderType);
            }
        }*/
    }

    public static class BlockBuilders {
        //================================ SIMPLE ====================================
        /* ****************** CASINGS ****************** */

        /// Returns the base BlockBuilder for a CasingBlock with properties and connected textures (in the block/ directory).
        public static BlockBuilder<CasingBlock, CreateRegistrate> simpleCasing(String nameOrMaterial, boolean _casingSuffix, MapColor mapColor, SoundType soundType,
                                                                               String texture) {
            if (_casingSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_casing", CasingBlock::new)
                        .transform(BuilderTransformers.casing(() -> omniDDConnected(texture)))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, CasingBlock::new)
                        .transform(BuilderTransformers.casing(() -> omniDDConnected(texture)))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        }

        /// Returns the base BlockBuilder for a CasingBlock with properties and connected textures (in the given directory/path).
        public static BlockBuilder<CasingBlock, CreateRegistrate> simpleCasing(String nameOrMaterial, boolean _casingSuffix, MapColor mapColor, SoundType soundType,
                                                                               String texturePath, String texture) {
            if (_casingSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_casing", CasingBlock::new)
                        .transform(BuilderTransformers.casing(() -> omniDDLocationConnected(texturePath, texture)))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, CasingBlock::new)
                        .transform(BuilderTransformers.casing(() -> omniDDLocationConnected(texturePath, texture)))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        }


        /* ****************** GLASS ****************** */

        /// Returns the base BlockBuilder for a ConnectedGlassBlock with properties and connected textures (in the block/palettes/ directory).
        public static BlockBuilder<ConnectedGlassBlock, CreateRegistrate> simpleConnectedGlass(String name, String texture, String topTexture) {
            return DND_REGISTRATE
                    .block(name, ConnectedGlassBlock::new)
                    .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniDDLocationConnected("palettes", texture), omniDDLocationConnected("palettes", topTexture))))
                    .addLayer(() -> RenderType::cutout)
                    .initialProperties(() -> Blocks.GLASS)
                    .properties(p -> p
                            .isValidSpawn((state, getter, pos, entityType) -> false)
                            .isRedstoneConductor((state, getter, pos) -> false)
                            .isSuffocating((state, getter, pos) -> false)
                            .isViewBlocking((state, getter, pos) -> false))
                    .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                    .tag(Tags.Blocks.GLASS, BlockTags.IMPERMEABLE);
        }

        /// Returns the base BlockBuilder for a ConnectedGlassBlock with properties and connected textures (in the given directory/path).
        public static BlockBuilder<ConnectedGlassBlock, CreateRegistrate> simpleConnectedGlass(String name, String texturePath, String texture, String topTexture) {
            return DND_REGISTRATE
                    .block(name, ConnectedGlassBlock::new)
                    .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniDDLocationConnected(texturePath, texture), omniDDLocationConnected(texturePath, topTexture))))
                    .addLayer(() -> RenderType::cutout)
                    .initialProperties(() -> Blocks.GLASS)
                    .properties(p -> p
                            .isValidSpawn((state, getter, pos, entityType) -> false)
                            .isRedstoneConductor((state, getter, pos) -> false)
                            .isSuffocating((state, getter, pos) -> false)
                            .isViewBlocking((state, getter, pos) -> false))
                    .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                    .tag(Tags.Blocks.GLASS, BlockTags.IMPERMEABLE);
        }


        /* ****************** CONNECTED PILLAR ****************** */

        /// Returns the base BlockBuilder for a ConnectedPillarBlock with properties and connected textures (in the block/palettes/ directory).
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleConnectedPillar(String nameOrMaterial, boolean _pillarSuffix, MapColor mapColor, SoundType soundType, String texture, String topTexture) {
            if (_pillarSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_pillar", ConnectedPillarBlock::new)
                        .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleDDConnected(texture), omniDDConnected(topTexture))))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, ConnectedPillarBlock::new)
                        .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleDDConnected(texture), omniDDConnected(topTexture))))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }

        /// Returns the base BlockBuilder for a ConnectedPillarBlock with properties and connected textures (in the given directory/path).
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleConnectedPillar(String nameOrMaterial, boolean _pillarSuffix, MapColor mapColor, SoundType soundType, String texturePath, String texture, String topTexture) {
            if (_pillarSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_pillar", ConnectedPillarBlock::new)
                        .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleDDLocationConnected(texturePath, texture), omniDDLocationConnected(texturePath, topTexture))))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, ConnectedPillarBlock::new)
                        .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleDDLocationConnected(texturePath, texture), omniDDLocationConnected(texturePath, topTexture))))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** BLOCK ****************** */

        /// Returns the base BlockBuilder for a Block with properties.
        public static BlockBuilder<Block, CreateRegistrate> simpleBlock(String nameOrMaterial, boolean _blockSuffix, MapColor mapColor, SoundType soundType) {
            if (_blockSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_block", Block::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, Block::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** SLAB ****************** */

        /// Returns the base BlockBuilder for a SlabBlock with properties. Already adds _slab
        public static BlockBuilder<SlabBlock, CreateRegistrate> simpleSlabBlock(String nameOrMaterial, boolean _slabSuffix, MapColor mapColor, SoundType sound) {
            if (_slabSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_slab", SlabBlock::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, SlabBlock::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** STAIR ****************** */

        /// Returns the base BlockBuilder for a StairBlock with properties. Already adds _stairs
        public static BlockBuilder<StairBlock, CreateRegistrate> simpleStairBlock(String nameOrMaterial, boolean _stairSuffix, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound) {
            if (_stairSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_stairs", p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** WALL ****************** */

        /// Returns the base BlockBuilder for a WallBlock with properties. Already adds _wall
        public static BlockBuilder<WallBlock, CreateRegistrate> simpleWallBlock(String nameOrMaterial, boolean _wallSuffix, MapColor mapColor, SoundType sound) {
            if (_wallSuffix)
                return DND_REGISTRATE
                        .block(nameOrMaterial + "_wall", WallBlock::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS);
            else
                return DND_REGISTRATE
                        .block(nameOrMaterial, WallBlock::new)
                        .initialProperties(SharedProperties::softMetal)
                        .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                        .loot(RegistrateBlockLootTables::dropSelf)
                        .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS);
        }
    }
}
