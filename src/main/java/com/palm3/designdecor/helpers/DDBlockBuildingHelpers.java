package com.palm3.designdecor.helpers;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.content.decoration.palettes.LayeredBlock;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.RotatedPillarCTBehaviour;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.palm3.designdecor.DDMain.DD_REGISTRATE;
import static com.palm3.designdecor.DDMain.asResource;
import static com.palm3.designdecor.helpers.DDCTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class DDBlockBuildingHelpers {
    //============================= Block Builders ===============================

    // ------- Datagen disable / fixes -------
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> noBlockState() {
        return b -> b.blockstate((c, p) -> {});
    }

    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> itemWithoutModel() {
        return b -> b.item().model((c, p) -> {}).build();
    }

    @SafeVarargs
    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> itemWithoutModel(TagKey<Item>... tag) {
        return b -> b.item().tag(tag).model((c, p) -> {}).build();
    }

    // ------- Simple Blocks Builders -------

    // These methods add don't have the tag, recipe and item.
    /// Creates a simple block with given name and properties (plus dropSelf and needs iron pickaxe). Needs register().
    /// Doesn't have crafting, tags or item.
    public static BlockBuilder<Block, CreateRegistrate> simpleBlock(String name, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(name, Block::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    /// Creates a simple slab with given name and properties (plus dropSelf and needs iron pickaxe). Needs register().
    /// Doesn't have crafting, tags or item.
    public static BlockBuilder<SlabBlock, CreateRegistrate> simpleSlabBlock(String nameWithout_slab, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(nameWithout_slab + "_slab", SlabBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    /// Creates a simple stair with given name and properties (plus dropSelf and needs iron pickaxe). Needs register().
    /// Doesn't have crafting, tags or item.
    public static BlockBuilder<StairBlock, CreateRegistrate> simpleStairBlock(String nameWithout_stairs, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(nameWithout_stairs + "_stairs", p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    /// Creates a simple wall with given name and properties (plus dropSelf and needs iron pickaxe). Needs register().
    /// Doesn't have crafting, tags or item.
    public static BlockBuilder<WallBlock, CreateRegistrate> simpleWallBlock(String nameWithout_wall, MapColor mapColor, SoundType sound) {
        return DD_REGISTRATE
                .block(nameWithout_wall + "_wall", WallBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS);
    }

    /// Creates a simple pillar with given name and properties (plus dropSelf and needs iron pickaxe). Needs register().
    /// Doesn't have crafting, tags or item.
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simplePillarBlock(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath) {
        return DD_REGISTRATE
                .block(nameWithout_pillar + "_pillar", ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    // ------- Simple Stone Block Builders - WITH item model --------

    // These methods add the item tag with the name of the block to dndecor:stone_types/x
    /// Creates a simple stone-type block with given name and properties (stonecutting tag stone_types/name). Needs register()
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

    /// Creates a simple stone-type slab with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<SlabBlock, CreateRegistrate> simpleStoneSlabBlock(String nameWithout_slab, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_slab + "_slab", SlabBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    /// Creates a simple stone-type stair with given name and properties (stonecutting tag stone_types/name). Needs register()
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

    /// Creates a simple stone-type wall with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<WallBlock, CreateRegistrate> simpleStoneWallBlock(String nameWithout_wall, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_wall + "_wall", WallBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    /// Creates a simple stone-type connected pillar with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleStonePillarBlock(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_pillar + "_pillar", ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    /// Creates a simple stone-type connected pillar with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleStoneLayeredBlock(String name, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DD_REGISTRATE
                .block("layered_" + name, ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(horizKryppersLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .item().tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))).build();
    }

    // ------- Simple Stone Block Builders - WITHOUT item model --------

    // These methods add the item tag with the name of the block to dndecor:stone_types/x
    /// Creates a simple stone-type block with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<Block, CreateRegistrate> simpleStoneBlockWithoutItemModel(String name, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(name, Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    /// Creates a simple stone-type slab with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<SlabBlock, CreateRegistrate> simpleStoneSlabBlockWithoutItemModel(String nameWithout_slab, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_slab + "_slab", SlabBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    /// Creates a simple stone-type stair with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<StairBlock, CreateRegistrate> simpleStoneStairBlockWithoutItemModel(String nameWithout_stair, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_stair + "_stairs", p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    /// Creates a simple stone-type wall with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<WallBlock, CreateRegistrate> simpleStoneWallBlockWithoutItemModel(String nameWithout_wall, MapColor mapColor, SoundType sound, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_wall + "_wall", WallBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    /// Creates a simple stone-type connected pillar with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleStonePillarBlockWithoutItemModel(String nameWithout_pillar, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DD_REGISTRATE
                .block(nameWithout_pillar + "_pillar", ConnectedPillarBlock::new)
                .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    /// Creates a simple stone-type layered block with given name and properties (stonecutting tag stone_types/name). Needs register()
    public static BlockBuilder<LayeredBlock, CreateRegistrate> simpleStoneLayeredBlockWithoutItemModel(String name, MapColor mapColor, SoundType sound, String generalPathInBlockDir, String textureAcceptsPath, String topTextureAcceptsPath, String stoneItemTag) {
        return DD_REGISTRATE
                .block("layered_" + name, LayeredBlock::new)
                .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(horizKryppersLocationConnected(generalPathInBlockDir, textureAcceptsPath), omniLocationConnected(generalPathInBlockDir, topTextureAcceptsPath))))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                .loot((t, g) -> t.dropSelf(g))
                .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
                .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                })
                .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("stone_types/" + stoneItemTag))));
    }

    //============================= Registrations ===============================
    public static final Map<String, BlockEntry<Block>> BLOCKS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<SlabBlock>> SLABS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<StairBlock>> STAIRS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<WallBlock>> WALLS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<ConnectedPillarBlock>> PILLARS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<LayeredBlock>> LAYERED_BLOCKS_MAP = new HashMap<>();


    // ---------- Stone Like Block Set Registration Methods ----------

    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar) in cut, brick and polished version.
    /// Accepts custom paths for the pillar texture, and the texture also accepts paths.
    /// Does NOT need register()
    public static void registerStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType, String stoneItemTag, String pillarTextureGeneralPath, String pillarTextureAcceptsPath, String pillarTopTextureAcceptsPath){
        BLOCKS_MAP.put("cut_" + material, simpleStoneBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleStoneBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleStoneBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleStoneBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleStoneSlabBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleStoneSlabBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleStoneSlabBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleStoneSlabBlock("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleStoneStairBlock("cut_" + material, () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleStoneStairBlock("polished_cut_" + material, () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleStoneStairBlock("cut_" + material + "_brick", () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleStoneStairBlock("small_" + material + "_brick", () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).register());

        WALLS_MAP.put("cut_" + material + "_wall", simpleStoneWallBlock("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleStoneWallBlock("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).register());
        WALLS_MAP.put("cut_" + material + "_brick_wall", simpleStoneWallBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());
        WALLS_MAP.put("small_" + material + "_brick_wall", simpleStoneWallBlock("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).register());

        PILLARS_MAP.put("layered_" + material, simpleStoneLayeredBlock(material, generalMapColor, generalSoundType, pillarTextureGeneralPath, pillarTextureAcceptsPath, pillarTopTextureAcceptsPath, stoneItemTag).register());

        PILLARS_MAP.put(material + "_pillar", simpleStonePillarBlock(material, generalMapColor, generalSoundType, pillarTextureGeneralPath, pillarTextureAcceptsPath, pillarTopTextureAcceptsPath, stoneItemTag).register());
    }

    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar) in cut, brick and polished version.
    /// Takes the pillar assets from /block/palettes/stone_types -> /pillar (side) & /cap (top).
    /// Does NOT need register()
    public static void registerStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType) {
        BLOCKS_MAP.put("cut_" + material, simpleStoneBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleStoneBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleStoneBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleStoneBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, material).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleStoneSlabBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleStoneSlabBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleStoneSlabBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleStoneSlabBlock("small_" + material + "_brick", generalMapColor, generalSoundType, material).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleStoneStairBlock("cut_" + material, () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleStoneStairBlock("polished_cut_" + material, () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleStoneStairBlock("cut_" + material + "_brick", () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleStoneStairBlock("small_" + material + "_brick", () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());

        WALLS_MAP.put("cut_" + material + "_wall", simpleStoneWallBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleStoneWallBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("cut_" + material + "_brick_wall", simpleStoneWallBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("small_" + material + "_brick_wall", simpleStoneWallBlock("small_" + material + "_brick", generalMapColor, generalSoundType, material).register());

        PILLARS_MAP.put("layered_" + material, simpleStoneLayeredBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "layered/" + material + "_cut_layered", "cap/" + material + "cut_cap", material).register());

        PILLARS_MAP.put(material + "_pillar", simpleStonePillarBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "pillar/" + material + "_cut_pillar", "cap/" + material + "_cut_cap", material).register());
    }

    /// !! Differs by the others because this doesn't generate a blockstate.
    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar) in cut, brick and polished version.
    /// Accepts custom paths for the pillar texture, and the texture also accepts paths.
    /// Does NOT need register()
    public static void registerStoneBlockSetWithoutBlockStateAndItemModel(String material, MapColor generalMapColor, SoundType generalSoundType, String stoneItemTag, String pillarTextureGeneralPath, String pillarTextureAcceptsPath, String pillarTopTextureAcceptsPath){
        BLOCKS_MAP.put("cut_" + material, simpleStoneBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleStoneBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleStoneBlockWithoutItemModel("cut_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleStoneBlockWithoutItemModel("small_" + material + "_bricks", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleStoneSlabBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleStoneSlabBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleStoneSlabBlockWithoutItemModel("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleStoneSlabBlockWithoutItemModel("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleStoneStairBlockWithoutItemModel("cut_" + material, () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleStoneStairBlockWithoutItemModel("polished_cut_" + material, () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleStoneStairBlockWithoutItemModel("cut_" + material + "_brick", () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleStoneStairBlockWithoutItemModel("small_" + material + "_brick", () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());

        WALLS_MAP.put("cut_" + material + "_wall", simpleStoneWallBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleStoneWallBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        WALLS_MAP.put("cut_" + material + "_brick_wall", simpleStoneWallBlockWithoutItemModel("cut_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());
        WALLS_MAP.put("small_" + material + "_brick_wall", simpleStoneWallBlockWithoutItemModel("small_" + material + "_brick", generalMapColor, generalSoundType, stoneItemTag).transform(noBlockState()).register());

        LAYERED_BLOCKS_MAP.put("layered_" + material, simpleStoneLayeredBlockWithoutItemModel(material, generalMapColor, generalSoundType, pillarTextureGeneralPath, pillarTextureAcceptsPath, pillarTopTextureAcceptsPath, stoneItemTag).transform(noBlockState()).register());

        PILLARS_MAP.put(material + "_pillar", simpleStonePillarBlockWithoutItemModel(material, generalMapColor, generalSoundType, pillarTextureGeneralPath, pillarTextureAcceptsPath, pillarTopTextureAcceptsPath, stoneItemTag).transform(noBlockState()).register());
    }

    /// !! Differs by the others because this doesn't generate a blockstate.
    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar) in cut, brick and polished version.
    /// Takes the pillar assets from /block/palettes/stone_types -> /pillar (side) & /cap (top).
    /// Does NOT need register()
    public static void registerStoneBlockSetWithoutBlockStateAndItemModel(String material, MapColor generalMapColor, SoundType generalSoundType) {
        BLOCKS_MAP.put("cut_" + material, simpleStoneBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleStoneBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleStoneBlockWithoutItemModel("cut_" + material + "_bricks", generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleStoneBlockWithoutItemModel("small_" + material + "_bricks", generalMapColor, generalSoundType, material).transform(noBlockState()).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleStoneSlabBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleStoneSlabBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleStoneSlabBlockWithoutItemModel("cut_" + material + "_brick", generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleStoneSlabBlockWithoutItemModel("small_" + material + "_brick", generalMapColor, generalSoundType, material).transform(noBlockState()).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleStoneStairBlockWithoutItemModel("cut_" + material, () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleStoneStairBlockWithoutItemModel("polished_cut_" + material, () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleStoneStairBlockWithoutItemModel("cut_" + material + "_brick", () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleStoneStairBlockWithoutItemModel("small_" + material + "_brick", () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).transform(noBlockState()).register());

        WALLS_MAP.put("cut_" + material + "_wall", simpleStoneWallBlockWithoutItemModel("cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleStoneWallBlockWithoutItemModel("polished_cut_" + material, generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        WALLS_MAP.put("cut_" + material + "_brick_wall", simpleStoneWallBlockWithoutItemModel("cut_" + material + "_brick", generalMapColor, generalSoundType, material).transform(noBlockState()).register());
        WALLS_MAP.put("small_" + material + "_brick_wall", simpleStoneWallBlockWithoutItemModel("small_" + material + "_brick", generalMapColor, generalSoundType, material).transform(noBlockState()).register());

        LAYERED_BLOCKS_MAP.put("layered_" + material, simpleStoneLayeredBlockWithoutItemModel(material, generalMapColor, generalSoundType, "palettes/stone_types", "layered/" + material + "_cut_layered", "cap/" + material + "_cut_cap", material).transform(noBlockState()).register());

        PILLARS_MAP.put(material + "_pillar", simpleStonePillarBlockWithoutItemModel(material, generalMapColor, generalSoundType, "palettes/stone_types", "pillar/" + material + "_cut_pillar", "cap/" + material + "_cut_cap", material).transform(noBlockState()).register());
    }
}