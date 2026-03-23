package com.palm3.designdecor.register;

import com.palm3.designdecor.helpers.DDWindowGen;
import com.palm3.designdecor.blocks.DiagonalGirderBlock;
import com.palm3.designdecor.blocks.beam.BeamBlock;
import com.palm3.designdecor.blocks.beam.BeamCTBehaviour;
import com.palm3.designdecor.blocks.frontlight.FrontlightBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.*;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.block.connected.*;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;

import static com.palm3.designdecor.DDMain.*;
import static com.palm3.designdecor.helpers.DDBlockBuildingHelpers.*;
import static com.palm3.designdecor.helpers.DDCTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

@SuppressWarnings({"deprecated", "removal"})
public class DDBlocks {
    //============================== Normal blocks ===============================

    // Deepslate Tiles - OK
    public static final BlockEntry<CasingBlock> DEEPSLATE_TILES = DD_REGISTRATE
            .block("deepslate_tiles", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omniConnected("deepslate_tiles")))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, p) -> t.dropSelf(p))
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .transform(itemWithoutModel())
            .register();

    // Red Deepslate Tiles - OK
    public static final BlockEntry<CasingBlock> RED_DEEPSLATE_TILES = DD_REGISTRATE
            .block("red_deepslate_tiles", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omniConnected("red_deepslate_tiles")))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, p) -> t.dropSelf(p))
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .transform(itemWithoutModel())
            .register();

    // Ornate Iron Glass - OK
    public static final BlockEntry<ConnectedGlassBlock> ORNATE_IRON_GLASS = DD_REGISTRATE
            .block("ornate_iron_glass", ConnectedGlassBlock::new)
            .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniLocationConnected("palettes", "ornate_iron_glass"), omniLocationConnected("palettes", "ornate_iron_glass_end"))))
            .addLayer(() -> RenderType::cutout)
            .initialProperties(() -> Blocks.GLASS)
            .properties(p -> p
                    .isValidSpawn((state, getter, pos, entityType) -> false)
                    .isRedstoneConductor((state, getter, pos) -> false)
                    .isSuffocating((state, getter, pos) -> false)
                    .isViewBlocking((state, getter, pos) -> false))
            .transform(noBlockState())
            .loot((t, g) -> t.dropWhenSilkTouch(g))
            .recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS),
                    RecipeCategory.BUILDING_BLOCKS, c::get))
            .tag(Tags.Blocks.GLASS, BlockTags.IMPERMEABLE)
            .transform(itemWithoutModel(Tags.Items.GLASS))
            /*.item()
            .tag(Tags.Items.GLASS)
            .build()*/
            .register();

    // Ornate Iron Glass Pane - OK
    public static final BlockEntry<ConnectedGlassPaneBlock> ORNATE_IRON_GLASS_PANE = DDWindowGen.customWindowPaneWithoutBlockStateAndItemModel(
                "ornate_iron_glass",
                ORNATE_IRON_GLASS,
                () -> omniLocationConnected("palettes", "ornate_iron_glass"),
                () -> RenderType::cutoutMipped)
            .register();

    // Industrial Plating Block - OK
    public static final BlockEntry<CasingBlock> INDUSTRIAL_PLATING_BLOCK = DD_REGISTRATE
            .block("industrial_plating_block", CasingBlock::new)
            .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniConnected("industrial_plating_block_side"), omniConnected("industrial_plating_block"))))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Large Metal Girder - OK
    public static final BlockEntry<ConnectedPillarBlock> LARGE_METAL_GIRDER = DD_REGISTRATE
            .block("large_metal_girder", ConnectedPillarBlock::new)
            .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleConnected("large_girder"), omniConnected("large_girder_top"))))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.METAL_GIRDER.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.METAL_GIRDER.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Beam Block - OK
    public static final BlockEntry<BeamBlock> BEAM = DD_REGISTRATE
            .block("beam", BeamBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .onRegister(connectedTextures(() -> new BeamCTBehaviour(
                    horizKryppersLocationConnected("beam", "beam"),
                    verticalLocationConnected("beam", "beam_top_z"),
                    horizKryppersLocationConnected("beam", "beam_top_x"))))
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(LARGE_METAL_GIRDER.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get(), INDUSTRIAL_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Diagonal Girder Block - OK
    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER_BLOCK = DD_REGISTRATE
            .block("diagonal_girder", DiagonalGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                    .pattern(" # ")
                    .pattern("#  ")
                    .define('#', AllBlocks.METAL_GIRDER.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.METAL_GIRDER.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Ornate Grate - OK
    public static final BlockEntry<CasingBlock> ORNATE_GRATE = DD_REGISTRATE
            .block("ornate_grate", CasingBlock::new)
            .addLayer(() -> RenderType::cutout)
            .transform(BuilderTransformers.casing(() -> omniConnected("ornate_grate")))
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().noOcclusion())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("#I#")
                    .pattern("I I")
                    .pattern("#I#")
                    .define('#', Tags.Items.INGOTS_IRON)
                    .define('I', Tags.Items.RODS_WOODEN)
                    .unlockedBy("has_ingredient", has(AllPaletteBlocks.ORNATE_IRON_WINDOW.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Zinc Bricks - OK
    public static final BlockEntry<Block> ZINC_BRICKS = DD_REGISTRATE
            .block("zinc_bricks", Block::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.ZINC_BLOCK.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.ZINC_BLOCK.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .transform(itemWithoutModel())
            .register();

    // Zinc Checker Tiles - OK
    public static final BlockEntry<CasingBlock> ZINC_CHECKER_TILES = DD_REGISTRATE
            .block("zinc_checker_tiles", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omniConnected("zinc_checker_tiles")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("###")
                    .pattern("#z#")
                    .pattern("###")
                    .define('z', AllBlocks.ZINC_BLOCK.asItem())
                    .define('#', AllItems.ZINC_INGOT)
                    .unlockedBy("has_ingredient", has(AllBlocks.ZINC_BLOCK.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .transform(itemWithoutModel())
            .register();

    // Stone Metal - OK
    public static final BlockEntry<CasingBlock> STONE_METAL = DD_REGISTRATE
            .block("stone_metal", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omniConnected("stone_metal")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(DDSoundTypes.METAL_HEAVY).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("AP")
                    .pattern("PA")
                    .define('P', AllTags.AllItemTags.PLATES.tag)
                    .define('A', AllPaletteStoneTypes.ASURINE.baseBlock.get())
                    .unlockedBy("has_ingredient", has(AllPaletteStoneTypes.ASURINE.baseBlock.get()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            .transform(itemWithoutModel())
            .register();

    // Colored Stone Metals - OK
    public static final DyedBlockList<Block> DYED_STONE_METALS = new DyedBlockList<>(color -> {
        var baseID = "stone_metal";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        var ct = omniLocationConnected("stone_metal", colorID);
        return DD_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(DDSoundTypes.METAL_HEAVY).strength(1.5f,2f))
                .transform(noBlockState())
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ct)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct)))
                .transform(pickaxeOnly())
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                            .pattern("ASA")
                            .pattern("SDS")
                            .pattern("ASA")
                            .define('S', ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "plates/iron")))
                            .define('A', AllPaletteStoneTypes.ASURINE.baseBlock.get())
                            .define('D', color.getTag())
                            .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                            .pattern("SSS").pattern("SDS").pattern("SSS")
                            .define('S', ItemTags.create(asResource("stone_metals")))
                            .define('D', color.getTag())
                            .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName() + "_dyed"));
                })
                .item().tag(ItemTags.create(asResource("stone_metals"))).build()
                .transform(itemWithoutModel())
                .register();
    });

    // Colored Velvet Blocks
    public static final DyedBlockList<Block> DYED_VELVET_BLOCKS = new DyedBlockList<>(color -> {
        var baseID = "velvet_block";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        return DD_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(SoundType.WOOL).strength(0.5f,0.5f))
                .transform(noBlockState())
                .loot((t, g) -> t.dropSelf(g))
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                            .pattern("Wn ")
                            .pattern("nW ")
                            .define('W', ItemTags.WOOL)
                            .define('n', ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "nuggets/brass")))
                            .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                })
                .item().tag(ItemTags.WOOL).build()
                .transform(itemWithoutModel())
                .register();
    });

    // Dark Metal Block
    public static final BlockEntry<Block> DARK_METAL_BLOCK = simpleBlock("dark_metal_block", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .define('#', AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Plating
    public static final BlockEntry<CasingBlock> DARK_METAL_PLATING = DD_REGISTRATE
            .block("dark_metal_plating", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omniConnected("dark_metal_plating")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', DDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .transform(noBlockState())
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Block Slab
    public static final BlockEntry<SlabBlock> DARK_METAL_BLOCK_SLAB = simpleSlabBlock("dark_metal_block", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .define('#', DDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Block Stairs
    public static final BlockEntry<StairBlock> DARK_METAL_BLOCK_STAIRS = simpleStairBlock("dark_metal_block", DDBlocks.DARK_METAL_BLOCK, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', DDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Bricks
    public static final BlockEntry<Block> DARK_METAL_BRICKS = simpleBlock("dark_metal_bricks", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .define('#', DDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Brick Slab
    public static final BlockEntry<SlabBlock> DARK_METAL_BRICK_SLAB = simpleSlabBlock("dark_metal_brick", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .define('#', DDBlocks.DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Dark Metal Brick Stairs
    public static final BlockEntry<StairBlock> DARK_METAL_BRICK_STAIRS = simpleStairBlock("dark_metal_brick", DDBlocks.DARK_METAL_BRICKS, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', DDBlocks.DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .transform(noBlockState())
            //.item().tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))).build()
            .transform(itemWithoutModel(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .register();

    // Brass Frontlight
    public static final BlockEntry<FrontlightBlock> BRASS_FRONTLIGHT = DD_REGISTRATE
            .block("brass_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/brass")))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Andesite Frontlight
    public static final BlockEntry<FrontlightBlock> ANDESITE_FRONTLIGHT = DD_REGISTRATE
            .block("andesite_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', AllItems.ANDESITE_ALLOY)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Zinc Frontlight
    public static final BlockEntry<FrontlightBlock> ZINC_FRONTLIGHT = DD_REGISTRATE
            .block("zinc_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/zinc")))
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Copper Frontlight
    public static final BlockEntry<FrontlightBlock> COPPER_FRONTLIGHT = DD_REGISTRATE
            .block("copper_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', Tags.Items.INGOTS_COPPER)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Industrial Frontlight
    public static final BlockEntry<FrontlightBlock> INDUSTRIAL_FRONTLIGHT = DD_REGISTRATE
            .block("industrial_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', AllBlocks.INDUSTRIAL_IRON_BLOCK)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Gold Frontlight
    public static final BlockEntry<FrontlightBlock> GOLD_FRONTLIGHT = DD_REGISTRATE
            .block("gold_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', Tags.Items.INGOTS_GOLD)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Iron Frontlight
    public static final BlockEntry<FrontlightBlock> IRON_FRONTLIGHT = DD_REGISTRATE
            .block("iron_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .define('i', Tags.Items.INGOTS_IRON)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .transform(itemWithoutModel())
            .register();

    // Netherite Frontlight
    public static final BlockEntry<FrontlightBlock> NETHERITE_FRONTLIGHT = DD_REGISTRATE
            .block("netherite_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(noBlockState())
            .loot((t, g) -> t.dropSelf(g))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("li ")
                        .pattern("is ")
                        .define('i', DataIngredient.items(Items.NETHERITE_INGOT))
                        .define('l', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('s', Tags.Items.STONE)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                        .pattern("LI ")
                        .pattern("IS ")
                        .define('I', AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()) // <-- Don't mess with me, i'm not going to make you craft a lamp with 2 damn netherite ingots. you get less but you don't waste ingots.
                        .define('L', DataIngredient.items(Items.BLAZE_ROD, Items.GLOWSTONE_DUST, Items.PRISMARINE_CRYSTALS))
                        .define('S', Tags.Items.STONE)
                        .unlockedBy("has_" + AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem(), has(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem())).save(p, asResource("crafting/" + c.getName() + "_from_industrial_iron_block"));
            })
            .transform(itemWithoutModel())
            .register();

    // Brass Floor
    public static final BlockEntry<Block> BRASS_FLOOR = simpleBlock("brass_floor", MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/brass"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Andesite Floor
    public static final BlockEntry<Block> ANDESITE_FLOOR = simpleBlock("andesite_floor", MapColor.COLOR_CYAN, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.items(AllItems.ANDESITE_ALLOY.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Zinc Floor
    public static final BlockEntry<Block> ZINC_FLOOR = simpleBlock("zinc_floor", MapColor.COLOR_LIGHT_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asExternalResource("forge", "ingots/zinc"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Copper Floor
    public static final BlockEntry<Block> COPPER_FLOOR = simpleBlock("copper_floor", MapColor.TERRACOTTA_ORANGE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_COPPER), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Industrial Floor
    public static final BlockEntry<Block> INDUSTRIAL_FLOOR = simpleBlock("industrial_floor", MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Gold Floor
    public static  final  BlockEntry<Block> GOLD_FLOOR = simpleBlock("gold_floor", MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_GOLD), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Iron Floor
    public static final BlockEntry<Block> IRON_FLOOR = simpleBlock("iron_floor", MapColor.TERRACOTTA_WHITE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c ,2))
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();

    // Netherite Floor
    public static final BlockEntry<Block> NETHERITE_FLOOR = simpleBlock("netherite_floor", MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(Items.NETHERITE_INGOT), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .transform(noBlockState())
            .transform(itemWithoutModel())
            .register();


    public static final BlockEntry<Block> WEATHERED_LIMESTONE = simpleStoneBlock("weathered_limestone", MapColor.COLOR_GRAY, SoundType.STONE, "weathered_limestone").transform(noBlockState()).transform(itemWithoutModel()).register();
    public static final BlockEntry<Block> GABBRO = simpleStoneBlock("gabbro", MapColor.COLOR_GRAY, SoundType.STONE, "gabbro").transform(noBlockState()).transform(itemWithoutModel()).register();
    public static final BlockEntry<Block> DOLOMITE = simpleStoneBlock("dolomite", MapColor.COLOR_GRAY, SoundType.STONE, "dolomite").transform(noBlockState()).transform(itemWithoutModel()).register();

    static {
        registerStoneBlockSetWithoutBlockStateAndItemModel("stone", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);
        registerStoneBlockSetWithoutBlockStateAndItemModel("packed_mud", MapColor.COLOR_LIGHT_GRAY, SoundType.MUD_BRICKS);
        registerStoneBlockSetWithoutBlockStateAndItemModel("amethyst", MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST);
        registerStoneBlockSetWithoutBlockStateAndItemModel("netherrack", MapColor.TERRACOTTA_RED, SoundType.NETHERRACK);
        registerStoneBlockSetWithoutBlockStateAndItemModel("basalt", MapColor.TERRACOTTA_GRAY, SoundType.BASALT);
        registerStoneBlockSetWithoutBlockStateAndItemModel("blackstone", MapColor.TERRACOTTA_BLACK, SoundType.STONE);
        registerStoneBlockSetWithoutBlockStateAndItemModel("weathered_limestone", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);
        registerStoneBlockSetWithoutBlockStateAndItemModel("gabbro", MapColor.TERRACOTTA_ORANGE, SoundType.STONE);
        registerStoneBlockSetWithoutBlockStateAndItemModel("dolomite", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);
    }

    public static void register() {}
}
