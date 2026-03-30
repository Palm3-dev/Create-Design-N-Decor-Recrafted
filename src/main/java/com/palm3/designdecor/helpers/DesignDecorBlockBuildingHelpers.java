/* In order to use this datagen class, the original assets need to be in this mod assets folder! */

package com.palm3.designdecor.helpers;

import com.electronwill.nightconfig.core.conversion.InvalidValueException;
import com.palm3.designdecor.blocks.frontlight.FrontlightBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.palm3.designdecor.DDMain.*;
import static com.palm3.designdecor.blocks.frontlight.Frontlight.NORMAL;
import static com.palm3.designdecor.blocks.frontlight.Frontlight.CAGE;
import static com.palm3.designdecor.blocks.frontlight.Frontlight.TOP;
import static com.palm3.designdecor.helpers.DesignDecorBlockBuildingHelpers.DataGenTransformers.*;
import static com.palm3.designdecor.helpers.RegistrateHelpers.BlockBuilders.*;
import static com.palm3.designdecor.helpers.DnDecorCTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class DesignDecorBlockBuildingHelpers {
    public static class DataGenTransformers {

        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (standard) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> simpleBlockDDBlockStateModelAndItem(@Nullable TagKey<Item>... tags) {
            if (tags != null)
                return b -> b.blockstate((c, p) -> {
                            p.models().withExistingParent(c.getName(), asDDResource(c.getName()));
                            p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
                        })
                        .item().tag(tags).build();
            else
                return b -> b.blockstate((c, p) -> {
                    p.models().withExistingParent(c.getName(), asDDResource(c.getName()));
                    p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
                })
                .simpleItem();
        }


        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (wall) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> wallBlockDDBlockStateItemAndModel(@Nullable TagKey<Item>... tags) {
            if (tags != null)
                return b -> b.blockstate((c, p) -> {
                            var post = p.models().withExistingParent(c.getName() + "_post", asDDResource("block/" + c.getName() + "_post"));
                            var side = p.models().withExistingParent(c.getName() + "_side", asDDResource("block/" + c.getName() + "_side"));
                            var sideTall = p.models().withExistingParent(c.getName() + "_side_tall", asDDResource("block/" + c.getName() + "_side_tall"));

                            p.wallBlock((WallBlock) c.get(), post, side, sideTall);
                        })
                        .item().model((c, p) -> {
                            String name = c.getName();

                            String subDir;
                            String material;
                            String finalName;
                            String temp;
                            if (name.startsWith("polished_cut_") && name.endsWith("_wall")) {
                                subDir = "polished";
                                temp = name.substring(13);
                                material = temp.substring(0, temp.length() - 5);
                                finalName = material + "_cut_polished";
                            } else if (name.startsWith("cut_") && name.endsWith("_bricks_wall")) {
                                subDir = "brick";
                                temp = name.substring(4);
                                material = temp.substring(0, temp.length() - 12);
                                finalName = material + "_cut_brick";
                            } else if (name.startsWith("small_") && name.endsWith("_bricks_wall")) {  //small_material_bricks_wall
                                subDir = "small_brick";
                                temp = name.substring(6);
                                material = temp.substring(0, temp.length() - 12);
                                finalName = material + "_cut_small_brick";
                            } else if (name.startsWith("cut_") && name.endsWith("_wall")) { // Needs to be put here otherwise it will explode because it founds cut_material_bricks_wall
                                subDir = "cut";
                                temp = name.substring(4);
                                material = temp.substring(0, temp.length() - 5);
                                finalName = material + "_cut";
                            } else {
                                subDir = "found_wall_entry_with_incorrect_name_for ";
                                finalName = name;
                            }

                            p.wallInventory(c.getName(), asDDResource("block/palettes/stone_types/" + subDir + "/" + finalName));

                        }).tag(tags).build();
            else
                return b -> b.blockstate((c, p) -> {
                            var post = p.models().withExistingParent(c.getName() + "_post", asDDResource("block/" + c.getName() + "_post"));
                            var side = p.models().withExistingParent(c.getName() + "_side", asDDResource("block/" + c.getName() + "_side"));
                            var sideTall = p.models().withExistingParent(c.getName() + "_side_tall", asDDResource("block/" + c.getName() + "_side_tall"));
                            var item = p.models().withExistingParent(c.getName(), asExternalResource("minecraft", "block/wall_inventory"));

                            p.wallBlock((WallBlock) c.get(), post, side, sideTall);
                        })
                        .item().model((c, p) -> {
                            String name = c.getName();

                            String subDir;
                            String material;
                            String finalName;
                            String temp;
                            if (name.startsWith("polished_cut_") && name.endsWith("_wall")) {
                                subDir = "polished";
                                temp = name.substring(13);
                                material = temp.substring(0, temp.length() - 5);
                                finalName = material + "_cut_polished";
                            } else if (name.startsWith("cut_") && name.endsWith("_bricks_wall")) {
                                subDir = "brick";
                                temp = name.substring(4);
                                material = temp.substring(0, temp.length() - 12);
                                finalName = material + "_cut_brick";
                            } else if (name.startsWith("small_") && name.endsWith("_bricks_wall")) {  //small_material_bricks_wall
                                subDir = "small_brick";
                                temp = name.substring(6);
                                material = temp.substring(0, temp.length() - 12);
                                finalName = material + "_cut_small_brick";
                            } else if (name.startsWith("cut_") && name.endsWith("_wall")) { // Needs to be put here otherwise it will explode because it founds cut_material_bricks_wall
                                subDir = "cut";
                                temp = name.substring(4);
                                material = temp.substring(0, temp.length() - 5);
                                finalName = material + "_cut";
                            } else {
                                subDir = "found_wall_entry_with_incorrect_name_for ";
                                finalName = name;
                            }

                            p.wallInventory(c.getName(), asDDResource("block/palettes/stone_types/" + subDir + "/" + finalName));

                        }).build();
        }


        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (stair) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> stairBlockDDBlockStateModelAndItem(@Nullable TagKey<Item>... tags) {
            if (tags != null)
                return b -> b.blockstate((c, p) -> {
                            var stairs = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName()));
                            var stairsInner = p.models().withExistingParent(c.getName() + "_inner", asDDResource("block/" + c.getName() + "_inner"));
                            var stairsOuter = p.models().withExistingParent(c.getName() + "_outer", asDDResource("block/" + c.getName() + "_outer"));

                            p.stairsBlock((StairBlock) c.get(), stairs, stairsInner, stairsOuter);
                        })
                        .item().tag(tags).build();
            else
                return b -> b.blockstate((c, p) -> {
                    var stairs = p.models().withExistingParent(c.getName(), asDDResource("block/" + c.getName()));
                    var stairsInner = p.models().withExistingParent(c.getName() + "_inner", asDDResource("block/" + c.getName() + "_inner"));
                    var stairsOuter = p.models().withExistingParent(c.getName() + "_outer", asDDResource("block/" + c.getName() + "_outer"));

                    p.stairsBlock((StairBlock) c.get(), stairs, stairsInner, stairsOuter);
                })
                .simpleItem();
        }


        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (slab) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<SlabBlock, P>> slabBlockDDBlockStateModelAndItem(@Nullable TagKey<Item>... tags) {
            if (tags != null)
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
            else
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
                .simpleItem();
        }


        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (slab) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<FrontlightBlock, P>> frontlightBlockDDBlockStateModelAndItem(@Nullable TagKey<Item>... tags) {
            if (tags != null)
                return b -> b.blockstate((c, p) -> {
                            String modelDir = c.getName();

                            Map<String, ModelFile> models = new HashMap<>();

                            models.put("normal", p.models().withExistingParent("block/" + modelDir + "/frontlight", asDDResource("block/" + modelDir + "/frontlight")));
                            models.put("normal_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_rot", asDDResource("block/" + modelDir + "/frontlight_rot")));
                            models.put("grate", p.models().withExistingParent("block/" + modelDir + "/frontlight_grate", asDDResource("block/" + modelDir + "/frontlight_grate")));
                            models.put("grate_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_grate_rot", asDDResource("block/" + modelDir + "/frontlight_grate_rot")));
                            models.put("top", p.models().withExistingParent("block/" + modelDir + "/frontlight_top", asDDResource("block/" + modelDir + "/frontlight_top")));
                            models.put("top_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_top_rot", asDDResource("block/" + modelDir + "/frontlight_top_rot")));

                            models.put("normal_off", p.models().withExistingParent("block/" + modelDir + "/frontlight_off", asDDResource("block/" + modelDir + "/frontlight_off")));
                            models.put("normal_off_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_off_rot", asDDResource("block/" + modelDir + "/frontlight_off_rot")));
                            models.put("grate_off", p.models().withExistingParent("block/" + modelDir + "/frontlight_off_grate", asDDResource("block/" + modelDir + "/frontlight_off_grate")));
                            models.put("grate_off_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_off_grate_rot", asDDResource("block/" + modelDir + "/frontlight_off_grate_rot")));
                            models.put("top_off", p.models().withExistingParent("block/" + modelDir + "/frontlight_off_top", asDDResource("block/" + modelDir + "/frontlight_off_top")));
                            models.put("top_off_rot", p.models().withExistingParent("block/" + modelDir + "/frontlight_off_top_rot", asDDResource("block/" + modelDir + "/frontlight_off_top_rot")));
                            p.models().withExistingParent("block/" + modelDir + "/item", asDDResource("item/" + c.getName()));

                            p.getVariantBuilder(c.get()).forAllStates(state -> {
                                Direction dir = state.getValue(FrontlightBlock.FACING);
                                boolean lit = state.getValue(FrontlightBlock.LIT);
                                boolean rotated = state.getValue(FrontlightBlock.ROTATED);
                                var type = state.getValue(FrontlightBlock.CAGE_TYPE);
                                String modelName;


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

                                if (type.equals(NORMAL)) {
                                    modelName = "normal";
                                } else if (type.equals(TOP)) {
                                    modelName = "top";
                                } else if (type.equals(CAGE)) {
                                    modelName = "grate";
                                } else  {
                                    throw new InvalidValueException("Could not find a value between NORMAL, TOP, CAGE in frontlight block enum.");
                                }
                                if (!lit) modelName += "_off";
                                if (rotated) modelName += "_rot";


                                return ConfiguredModel.builder().modelFile(models.get(modelName)).rotationY(yRot).rotationX(xRot).build();
                            });
                        })
                        .item().model((c, p) -> {
                            p.blockItem(c::get, "/item");
                        }).tag(tags).build();
            else
                return b -> b;
        }
    }


    /* ****************** STONE blocks subcategory ****************** */
    /* This category only works on this mod, or on mods that use the Design 'N' Decor assets, since those methods search in the DnD assets directory
     * and the model assets are taken from there. */
    /// Returns the basic BlockBuilder for a stone-type Block with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<Block, CreateRegistrate> simpleDDStoneBlock(String name, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return simpleBlock(name, false, mapColor, sound)
                .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                });
    }

    /// Returns the basic BlockBuilder for a stone-type SlabBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<SlabBlock, CreateRegistrate> simpleDDStoneSlabBlock(String nameWithout_slab, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return simpleSlabBlock(nameWithout_slab, true, mapColor, sound)
                .transform(slabBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .transform(simpleBlockDDBlockStateModelAndItem())
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                });
    }

    /// Returns the basic BlockBuilder for a stone-type StairBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<StairBlock, CreateRegistrate> simpleDDStoneStairBlock(String nameWithout_stair, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return simpleStairBlock(nameWithout_stair, true, parentBlock, mapColor, sound)
                .transform(stairBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                });
    }

    /// Returns the basic BlockBuilder for a stone-type WallBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<WallBlock, CreateRegistrate> simpleDDStoneWallBlock(String nameWithout_wall, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return simpleWallBlock(nameWithout_wall, true, mapColor,sound)
                .transform(wallBlockDDBlockStateItemAndModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                });
    }

    /// Returns the basic BlockBuilder for a stone-type ConnectedPillarBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleDDStonePillarBlock(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return simpleConnectedPillar(nameWithout_pillar, true, mapColor, sound, generalPathInBlockDir, textureAcceptsPath, topTextureAcceptsPath)
                .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                });
    }

    /// Returns the basic BlockBuilder for a stone-type ConnectedPillarBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleDDStoneLayeredBlock(String name, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DND_REGISTRATE
                .block("layered_" + name, ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(horizKryppersDDLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniDDLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                });
    }
}
