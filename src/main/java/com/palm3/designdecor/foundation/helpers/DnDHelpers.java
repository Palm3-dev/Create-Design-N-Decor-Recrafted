/* You can use this class (or methods) in your mod, although
    it's KINDA ALL A MESS (but it works)

    The block registers DON'T need the .register() method. <--- but i think you know
*/

package com.palm3.designdecor.foundation.helpers;

import com.electronwill.nightconfig.core.conversion.InvalidValueException;
import com.palm3.designdecor.content.blocks.frontlight.FrontlightBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.RotableSquareSignBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers;
import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.palm3.designdecor.DnDMain.*;
import static com.palm3.designdecor.DnDMain.DND_REGISTRATE;
import static com.palm3.designdecor.DnDMain.asResource;
import static com.palm3.designdecor.foundation.helpers.create_registrate.CTSpriteShiftsHelpers.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.BlockBuilders.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.ItemUtils.customTagOrDefault;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.ItemUtils.itemTag;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateDataGenTransformers.Generators.*;
import static com.palm3.designdecor.content.blocks.frontlight.Frontlight.*;
import static com.palm3.designdecor.foundation.helpers.DnDHelpers.DDStoneBlockBuilders.*;  // this class
import static com.palm3.designdecor.foundation.helpers.DnDHelpers.DataGenTransformers.*;   // this class
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class DnDHelpers {

    public static class DataGenTransformers {
        @SafeVarargs
        /// Transformer that adds an item + tag(s) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> stoneWallDDItemWithModel(@Nullable TagKey<Item>... tags) {
            return b -> b.item().model((c, p) -> {
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
                    throw new IllegalArgumentException("Found wall entry with incorrect name for: " + c.getName());
                }

                p.wallInventory(c.getName(), asDDResource("block/palettes/stone_types/" + subDir + "/" + finalName));

            }).transform(customTagOrDefault(null, tags)).build();
        }



        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (frontlight) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<FrontlightBlock, P>> frontlightBlockDDBlockStateModelAndItem(@Nullable TagKey<Item>... tags) {
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
                    }).transform(RegistrateBlockBuildingHelpers.ItemUtils.customTagOrDefault(null, tags)).build();
        }

        @SafeVarargs
        /// Transformer that creates an item + tag(s), model and a blockstate (standard) from DnD assets. See method declaration for details.
        public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> blockDDParent_BMI(@Nullable TagKey<Item>... tags) {
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
    }

    public static class DDStoneBlockBuilders {
        /* This category only works on this mod, or on mods that use the Design 'N' Decor assets, since those methods search in the DnD assets directory
         * and the model assets are taken from there. */
        /// Returns the basic BlockBuilder for a stone-type Block with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<Block, CreateRegistrate> simpleDDStoneBlock(String name, String textureDir, MapColor mapColor, SoundType sound, String stoneItemTag) {
            return simpleBlock(name, mapColor, sound)
                    .transform(blockBMI(ORIGINAL_MOD_ID, textureDir, itemTag("stone_types/" + stoneItemTag)))
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    });
        }

        /// Returns the basic BlockBuilder for a stone-type SlabBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<SlabBlock, CreateRegistrate> simpleDDStoneSlabBlock(String nameWithout_slab, String texturePath, MapColor mapColor, SoundType sound, String stoneItemTag) {
            return simpleSlabBlock(nameWithout_slab, mapColor, sound)
                    .transform(slabBMI(ORIGINAL_MOD_ID, texturePath, itemTag("stone_types/" + stoneItemTag)))
                    //.transform(simpleBlockDDBlockStateModelAndItem())
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                    });
        }

        /// Returns the basic BlockBuilder for a stone-type StairBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<StairBlock, CreateRegistrate> simpleDDStoneStairBlock(String nameWithout_stair, String textureDir, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound, String stoneItemTag) {
            return simpleStairBlock(nameWithout_stair, parentBlock, mapColor, sound)
                    .transform(stairBMI(ORIGINAL_MOD_ID, textureDir, itemTag("stone_types/" + stoneItemTag)))
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    });
        }

        /// Returns the basic BlockBuilder for a stone-type WallBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<WallBlock, CreateRegistrate> simpleDDStoneWallBlock(String nameWithout_wall, String textureDir, MapColor mapColor, SoundType sound, String stoneItemTag) {
            return simpleWallBlock(nameWithout_wall, mapColor,sound)
                    .transform(wallBM(ORIGINAL_MOD_ID, textureDir))
                    .transform(stoneWallDDItemWithModel(itemTag("stone_types/" + stoneItemTag)))
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    });
        }

        /// Returns the basic BlockBuilder for a stone-type ConnectedPillarBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleDDStonePillarBlock(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
            return simpleConnectedPillar(nameWithout_pillar, true, mapColor, sound, generalPathInBlockDir, textureAcceptsPath, topTextureAcceptsPath)
                    .transform(DataGenTransformers.blockDDParent_BMI(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    });
        }

        /// Returns the basic BlockBuilder for a stone-type ConnectedPillarBlock with given name and properties (recipe: stonecutting the tag stone_types/name).
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleDDStoneLayeredBlock(String name, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
            return DND_REGISTRATE
                    .block("layered_" + name, ConnectedPillarBlock::new)
                    .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(horizKryppersConn(ORIGINAL_MOD_ID, generalPathInBlockDir + "/" + textureAcceptsPath), omniConn(ORIGINAL_MOD_ID, generalPathInBlockDir + "/" + topTextureAcceptsPath))))
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                    .loot((t, g) -> t.dropSelf(g))
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                    .transform(DataGenTransformers.blockDDParent_BMI(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))))
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    });
        }
    }


    public static final Map<String, BlockEntry<Block>> BLOCKS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<SlabBlock>> SLABS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<StairBlock>> STAIRS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<WallBlock>> WALLS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<ConnectedPillarBlock>> PILLARS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<SquareSignBlock>> SIGNS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<RotableSquareSignBlock>> ROTABLE_SIGNS_MAP = new HashMap<>();


    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar, layered) in cut, brick and polished version.
    /// Takes the pillar assets from /block/palettes/stone_types --> /pillar (side) & /cap (top).
    public static void registerStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType) {
        BLOCKS_MAP.put("cut_" + material, simpleDDStoneBlock("cut_" + material, toStonesDir("cut/" + material + "_cut"), generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleDDStoneBlock("polished_cut_" + material, toStonesDir("polished/" + material + "_cut_polished"), generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleDDStoneBlock("cut_" + material + "_bricks", toStonesDir("brick/" + material + "_cut_brick"), generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleDDStoneBlock("small_" + material + "_bricks", toStonesDir("small_brick/" + material + "_cut_small_brick"), generalMapColor, generalSoundType, material).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleDDStoneSlabBlock("cut_" + material, toStonesDir("cut/" + material + "_cut"), generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleDDStoneSlabBlock("polished_cut_" + material, toStonesDir("polished/" + material + "_cut_polished"), generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleDDStoneSlabBlock("cut_" + material + "_brick", toStonesDir("brick/" + material + "_cut_brick"), generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleDDStoneSlabBlock("small_" + material + "_brick", toStonesDir("small_brick/" + material + "_cut_small_brick"), generalMapColor, generalSoundType, material).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleDDStoneStairBlock("cut_" + material, toStonesDir("cut/" + material + "_cut"), () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleDDStoneStairBlock("polished_cut_" + material, toStonesDir("polished/" + material + "_cut_polished"), () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleDDStoneStairBlock("cut_" + material + "_brick", toStonesDir("brick/" + material + "_cut_brick"), () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleDDStoneStairBlock("small_" + material + "_brick", toStonesDir("small_brick/" + material + "_cut_small_brick"), () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());

        WALLS_MAP.put("cut_" + material + "_wall", DDStoneBlockBuilders.simpleDDStoneWallBlock("cut_" + material, toStonesDir("cut/" + material + "_cut"), generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleDDStoneWallBlock("polished_cut_" + material, toStonesDir("polished/" + material + "_cut_polished"), generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("cut_" + material + "_bricks_wall", simpleDDStoneWallBlock("cut_" + material + "_bricks", toStonesDir("brick/" + material + "_cut_brick"), generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("small_" + material + "_bricks_wall", simpleDDStoneWallBlock("small_" + material + "_bricks", toStonesDir("small_brick/" + material + "_cut_small_brick"), generalMapColor, generalSoundType, material).register());

        PILLARS_MAP.put("layered_" + material, simpleDDStoneLayeredBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "layered/" + material + "_cut_layered", "cap/" + material + "_cut_cap", material).register());

        PILLARS_MAP.put(material + "_pillar", simpleDDStonePillarBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "pillar/" + material + "_cut_pillar", "cap/" + material + "_cut_cap", material).register());
    }

    public static void registerNumberAndLetterSignSet() {
        for (int i = 0; i < 10; i++) {
            ROTABLE_SIGNS_MAP.put(i + "_sign", rotableSquareSignBlock(i + "_sign", asDDResource(symbolSignPathPlusName(String.valueOf(i))), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        }

        for (char letter = 'a'; letter <= 'z'; letter++) {
            ROTABLE_SIGNS_MAP.put(letter + "_sign", rotableSquareSignBlock(letter + "_sign", asDDResource(symbolSignPathPlusName(String.valueOf(letter))), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        }

        ROTABLE_SIGNS_MAP.put("equal_sign", rotableSquareSignBlock("equal", asDDResource(symbolSignPathPlusName("equal")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("colon_sign", rotableSquareSignBlock("colon", asDDResource(symbolSignPathPlusName("colon")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("exclamation_point_sign", rotableSquareSignBlock("exclamation_point", asDDResource(symbolSignPathPlusName("exclamation_point")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("left_parenthese_sign", rotableSquareSignBlock("left_parenthese", asDDResource(symbolSignPathPlusName("left_parenthese")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("right_parenthese_sign", rotableSquareSignBlock("right_parenthese", asDDResource(symbolSignPathPlusName("right_parenthese")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("minus_sign", rotableSquareSignBlock("minus", asDDResource(symbolSignPathPlusName("minus")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("period_sign", rotableSquareSignBlock("period", asDDResource(symbolSignPathPlusName("period")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("plus_sign", rotableSquareSignBlock("plus", asDDResource(symbolSignPathPlusName("plus")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("question_mark_sign", rotableSquareSignBlock("question_mark", asDDResource(symbolSignPathPlusName("question_mark")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
        ROTABLE_SIGNS_MAP.put("slash_sign", rotableSquareSignBlock("slash", asDDResource(symbolSignPathPlusName("slash")), null, "brass").transform(rotableSquareSignBM(ORIGINAL_MOD_ID, symbolSignPath())).register());
    }

    /// @return  DD sign texture path in block dir
    public static @NotNull String signPath() { return "old/signs/"; }

    /// @return  DD symbol_sign texture path in block dir
    public static @NotNull String symbolSignPath() { return "old/symbol_signs/"; }

    /// @return  DD sign texture path in textures dir
    public static @NotNull String signPathPlusName(String name) { return "block/old/signs/" + name; }

    /// @return  DD symbol_sign texture path in textures dir
    public static @NotNull String symbolSignPathPlusName(String name) { return "block/old/symbol_signs/" + name; }

    /// @return DD stone_types location + given path
    public static String toStonesDir(String path) { return "block/palettes/stone_types/" + path; }
}
