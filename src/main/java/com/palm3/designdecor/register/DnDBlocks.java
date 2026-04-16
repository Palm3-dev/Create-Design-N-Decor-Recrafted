package com.palm3.designdecor.register;

import com.palm3.designdecor.content.blocks.supports.DiagonalMetalSupportBlock;
import com.palm3.designdecor.content.blocks.supports.MetalSupport;
import com.palm3.designdecor.content.blocks.supports.MetalSupportBlock;
import com.palm3.designdecor.content.blocks.OrnateGrateBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.RotableSquareSignBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.palm3.designdecor.content.blocks.diagonal_girder.DiagonalGirderBlock;
import com.palm3.designdecor.content.blocks.beam.BeamBlock;
import com.palm3.designdecor.content.blocks.beam.BeamCTBehaviour;
import com.palm3.designdecor.content.blocks.frontlight.FrontlightBlock;
import com.palm3.designdecor.foundation.helpers.DnDHelpers;
import com.simibubi.create.*;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.*;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.block.connected.*;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
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
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import static com.palm3.designdecor.DnDMain.*;
import static com.palm3.designdecor.foundation.helpers.DnDHelpers.DDStoneBlockBuilders.simpleDDStoneBlock;
import static com.palm3.designdecor.foundation.helpers.DnDHelpers.DataGenTransformers.*;

import static com.palm3.designdecor.foundation.helpers.create_registrate.CTSpriteShiftsHelpers.*;

import static com.palm3.designdecor.foundation.helpers.DnDHelpers.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.BlockBuilders.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.ItemUtils.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateDataGenTransformers.Generators.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

@SuppressWarnings({"deprecated", "removal"})
public class DnDBlocks {
    //============================== Normal blocks ===============================
    // DPM = Direct Parent Model, so has as parent a Design 'n' Decor model

    // Deepslate Tiles
    public static final BlockEntry<CasingBlock> DEEPSLATE_TILES = simpleCasing("deepslate_tiles", MapColor.TERRACOTTA_GRAY, SoundType.DEEPSLATE, "deepslate_tiles")
            .transform(blockBMI(ORIGINAL_MOD_ID, "block/deepslate_tiles"))
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .register();

    // Red Deepslate Tiles
    public static final BlockEntry<CasingBlock> RED_DEEPSLATE_TILES = simpleCasing("red_deepslate_tiles", MapColor.TERRACOTTA_GRAY, SoundType.DEEPSLATE, "red_deepslate_tiles")
            .transform(blockBMI(ORIGINAL_MOD_ID, "block/red_deepslate_tiles"))
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .register();

    // Ornate Iron Glass
    public static final BlockEntry<ConnectedGlassBlock> ORNATE_IRON_GLASS = simpleConnectedGlass("ornate_iron_glass", "ornate_iron_glass", "ornate_iron_glass_end")
            .blockstate((c, p) -> {
                var model = p.models().withExistingParent(c.getName(), asNamespaceResource("minecraft", "block/cube_column"))
                        .texture("end", asDDResource("block/palettes/ornate_iron_glass_end"))
                        .texture("side", asDDResource("block/palettes/ornate_iron_glass"));
                p.simpleBlock(c.getEntry(), model);
            })
            .simpleItem()
            .recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS),
                    RecipeCategory.BUILDING_BLOCKS, c::get))
            .register();

    // Ornate Iron Glass Pane - DPM
    public static final BlockEntry<ConnectedGlassPaneBlock> ORNATE_IRON_GLASS_PANE = DND_REGISTRATE
            .block("ornate_iron_glass_pane", ConnectedGlassPaneBlock::new)
            .addLayer(() -> RenderType::cutout)
            .onRegister(connectedTextures(() -> new GlassPaneCTBehaviour(omniConn(ORIGINAL_MOD_ID, "palettes/ornate_iron_glass"))))
            .initialProperties(() -> Blocks.GLASS_PANE)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .loot(RegistrateBlockLootTables::dropSelf)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("###")
                    .pattern("###")
                    .define('#', DnDBlocks.ORNATE_IRON_GLASS)
                    .unlockedBy("has_ingredient", has(DnDBlocks.ORNATE_IRON_GLASS))
                    .save(p);
                p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_PANES_COLORLESS), RecipeCategory.BUILDING_BLOCKS, c::get);
            })
            .blockstate((c, p) -> {
                var post = p.models().withExistingParent(c.getName() + "_post", asDDResource("block/ornate_iron_glass_pane_post"));
                var side = p.models().withExistingParent(c.getName() + "_side", asDDResource("block/ornate_iron_glass_pane_side"));
                var sideAlt = p.models().withExistingParent(c.getName() + "_side_alt", asDDResource("block/ornate_iron_glass_pane_side_alt"));
                var noSide = p.models().withExistingParent(c.getName() + "_noside", asDDResource("block/ornate_iron_glass_pane_noside"));
                var noSideAlt = p.models().withExistingParent(c.getName() + "_noside_alt", asDDResource("block/ornate_iron_glass_pane_noside_alt"));
                p.paneBlock(c.get(), post, side, sideAlt, noSide, noSideAlt);
            })
            .item()
            .tag(Tags.Items.GLASS_PANES, Tags.Items.GLASS_PANES_COLORLESS)
            .model((c, p) -> p.generated(c, asDDResource("block/palettes/ornate_iron_glass")))
            .build()
            .register();

    // Industrial Plating Block
    public static final BlockEntry<Block> INDUSTRIAL_PLATING_BLOCK = DND_REGISTRATE
            .block("industrial_plating_block", Block::new)
            .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniConn(ORIGINAL_MOD_ID, "industrial_plating_block_side"), omniConn(ORIGINAL_MOD_ID, "industrial_plating_block"))))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .register();

    // Large Metal Girder - DPM
    public static final BlockEntry<ConnectedPillarBlock> LARGE_METAL_GIRDER = simpleConnectedPillar("large_metal_girder", false, MapColor.COLOR_GRAY, SoundType.NETHERITE_BLOCK, "large_girder", "large_girder_top")
            .transform(DnDHelpers.DataGenTransformers.blockDDParent_BMI())  // MAYBE, but MAYBE, fix with blockBMI (conn. texture not working)
            .blockstate((c, p) -> {
                p.models().withExistingParent(c.getName(), asDDResource(c.getName()));
                p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
            })
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.METAL_GIRDER.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.METAL_GIRDER.asItem()))
                    .save(p))
            .register();

    // Beam Block - DPM
    public static final BlockEntry<BeamBlock> BEAM = DND_REGISTRATE
            .block("beam", BeamBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops())
            .onRegister(connectedTextures(() -> new BeamCTBehaviour(
                    horizKryppersConn(ORIGINAL_MOD_ID, "beam/beam"),
                    verticalConn(ORIGINAL_MOD_ID, "beam/beam_top_z"),
                    horizKryppersConn(ORIGINAL_MOD_ID, "beam/beam_top_x"))))
            .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates((s) -> {
                BeamBlock.BeamStates beam = s.getValue(BeamBlock.BEAM);
                String axis = s.getValue(BeamBlock.AXIS) == Direction.Axis.X ? "_x" : "_z";
                ModelFile.ExistingModelFile modelBoth = p.models().getExistingFile(asDDResource("block/beam/block" + axis));
                ModelFile.ExistingModelFile modelTop = p.models().getExistingFile(asDDResource("block/beam/top" + axis));
                ModelFile.ExistingModelFile modelBottom = p.models().getExistingFile(asDDResource("block/beam/bottom" + axis));
                var item = p.models().withExistingParent(c.getName() + "_item", asDDResource("block/beam/block_x"));
                ModelFile.ExistingModelFile modelFile;
                switch (beam) {
                    case TOP -> modelFile = modelTop;
                    case BOTTOM -> modelFile = modelBottom;
                    case BOTH -> modelFile = modelBoth;
                    default -> modelFile = modelBoth;
                }

                ModelFile.ExistingModelFile model = modelFile;
                return ConfiguredModel.builder().modelFile(model).build();
            }))
            .item().model((c, p) -> p.blockItem(c::get, "_item")).build()
            .loot(RegistrateBlockLootTables::dropSelf)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(LARGE_METAL_GIRDER.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get(), INDUSTRIAL_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .register();

    // Metal Support
    public static final BlockEntry<MetalSupportBlock> METAL_SUPPORT = DND_REGISTRATE
            .block("metal_support", MetalSupportBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .blockstate((c, p) -> {
                var bottomPole = p.models().getExistingFile(asResource("block/metal_support/bottom_pole"));
                var middlePole = p.models().getExistingFile(asResource("block/metal_support/middle_pole"));
                var topPole = p.models().getExistingFile(asResource("block/metal_support/top_pole"));
                var topBottomSingle = p.models().getExistingFile(asResource("block/metal_support/top_bottom"));
                var itemModel = p.models().withExistingParent("block/metal_support/item", asDDResource("block/metal_support/item"));

                p.getVariantBuilder(c.getEntry()).forAllStates(state -> {
                    Direction.Axis axis = state.getValue(MetalSupportBlock.HORIZONTAL_AXIS);
                    MetalSupport blockType = state.getValue(MetalSupportBlock.BLOCK_TYPE);
                    ModelFile model;

                    int yRot = 0;
                    if (axis == Direction.Axis.X) yRot = 90;

                    switch (blockType) {
                        case TOP_BOTTOM -> model = topBottomSingle;
                        case BOTTOM -> model = bottomPole;
                        case MIDDLE -> model = middlePole;
                        case TOP -> model = topPole;
                        default -> throw new IllegalArgumentException("Unexpected or invalid blockType:" + blockType);
                    }

                    return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
                });
            })
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(itemTag("dark_metal_decor")), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item().model((c, p) -> p.blockItem(c::get, "/item")).build()
            .register();

    // Diagonal Metal Support - DPM
    public static final BlockEntry<DiagonalMetalSupportBlock> DIAGONAL_METAL_SUPPORT = DND_REGISTRATE
            .block("diagonal_metal_support", DiagonalMetalSupportBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .blockstate((c, p) -> {
                var block = p.models().withExistingParent("block/diagonal_metal_support/" + c.getName(), asDDResource("block/diagonal_metal_support/block"));
                var item = p.models().withExistingParent("block/diagonal_metal_support/" + c.getName() + "_item", asDDResource("block/diagonal_metal_support/item"));

                p.getVariantBuilder(c.getEntry()).forAllStates(state -> {
                    Direction dir = state.getValue(DiagonalMetalSupportBlock.HORIZONTAL_FACING);
                    int yRot;
                    switch (dir) {
                        case EAST -> yRot = 90;
                        case SOUTH -> yRot = 180;
                        case WEST -> yRot = 270;
                        default -> yRot = 0;
                    }

                    return ConfiguredModel.builder().modelFile(block).rotationY(yRot).build();
                });
            })
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(itemTag("dark_metal_decor")), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item().model((c, p) -> p.blockItem(c::get, "/diagonal_metal_support_item")).build()
            .register();

    // Diagonal Girder Block - DPM
    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER_BLOCK = DND_REGISTRATE
            .block("diagonal_girder", DiagonalGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops())
            .blockstate((c, p) -> {
                var girder = p.models().withExistingParent(c.getName(), asDDResource("block/diagonal_girder/block"));
                var girderUp = p.models().withExistingParent(c.getName() + "_up", asDDResource("block/diagonal_girder/block_up"));

                p.getVariantBuilder(c.get()).forAllStates(state -> {
                    Direction dir = state.getValue(DiagonalGirderBlock.FACING);
                    boolean facingUp = state.getValue(DiagonalGirderBlock.FACING_UP);

                    ModelFile model = facingUp ? girderUp : girder;
                    int rotY = (int) dir.toYRot() + 90;

                    return ConfiguredModel.builder().modelFile(model).rotationY(rotY).build();

                });
            })
            .item().model((c, p) -> p.blockItem(c::get)).build()
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                    .pattern(" # ")
                    .pattern("#  ")
                    .define('#', AllBlocks.METAL_GIRDER.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.METAL_GIRDER.asItem()))
                    .save(p::accept))
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .register();

    // Ornate Grate - DPM
    public static final BlockEntry<OrnateGrateBlock> ORNATE_GRATE = DND_REGISTRATE
            .block("ornate_grate", OrnateGrateBlock::new)
            .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniConn(ORIGINAL_MOD_ID, "ornate_grate"), omniConn(ORIGINAL_MOD_ID, "ornate_grate"))))
            .addLayer(() -> RenderType::cutout)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.WOOD).requiresCorrectToolForDrops())
            .loot(RegistrateBlockLootTables::dropSelf)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate((c, p) -> {
                p.models().withExistingParent(c.getName(), asDDResource("block/ornate_grate"));
                p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c ,p));
            })
            .simpleItem()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("#I#")
                    .pattern("I I")
                    .pattern("#I#")
                    .define('#', Tags.Items.INGOTS_IRON)
                    .define('I', Tags.Items.RODS_WOODEN)
                    .unlockedBy("has_ingredient", has(AllPaletteBlocks.ORNATE_IRON_WINDOW.asItem()))
                    .save(p))
            .register();

    // Zinc Bricks
    public static final BlockEntry<Block> ZINC_BRICKS = simpleBlock("zinc_bricks", MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.ZINC_BLOCK.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.ZINC_BLOCK.asItem()))
                    .save(p::accept))
            .register();

    // Zinc Checker Tiles
    public static final BlockEntry<CasingBlock> ZINC_CHECKER_TILES = simpleCasing("zinc_checker_tiles", MapColor.COLOR_LIGHT_GRAY, SoundType.METAL, "zinc_checker_tiles")
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("###")
                    .pattern("#z#")
                    .pattern("###")
                    .define('z', AllBlocks.ZINC_BLOCK.asItem())
                    .define('#', AllItems.ZINC_INGOT)
                    .unlockedBy("has_ingredient", has(AllBlocks.ZINC_BLOCK.asItem()))
                    .save(p::accept))
            .register();

    // Stone Metal
    public static final BlockEntry<CasingBlock> STONE_METAL = simpleCasing("stone_metal", MapColor.TERRACOTTA_GRAY, DnDSoundTypes.METAL_HEAVY, "stone_metal")
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("AP")
                    .pattern("PA")
                    .define('P', AllTags.AllItemTags.PLATES.tag)
                    .define('A', AllPaletteStoneTypes.ASURINE.baseBlock.get())
                    .unlockedBy("has_ingredient", has(AllPaletteStoneTypes.ASURINE.baseBlock.get()))
                    .save(p::accept))
            .register();

    // Colored Stone Metals
    public static final DyedBlockList<Block> DYED_STONE_METALS = new DyedBlockList<>(color -> {
        var baseID = "stone_metal";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        var ct = omniConn(ORIGINAL_MOD_ID, "stone_metal/" + colorID);
        return DND_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(DnDSoundTypes.METAL_HEAVY).strength(1.5f,2f))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ct)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct)))
                .transform(pickaxeOnly())
                .transform(blockBMI(ORIGINAL_MOD_ID, "block/stone_metal/" + colorID))
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
                .register();
    });

    // Colored Velvet Blocks - DPM
    public static final DyedBlockList<Block> DYED_VELVET_BLOCKS = new DyedBlockList<>(color -> {
        var baseID = "velvet_block";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        return DND_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(SoundType.WOOL).strength(0.5f,0.5f))
                .blockstate((c, p) -> {
                    p.models().withExistingParent(c.getName(), asDDResource("block/" + colorID + "_velvet_block"));
                    p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c, p));
                    // Could work with blockBMI but doesn't find create white seat top texture
                })
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
                .register();
    });

    // Dark Metal Block
    public static final BlockEntry<Block> DARK_METAL_BLOCK = simpleBlock("dark_metal_block", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(blockBMI(ORIGINAL_MOD_ID, null, itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .define('#', AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .register();

    // Dark Metal Plating
    public static final BlockEntry<CasingBlock> DARK_METAL_PLATING = simpleCasing("dark_metal_plating", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK, "dark_metal_plating")
            .transform(blockBMI(ORIGINAL_MOD_ID, null, itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .register();

    // Dark Metal Block Slab
    public static final BlockEntry<SlabBlock> DARK_METAL_BLOCK_SLAB = simpleSlabBlock("dark_metal_block_slab", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(doubleSlabBMI(ORIGINAL_MOD_ID, "block/dark_metal_block", "block/dark_metal_block_slab", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
            })
            .register();

    // Dark Metal Block Stairs
    public static final BlockEntry<StairBlock> DARK_METAL_BLOCK_STAIRS = simpleStairBlock("dark_metal_block", DnDBlocks.DARK_METAL_BLOCK, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(stairBMI(ORIGINAL_MOD_ID, "block/dark_metal_block_stairs", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .register();

    // Dark Metal Block Wall
    public static final BlockEntry<WallBlock> DARK_METAL_BLOCK_WALL = simpleWallBlock("dark_metal_block", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(wallBM(ORIGINAL_MOD_ID, "block/dark_metal_block"))
            .transform(wallI(ORIGINAL_MOD_ID, "block/dark_metal_block", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .lang("Dark Metal Block Wall")
            .register();

    // Dark Metal Bricks
    public static final BlockEntry<Block> DARK_METAL_BRICKS = simpleBlock("dark_metal_bricks", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(blockBMI(ORIGINAL_MOD_ID, null, itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .register();

    // Dark Metal Brick Slab
    public static final BlockEntry<SlabBlock> DARK_METAL_BRICK_SLAB = simpleSlabBlock("dark_metal_brick",MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(slabBMI(ORIGINAL_MOD_ID, "block/dark_metal_bricks", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
            })
            .register();

    // Dark Metal Brick Stairs
    public static final BlockEntry<StairBlock> DARK_METAL_BRICK_STAIRS = simpleStairBlock("dark_metal_brick", DnDBlocks.DARK_METAL_BRICKS, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(stairBMI(ORIGINAL_MOD_ID, "block/dark_metal_brick_stairs", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .register();

    // Dark Metal Brick Wall
    public static final BlockEntry<WallBlock> DARK_METAL_BRICK_WALL = simpleWallBlock("dark_metal_brick", MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(wallBM(ORIGINAL_MOD_ID, "block/dark_metal_bricks"))
            .transform(wallI(ORIGINAL_MOD_ID, "block/dark_metal_bricks", itemTag("dark_metal_decor")))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', DnDBlocks.DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .register();

    // Brass Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> BRASS_FRONTLIGHT = DND_REGISTRATE
            .block("brass_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())
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
            .register();

    // Andesite Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> ANDESITE_FRONTLIGHT = DND_REGISTRATE
            .block("andesite_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())
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
            .register();

    // Zinc Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> ZINC_FRONTLIGHT = DND_REGISTRATE
            .block("zinc_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Copper Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> COPPER_FRONTLIGHT = DND_REGISTRATE
            .block("copper_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Industrial Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> INDUSTRIAL_FRONTLIGHT = DND_REGISTRATE
            .block("industrial_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Gold Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> GOLD_FRONTLIGHT = DND_REGISTRATE
            .block("gold_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Iron Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> IRON_FRONTLIGHT = DND_REGISTRATE
            .block("iron_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Netherite Frontlight - DPM
    public static final BlockEntry<FrontlightBlock> NETHERITE_FRONTLIGHT = DND_REGISTRATE
            .block("netherite_frontlight", FrontlightBlock::new)
            .addLayer(() -> RenderType::cutoutMipped)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(frontlightBlockDDBlockStateModelAndItem())            .loot((t, g) -> t.dropSelf(g))
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
            .register();

    // Brass Floor
    public static final BlockEntry<Block> BRASS_FLOOR = simpleBlock("brass_floor",MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/brass"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Andesite Floor
    public static final BlockEntry<Block> ANDESITE_FLOOR = simpleBlock("andesite_floor",MapColor.COLOR_CYAN, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.items(AllItems.ANDESITE_ALLOY.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Zinc Floor
    public static final BlockEntry<Block> ZINC_FLOOR = simpleBlock("zinc_floor",MapColor.COLOR_LIGHT_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asNamespaceResource("forge", "ingots/zinc"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Copper Floor
    public static final BlockEntry<Block> COPPER_FLOOR = simpleBlock("copper_floor", MapColor.TERRACOTTA_ORANGE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_COPPER), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Industrial Floor
    public static final BlockEntry<Block> INDUSTRIAL_FLOOR = simpleBlock("industrial_floor", MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Gold Floor
    public static  final  BlockEntry<Block> GOLD_FLOOR = simpleBlock("gold_floor",MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_GOLD), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Iron Floor
    public static final BlockEntry<Block> IRON_FLOOR = simpleBlock("iron_floor",MapColor.TERRACOTTA_WHITE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c ,2))
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Netherite Floor
    public static final BlockEntry<Block> NETHERITE_FLOOR = simpleBlock("netherite_floor",MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(Items.NETHERITE_INGOT), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .transform(blockBMI(ORIGINAL_MOD_ID, null))
            .register();

    // Raw Stones
    public static final BlockEntry<Block> WEATHERED_LIMESTONE = simpleDDStoneBlock("weathered_limestone", toStonesDir("weathered_limestone"), MapColor.COLOR_GRAY, SoundType.STONE, "weathered_limestone").register();
    public static final BlockEntry<Block> GABBRO = simpleDDStoneBlock("gabbro", toStonesDir("gabbro"), MapColor.COLOR_GRAY, SoundType.STONE, "gabbro").register();
    public static final BlockEntry<Block> DOLOMITE = simpleDDStoneBlock("dolomite", toStonesDir("dolomite"), MapColor.COLOR_GRAY, SoundType.STONE, "dolomite").register();

    static {
        // Stone-sets types registration
        registerStoneBlockSet("stone", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);
        registerStoneBlockSet("packed_mud", MapColor.COLOR_LIGHT_GRAY, SoundType.MUD_BRICKS);
        registerStoneBlockSet("amethyst", MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST);
        registerStoneBlockSet("netherrack", MapColor.TERRACOTTA_RED, SoundType.NETHERRACK);
        registerStoneBlockSet("basalt", MapColor.TERRACOTTA_GRAY, SoundType.BASALT);
        registerStoneBlockSet("blackstone", MapColor.TERRACOTTA_BLACK, SoundType.STONE);
        registerStoneBlockSet("weathered_limestone", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);
        registerStoneBlockSet("gabbro", MapColor.TERRACOTTA_ORANGE, SoundType.STONE);
        registerStoneBlockSet("dolomite", MapColor.COLOR_LIGHT_GRAY, SoundType.STONE);

        registerNumberAndLetterSignSet();
    }

    // Even more signs - DPM
    public static final BlockEntry<RotableSquareSignBlock> BLANK_SYMBOL_SIGN = rotableSquareSignBlock("blank_symbol", asDDResource("block/old/letter_signs/blank"), null, "brass")
            .blockstate((c, p) -> {
                String fileTextureName = c.getName().substring(0, c.getName().length() - 12);

                var model0 = p.models().withExistingParent("block/signs/symbol_signs/" + c.getName() + "_0", asResource("block/base/signs/sign_model_0"))
                        .texture("0", asDDResource("block/old/letter_signs/" + fileTextureName));
                var model90 = p.models().withExistingParent("block/signs/symbol_signs/" + c.getName() + "_90", asResource("block/base/signs/sign_model_90"))
                        .texture("0", asDDResource("block/old/letter_signs/" + fileTextureName));
                var model180 = p.models().withExistingParent("block/signs/symbol_signs/" + c.getName() + "_180", asResource("block/base/signs/sign_model_180"))
                        .texture("0", asDDResource("block/old/letter_signs/" + fileTextureName));
                var model270 = p.models().withExistingParent("block/signs/symbol_signs/" + c.getName() + "_270", asResource("block/base/signs/sign_model_270"))
                        .texture("0", asDDResource("block/old/letter_signs/" + fileTextureName));

                p.getVariantBuilder(c.get()).forAllStates(state -> {
                    Direction dir = state.getValue(SquareSignBlock.FACING);
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
            })
            .register();

    // Rotable cause, why not?
    public static final BlockEntry<RotableSquareSignBlock> UP_SIGN = rotableSquareSignBlock("up", asDDResource(signPathPlusName("up")), null, null).transform(rotableSquareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<RotableSquareSignBlock> DOWN_SIGN = rotableSquareSignBlock("down", asDDResource(signPathPlusName("down")), null, null).transform(rotableSquareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<RotableSquareSignBlock> LEFT_SIGN = rotableSquareSignBlock("left", asDDResource(signPathPlusName("left")), null, null).transform(rotableSquareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<RotableSquareSignBlock> RIGHT_SIGN = rotableSquareSignBlock("right", asDDResource(signPathPlusName("right")), null, null).transform(rotableSquareSignBM(ORIGINAL_MOD_ID, signPath())).register();

    public static final BlockEntry<SquareSignBlock> WARNING_SIGN = simpleSquareSignBlock("warning", asDDResource(signPathPlusName("warning")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> STOP_SIGN = simpleSquareSignBlock("stop", asDDResource(signPathPlusName("stop")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> BIOHAZARD_SIGN = simpleSquareSignBlock("biohazard", asDDResource(signPathPlusName("biohazard")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> OIL_SIGN = simpleSquareSignBlock("american", asDDResource(signPathPlusName("american")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).lang("'Did somebody say freedom??!' sign").register();
    public static final BlockEntry<SquareSignBlock> BACK_SIGN = simpleSquareSignBlock("back", asDDResource(signPathPlusName("back")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> BLANK_SIGN = simpleSquareSignBlock("blank", asDDResource(signPathPlusName("blank")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> BROKEN_WRENCH_SIGN = simpleSquareSignBlock("broken_wrench", asDDResource(signPathPlusName("broken_wrench")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> DOLLAR_SIGN = simpleSquareSignBlock("capitalism_warning", asDDResource(signPathPlusName("capitalism_warning")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).lang("Dollar sign").register();
    public static final BlockEntry<SquareSignBlock> GEAR_SIGN = simpleSquareSignBlock("gear", asDDResource(signPathPlusName("gear")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> GLITCH_WARNING_SIGN = simpleSquareSignBlock("glitch_warning", asDDResource(signPathPlusName("glitch_warning")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> MAGNET_SIGN = simpleSquareSignBlock("magnet", asDDResource(signPathPlusName("magnet")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> MOYAI_SIGN = simpleSquareSignBlock("moyai", asDDResource(signPathPlusName("moyai")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> SILLY_SIGN = simpleSquareSignBlock("silly", asDDResource(signPathPlusName("silly")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();
    public static final BlockEntry<SquareSignBlock> TAP_SIGN = simpleSquareSignBlock("tap", asDDResource(signPathPlusName("tap")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).lang("Tap water sign").register();
    public static final BlockEntry<SquareSignBlock> BUNNY_SIGN = simpleSquareSignBlock("bun", asDDResource(signPathPlusName("bun")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).lang("Bunny sign").register();
    public static final BlockEntry<SquareSignBlock> CREEPER_SIGN = simpleSquareSignBlock("creeper", asDDResource(signPathPlusName("creeper")), null ,null).transform(squareSignBM(ORIGINAL_MOD_ID, signPath())).register();



    public static void register() {}
}