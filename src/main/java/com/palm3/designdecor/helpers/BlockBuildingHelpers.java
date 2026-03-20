package com.palm3.designdecor.helpers;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.RotatedPillarCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.palm3.designdecor.DDMain.DD_REGISTRATE;
import static com.palm3.designdecor.DDMain.asResource;
import static com.palm3.designdecor.helpers.CTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class BlockBuildingHelpers {
    // Simple
    /*private static BlockBuilder<Block, CreateRegistrate> simpleBlock(String name, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(name, Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem();
    }

    private static BlockBuilder<SlabBlock, CreateRegistrate> simpleSlabBlock(String baseName, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(baseName + "_slab", SlabBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem();
    }

    private static BlockBuilder<StairBlock, CreateRegistrate> simpleStairBlock(String baseName, Block parentBlock, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(baseName + "_stairs", p -> new StairBlock(parentBlock.defaultBlockState(), p))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem();
    }*/

    //  ------- Stone Like Blocks Registers -------
    /*private static BlockEntry<Block> registerSimpleStoneBlock(String name, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(name, Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build()
                .register();
    }

    private static BlockEntry<SlabBlock> registerSimpleStoneSlabBlock(String name, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(name, SlabBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build()
                .register();
    }

    private static BlockEntry<StairBlock> registerSimpleStoneStairBlock(String name, Block parentBlock, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(name, p -> new StairBlock(parentBlock.defaultBlockState(), p))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build()
                .register();
    }*/

    // ------- Stone Like Blocks Builders -------
    // These methods add the item tag with the name of the block to dndecor:stone_types/x
    public static BlockBuilder<Block, CreateRegistrate> simpleStoneBlock(String name, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(name, Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    public static BlockBuilder<SlabBlock, CreateRegistrate> simpleStoneSlabBlock(String nameWithout_slab, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_slab + "_slab", SlabBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    public static BlockBuilder<StairBlock, CreateRegistrate> simpleStoneStairBlock(String nameWithout_stair, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_stair + "_stairs", p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    public static BlockBuilder<WallBlock, CreateRegistrate> simpleStoneWallBlock(String nameWithout_stair, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_stair + "_wall", WallBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleStonePillarBlock(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_pillar + "_pillar", ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(omniLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }


    // ---------- Stone Like Blocks Registration Methods ----------
    public static final Map<String, BlockEntry<Block>> STONE_BLOCKS_SET = new HashMap<>();
    public static final Map<String, BlockEntry<SlabBlock>> STONE_SLABS_SET = new HashMap<>();
    public static final Map<String, BlockEntry<StairBlock>> STONE_STAIRS_SET = new HashMap<>();
    public static final Map<String, BlockEntry<WallBlock>> STONE_WALLS_SET = new HashMap<>();
    public static final Map<String, BlockEntry<ConnectedPillarBlock>> STONE_PILLARS_SET = new HashMap<>();

    public static void registerSimpleStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType, String stoneItemTag, String pillarTextureGeneralPath, String pillarTextureAcceptsPath, String pillarTopTextureAcceptsPath){
        STONE_BLOCKS_SET.put("cut_" + material, simpleStoneBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_BLOCKS_SET.put("polished_cut_" + material, simpleStoneBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_BLOCKS_SET.put("cut_" + material + "_bricks", simpleStoneBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_BLOCKS_SET.put("small_" + material + "_bricks", simpleStoneBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).register());

        STONE_SLABS_SET.put("cut_" + material + "_slab", simpleStoneSlabBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_SLABS_SET.put("polished_cut_" + material + "_slab", simpleStoneSlabBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_SLABS_SET.put("cut_" + material + "_brick_slab", simpleStoneSlabBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_SLABS_SET.put("small_" + material + "_brick_slab", simpleStoneSlabBlock("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());

        STONE_STAIRS_SET.put("cut_" + material + "_stairs", simpleStoneStairBlock("cut_" + material, () -> STONE_BLOCKS_SET.get("cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_STAIRS_SET.put("polished_cut_" + material + "_stairs", simpleStoneStairBlock("polished_cut_" + material, () -> STONE_BLOCKS_SET.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_STAIRS_SET.put("cut_" + material + "_brick_stairs", simpleStoneStairBlock("cut_" + material + "_brick", () -> STONE_BLOCKS_SET.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_STAIRS_SET.put("small_" + material + "_brick_stairs", simpleStoneStairBlock("small_" + material + "_brick", () -> STONE_BLOCKS_SET.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).register());

        STONE_WALLS_SET.put("cut_" + material + "_wall", simpleStoneWallBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_WALLS_SET.put("polished_cut_" + material + "_wall", simpleStoneWallBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_WALLS_SET.put("cut_" + material + "_brick_wall", simpleStoneWallBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());
        STONE_WALLS_SET.put("small_" + material + "_brick_wall", simpleStoneWallBlock("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());

        STONE_PILLARS_SET.put(material + "_pillar", simpleStonePillarBlock(material, generalMapColor, generalSoundType, pillarTextureGeneralPath, pillarTextureAcceptsPath, pillarTopTextureAcceptsPath, stoneItemTag).register());
    }

    public static void registerSimpleStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType){
        STONE_BLOCKS_SET.put("cut_" + material, simpleStoneBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_BLOCKS_SET.put("polished_cut_" + material, simpleStoneBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_BLOCKS_SET.put("cut_" + material + "_bricks", simpleStoneBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, material).register());
        STONE_BLOCKS_SET.put("small_" + material + "_bricks", simpleStoneBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, material).register());

        STONE_SLABS_SET.put("cut_" + material + "_slab", simpleStoneSlabBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_SLABS_SET.put("polished_cut_" + material + "_slab", simpleStoneSlabBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_SLABS_SET.put("cut_" + material + "_brick_slab", simpleStoneSlabBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, material).register());
        STONE_SLABS_SET.put("small_" + material + "_brick_slab", simpleStoneSlabBlock("small_" + material + "_brick", generalMapColor, generalSoundType, material).register());

        STONE_STAIRS_SET.put("cut_" + material + "_stairs", simpleStoneStairBlock("cut_" + material, () -> STONE_BLOCKS_SET.get("cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STONE_STAIRS_SET.put("polished_cut_" + material + "_stairs", simpleStoneStairBlock("polished_cut_" + material, () -> STONE_BLOCKS_SET.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STONE_STAIRS_SET.put("cut_" + material + "_brick_stairs", simpleStoneStairBlock("cut_" + material + "_brick", () -> STONE_BLOCKS_SET.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());
        STONE_STAIRS_SET.put("small_" + material + "_brick_stairs", simpleStoneStairBlock("small_" + material + "_brick", () -> STONE_BLOCKS_SET.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());

        STONE_WALLS_SET.put("cut_" + material + "_wall", simpleStoneWallBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_WALLS_SET.put("polished_cut_" + material + "_wall", simpleStoneWallBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        STONE_WALLS_SET.put("cut_" + material + "_brick_wall", simpleStoneWallBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, material).register());
        STONE_WALLS_SET.put("small_" + material + "_brick_wall", simpleStoneWallBlock("small_" + material + "_brick", generalMapColor, generalSoundType, material).register());

        STONE_PILLARS_SET.put(material + "_pillar", simpleStonePillarBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "palettes/stone_types/pillar/" + material, "palettes/stone_types/cap/" + material, material).register());
    }
}