package com.palm3.designdecor.foundation.helpers.create_registrate;

import com.palm3.designdecor.content.blocks.sign_blocks.RotableSquareSignBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.palm3.designdecor.DnDMain.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.ItemUtils.customTagOrDefault;

public class RegistrateDataGenTransformers {
    public static class Disablers {
        // Mainly for testing purposes
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
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> itemWithoutModel(@NotNull TagKey<Item>... tag) {
            return b -> b.item().tag(tag).model((c, p) -> {
            }).build();
        }
    }

    public static class Generators {
        /* =========== METHODS NAMING SCHEME ===========
        * 'x' is a generic method name (ex. squareSign, a block type)
        * xBM -> BlockState and Model
        * xBMI -> BlockState, Model and Item
        */
        
        /**
         * Transformer that creates a model and a blockstate for a simple sign.
         * The file texture name is the sign name without _sign at the end, keep it in mind.
         * @param namespace The namespace where to search the texture.
         * @param texturePathNoName The path where to search the texture in 'block/' directory.  Remember to put '/' at the end. If null, the default path will be 'block/signs/.'
         * @throws IllegalArgumentException If the parameters are not both null or !null.
         * @return BlockState and Model builder with given texture path.
        */
        public static <P> NonNullUnaryOperator<BlockBuilder<SquareSignBlock, P>> squareSignBM(@NotNull String namespace, @Nullable String texturePathNoName) {
            if (texturePathNoName != null)
                return b -> b.blockstate((c, p) -> {
                    String fileTextureName;
                    if (c.getName().endsWith("_sign"))
                        fileTextureName = c.getName().substring(0, c.getName().length() - 5);
                    else
                        fileTextureName = c.getName();

                    var model = p.models().withExistingParent("block/signs/" + c.getName(), asResource("block/base/signs/sign_model_0"))
                            .texture("0", asNamespaceResource(namespace, "block/" + texturePathNoName + fileTextureName));

                    p.getVariantBuilder(c.get()).forAllStates(state -> {
                        Direction dir = state.getValue(SquareSignBlock.FACING);

                        int yRot = 0;
                        int xRot = 0;
                        switch (dir) {
                            case UP -> xRot = 270;
                            case DOWN -> xRot = 90;
                            case NORTH -> yRot = 0;
                            case SOUTH -> yRot = 180;
                            case EAST -> yRot = 90;
                            case WEST -> yRot = 270;
                        }

                        return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
                    });
                });
            else
                return b -> b.blockstate((c, p) -> {
                    String fileTextureName = c.getName().substring(0, c.getName().length() - 5);
                    var model = p.models().withExistingParent("block/signs/" + c.getName(), asResource("block/base/signs/sign_model_0"))
                            .texture("0", asResource("block/signs/" + fileTextureName));

                    p.getVariantBuilder(c.get()).forAllStates(state -> {
                        Direction dir = state.getValue(SquareSignBlock.FACING);

                        int yRot = 0;
                        int xRot = 0;
                        switch (dir) {
                            case UP -> xRot = 270;
                            case DOWN -> xRot = 90;
                            case NORTH -> yRot = 0;
                            case SOUTH -> yRot = 180;
                            case EAST -> yRot = 90;
                            case WEST -> yRot = 270;
                        }

                        return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
                    });
                });
        }

        /**
         * Transformer that creates a model and a blockstate for a rotable sign.
         * The file texture name is the sign name without _sign at the end, keep it in mind.
         * @param namespace The namespace where to search the texture.
         * @param texturePathNoName The path where to search the texture in 'block/' directory. Remember to put '/' at the end. If null, the default path will be 'block/signs/.'
         * @throws IllegalArgumentException If the parameters are not both null or !null.
         * @return BlockState and Model builder with given texture path.
         */
        public static <P> NonNullUnaryOperator<BlockBuilder<RotableSquareSignBlock, P>> rotableSquareSignBM(@NotNull String namespace, @Nullable String texturePathNoName) {
            if (texturePathNoName != null)
                return b -> b.blockstate((c, p) -> {
                    String fileTextureName = c.getName().substring(0, c.getName().length() - 5);
                    var model0 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_0", asResource("block/base/signs/sign_model_0"))
                            .texture("0", asNamespaceResource(namespace, "block/" + texturePathNoName + fileTextureName));
                    var model90 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_90", asResource("block/base/signs/sign_model_90"))
                            .texture("0", asNamespaceResource(namespace, "block/" + texturePathNoName + fileTextureName));
                    var model180 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_180", asResource("block/base/signs/sign_model_180"))
                            .texture("0", asNamespaceResource(namespace, "block/" + texturePathNoName + fileTextureName));
                    var model270 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_270", asResource("block/base/signs/sign_model_270"))
                            .texture("0", asNamespaceResource(namespace, "block/" + texturePathNoName + fileTextureName));

                    p.getVariantBuilder(c.get()).forAllStates(state -> {
                        Direction dir = state.getValue(RotableSquareSignBlock.FACING);
                        var axisDir = state.getValue(RotableSquareSignBlock.AXIS_ROT);
                        ModelFile model = model0;  // Fallback

                        switch (axisDir) {
                            case ROT0 -> model = model0;
                            case ROT90 -> model = model90;
                            case ROT180 -> model = model180;
                            case ROT270 -> model = model270;
                        }

                        int yRot = 0;
                        int xRot = 0;
                        switch (dir) {
                            case UP -> xRot = 270;
                            case DOWN -> xRot = 90;
                            case NORTH -> yRot = 0;
                            case SOUTH -> yRot = 180;
                            case EAST -> yRot = 90;
                            case WEST -> yRot = 270;
                        }

                        return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
                    });
                });
            else
                return b -> b.blockstate((c, p) -> {
                    String fileTextureName = c.getName().substring(0, c.getName().length() - 5);
                    var model0 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_0", asResource("block/base/signs/sign_model_0"))
                            .texture("0", asResource("block/signs/" + fileTextureName));
                    var model90 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_90", asResource("block/base/signs/sign_model_90"))
                            .texture("0", asResource("block/signs/" + fileTextureName));
                    var model180 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_180", asResource("block/base/signs/sign_model_180"))
                            .texture("0", asResource("block/signs/" + fileTextureName));
                    var model270 = p.models().withExistingParent("block/signs/rotable/" + c.getName() + "_270", asResource("block/base/signs/sign_model_270"))
                            .texture("0", asResource("block/signs/" + fileTextureName));

                    p.getVariantBuilder(c.get()).forAllStates(state -> {
                        Direction dir = state.getValue(RotableSquareSignBlock.FACING);
                        var axisDir = state.getValue(RotableSquareSignBlock.AXIS_ROT);
                        ModelFile model = model0;  // Fallback

                        switch (axisDir) {
                            case ROT0 -> model = model0;
                            case ROT90 -> model = model90;
                            case ROT180 -> model = model180;
                            case ROT270 -> model = model270;
                        }

                        int yRot = 0;
                        int xRot = 0;
                        switch (dir) {
                            case UP -> xRot = 270;
                            case DOWN -> xRot = 90;
                            case NORTH -> yRot = 0;
                            case SOUTH -> yRot = 180;
                            case EAST -> yRot = 90;
                            case WEST -> yRot = 270;
                        }

                        return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
                    });
                });
        }

        /**
         * Transformer that creates a model, blockstate and item for a slab.
         * @param namespace The namespace where to search the texture and models.
         * @param textureDir The directory where to search the top, bottom and side texture.
         * @param tags The item tags that the slab can have, can be null.
         * @return BlockState, Model and Item builder with given texture path.
         */
        @SafeVarargs
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<SlabBlock, P>> slabBMI(@NotNull String namespace, @NotNull String textureDir, @Nullable TagKey<Item>... tags) {
            return b -> b.blockstate((c, p) -> {
                var slabBottom = p.models().withExistingParent(c.getName() + "_bottom", asNamespaceResource("minecraft", "block/slab"))
                        .texture("bottom", asNamespaceResource(namespace, textureDir))
                        .texture("side", asNamespaceResource(namespace, textureDir))
                        .texture("top", asNamespaceResource(namespace, textureDir));

                var slabTop = p.models().withExistingParent(c.getName() + "_top", asNamespaceResource("minecraft", "block/slab_top"))
                        .texture("bottom", asNamespaceResource(namespace, textureDir))
                        .texture("side", asNamespaceResource(namespace, textureDir))
                        .texture("top", asNamespaceResource(namespace, textureDir));

                var slabFull = p.models().withExistingParent(c.getName() + "_full_block", asNamespaceResource("minecraft", "block/cube_all"))
                        .texture("all", asNamespaceResource(namespace, textureDir));

                p.getVariantBuilder((SlabBlock)c.get())
                        .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM)
                        .addModels(new ConfiguredModel(slabBottom))
                        .partialState().with(SlabBlock.TYPE, SlabType.TOP)
                        .addModels(new ConfiguredModel(slabTop))
                        .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE)
                        .addModels(new ConfiguredModel(slabFull));
            })
            .item().model((c, p) -> p.blockItem(c::get, "_bottom")).transform(customTagOrDefault(null, tags)).build();
        }

        /**
         * Transformer that creates a model, blockstate and item for a slab.
         * @param namespace The namespace where to search the texture and models.
         * @param textureDir The directory where to search the top and bottom texture.
         * @param doubleTextureDir The directory where to search the double slab texture (layered side).
         * @param tags The item tags that the slab can have, can be null.
         * @return BlockState, Model and Item builder with given texture path.
         */
        @SafeVarargs
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<SlabBlock, P>> doubleSlabBMI(@NotNull String namespace, @NotNull String textureDir, @NotNull String doubleTextureDir, @Nullable TagKey<Item>... tags) {
            return b -> b.blockstate((c, p) -> {
                var slabBottom = p.models().withExistingParent(c.getName() + "_bottom", asNamespaceResource("minecraft", "block/slab"))
                        .texture("bottom", asNamespaceResource(namespace, textureDir))
                        .texture("side", asNamespaceResource(namespace, doubleTextureDir))
                        .texture("top", asNamespaceResource(namespace, textureDir));

                var slabTop = p.models().withExistingParent(c.getName() + "_top", asNamespaceResource("minecraft", "block/slab_top"))
                        .texture("bottom", asNamespaceResource(namespace, textureDir))
                        .texture("side", asNamespaceResource(namespace, doubleTextureDir))
                        .texture("top", asNamespaceResource(namespace, textureDir));

                var slabDouble = p.models().withExistingParent(c.getName() + "_double", asNamespaceResource("minecraft", "block/cube_column"))
                        .texture("end", asNamespaceResource(namespace, textureDir))
                        .texture("side", asNamespaceResource(namespace, doubleTextureDir));

                p.getVariantBuilder((SlabBlock)c.get())
                        .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM)
                        .addModels(new ConfiguredModel(slabBottom))
                        .partialState().with(SlabBlock.TYPE, SlabType.TOP)
                        .addModels(new ConfiguredModel(slabTop))
                        .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE)
                        .addModels(new ConfiguredModel(slabDouble));
            })
            .item().model((c, p) -> p.blockItem(c::get, "_bottom")).transform(customTagOrDefault(null, tags)).build();
        }


        /**
         * Transformer that creates a model, blockstate and item for a slab.
         * @param namespace The namespace where to search the texture and models.
         * @param textureDir The directory where to search the top and bottom texture.
         * @param tags The item tags that the wall can have, can be null.
         * @return BlockState, Model and Item builder with given texture path.
         */
        @SafeVarargs
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> stairBMI(@NotNull String namespace, @NotNull String textureDir, @Nullable TagKey<Item>... tags) {
            return b -> b.blockstate((c, p) -> {
                    var stairs = p.models().withExistingParent(c.getName(), asNamespaceResource("minecraft", "block/stairs"))
                            .texture("bottom", asNamespaceResource(namespace, textureDir))
                            .texture("side", asNamespaceResource(namespace, textureDir))
                            .texture("top", asNamespaceResource(namespace, textureDir));
                    var stairsInner = p.models().withExistingParent(c.getName() + "_inner", asNamespaceResource("minecraft", "block/inner_stairs"))
                            .texture("bottom", asNamespaceResource(namespace, textureDir))
                            .texture("side", asNamespaceResource(namespace, textureDir))
                            .texture("top", asNamespaceResource(namespace, textureDir));
                    var stairsOuter = p.models().withExistingParent(c.getName() + "_outer", asNamespaceResource("minecraft", "block/outer_stairs"))
                            .texture("bottom", asNamespaceResource(namespace, textureDir))
                            .texture("side", asNamespaceResource(namespace, textureDir))
                            .texture("top", asNamespaceResource(namespace, textureDir));

                    p.stairsBlock((StairBlock) c.get(), stairs, stairsInner, stairsOuter);
                })
                .item().transform(customTagOrDefault(null, tags)).build();
        }


        /**
         * Transformer that creates a model, blockstate and item for a wall.
         * @param namespace The namespace where to search the texture and models.
         * @param textureDir The directory where to search the top and bottom texture.
         * @return BlockState, Model and Item builder with given texture path.
         */
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> wallBM(@NotNull String namespace, @NotNull String textureDir) {

        return b -> b.blockstate((c, p) -> {
                var post = p.models().withExistingParent(c.getName() + "_post", asNamespaceResource("minecraft", "block/template_wall_post"))
                        .texture("wall", asNamespaceResource(namespace, textureDir));
                var side = p.models().withExistingParent(c.getName() + "_side", asNamespaceResource("minecraft", "block/template_wall_side"))
                        .texture("wall", asNamespaceResource(namespace, textureDir));
                var sideTall = p.models().withExistingParent(c.getName() + "_side_tall", asNamespaceResource("minecraft", "block/template_wall_side_tall"))
                        .texture("wall", asNamespaceResource(namespace, textureDir));


                p.wallBlock((WallBlock) c.get(), post, side, sideTall);
            });
        }

        /**
         * Transformer that creates an item for a wall block.
         * @param namespace The namespace where to search the texture.
         * @param textureDir The directory where to search the top and bottom texture.
         * @param tags The item tags that the wall can have, can be null.
         * @return BlockState, Model and Item builder with given texture path.
         */
        @SafeVarargs
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> wallI(@NotNull String namespace, @NotNull String textureDir, @Nullable TagKey<Item>... tags) {
            return b -> b
                .item().model((c, p) -> p.wallInventory(c.getName(), asNamespaceResource(namespace, textureDir)))
                .transform(customTagOrDefault(null, tags)).build();
        }


        /**
         * Transformer that creates a model, blockstate and item for a normal block.
         * @param namespace The namespace where to search the texture.
         * @param textureDir The directory where to search the texture. If null, default id block/block_name
         * @param tags The item tags that the block can have, can be null.
         * @return BlockState, Model and Item builder with given texture path.
         */
        @SafeVarargs
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> blockBMI(@NotNull String namespace, @Nullable String textureDir, @Nullable TagKey<Item>... tags) {
            return b -> b.blockstate((c, p) -> {
                        if (textureDir == null) {
                            p.models().withExistingParent(c.getName(), asNamespaceResource("minecraft", "block/cube_all"))
                                    .texture("all", asNamespaceResource(namespace, "block/" + c.getName()));
                            p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
                        } else {
                            p.models().withExistingParent(c.getName(), asNamespaceResource("minecraft", "block/cube_all"))
                                    .texture("all", asNamespaceResource(namespace, textureDir));
                            p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
                        }
                    })
                    .item().transform(customTagOrDefault(null, tags)).build();
        }
    }
}
