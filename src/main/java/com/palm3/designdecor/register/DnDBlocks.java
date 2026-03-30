package com.palm3.designdecor.register;

import com.palm3.designdecor.blocks.diagonal_girder.DiagonalGirderBlock;
import com.palm3.designdecor.blocks.beam.BeamBlock;
import com.palm3.designdecor.blocks.beam.BeamCTBehaviour;
import com.palm3.designdecor.blocks.frontlight.FrontlightBlock;
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
import org.stringtemplate.v4.misc.AmbiguousMatchException;

import static com.palm3.designdecor.DDMain.*;
import static com.palm3.designdecor.helpers.DesignDecorBlockBuildingHelpers.*;
import static com.palm3.designdecor.helpers.DesignDecorBlockBuildingHelpers.DataGenTransformers.*;
import static com.palm3.designdecor.helpers.DnDRegistrationHelpers.*;
import static com.palm3.designdecor.helpers.DnDecorCTSpriteShiftsHelpers.*;
import static com.palm3.designdecor.helpers.RegistrateHelpers.BlockBuilders.*;
import static com.palm3.designdecor.helpers.RegistrateHelpers.Transformers.DatagenDisablers.itemWithoutModel;
import static com.palm3.designdecor.helpers.RegistrateHelpers.Transformers.DatagenDisablers.noBlockState;
import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

@SuppressWarnings({"deprecated", "removal"})
public class DnDBlocks {
    //============================== Normal blocks ===============================

    // Deepslate Tiles
    public static final BlockEntry<CasingBlock> DEEPSLATE_TILES = simpleCasing("deepslate_tiles", false, MapColor.TERRACOTTA_GRAY, SoundType.DEEPSLATE, "deepslate_tiles")
            .transform(simpleBlockDDBlockStateModelAndItem())
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .register();

    // Red Deepslate Tiles
    public static final BlockEntry<CasingBlock> RED_DEEPSLATE_TILES = simpleCasing("red_deepslate_tiles", false, MapColor.TERRACOTTA_GRAY, SoundType.DEEPSLATE, "red_deepslate_tiles")
            .transform(simpleBlockDDBlockStateModelAndItem())
            .recipe((c, p) -> p.stonecutting(
                    DataIngredient.tag(Tags.Items.COBBLESTONE_DEEPSLATE),
                    RecipeCategory.BUILDING_BLOCKS,
                    c::get))
            .register();

    // Ornate Iron Glass
    public static final BlockEntry<ConnectedGlassBlock> ORNATE_IRON_GLASS = simpleConnectedGlass("ornate_iron_glass", "ornate_iron_glass", "ornate_iron_glass_end")
            .transform(simpleBlockDDBlockStateModelAndItem())
            .recipe((c, p) -> p.stonecutting(DataIngredient.tag(Tags.Items.GLASS),
                    RecipeCategory.BUILDING_BLOCKS, c::get))
            .register();

    // Ornate Iron Glass Pane
    public static final BlockEntry<ConnectedGlassPaneBlock> ORNATE_IRON_GLASS_PANE = DND_REGISTRATE
            .block("ornate_iron_glass_pane", ConnectedGlassPaneBlock::new)
            .addLayer(() -> RenderType::cutout)
            .onRegister(connectedTextures(() -> new GlassPaneCTBehaviour(omniDDLocationConnected("palettes", "ornate_iron_glass"))))
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
            .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniDDConnected("industrial_plating_block_side"), omniDDConnected("industrial_plating_block"))))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
            .transform(simpleBlockDDBlockStateModelAndItem())
            .loot((t, g) -> t.dropSelf(g))
            .recipe((c, p) -> {
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .register();

    // Large Metal Girder
    public static final BlockEntry<ConnectedPillarBlock> LARGE_METAL_GIRDER = simpleConnectedPillar("large_metal_girder", false, MapColor.COLOR_GRAY, SoundType.NETHERITE_BLOCK, "large_girder", "large_girder_top")
            .transform(simpleBlockDDBlockStateModelAndItem())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.METAL_GIRDER.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.METAL_GIRDER.asItem()))
                    .save(p))
            .register();

    // Beam Block
    public static final BlockEntry<BeamBlock> BEAM = DND_REGISTRATE
            .block("beam", BeamBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops())
            .onRegister(connectedTextures(() -> new BeamCTBehaviour(
                    horizKryppersDDLocationConnected("beam", "beam"),
                    verticalDDLocationConnected("beam", "beam_top_z"),
                    horizKryppersDDLocationConnected("beam", "beam_top_x"))))
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
                    default -> throw new AmbiguousMatchException(null, null);
                }

                ModelFile.ExistingModelFile model = modelFile;
                return ConfiguredModel.builder().modelFile(model).build();
            }))
            .item().model((c, p) -> {
                p.blockItem(c::get, "_item");
            }).build()
            .loot(RegistrateBlockLootTables::dropSelf)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(LARGE_METAL_GIRDER.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get(), INDUSTRIAL_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .register();

    // Diagonal Girder Block
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

    // Ornate Grate
    public static final BlockEntry<CasingBlock> ORNATE_GRATE = simpleCasing("ornate_grate", false, MapColor.COLOR_GRAY, SoundType.WOOD, "ornate_grate")
            .transform(simpleBlockDDBlockStateModelAndItem())
            .addLayer(() -> RenderType::cutout)
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
    public static final BlockEntry<Block> ZINC_BRICKS = simpleBlock("zinc_bricks", false, MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .transform(simpleBlockDDBlockStateModelAndItem())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', AllBlocks.ZINC_BLOCK.asItem())
                    .unlockedBy("has_ingredient", has(AllBlocks.ZINC_BLOCK.asItem()))
                    .save(p::accept))
            .register();

    // Zinc Checker Tiles
    public static final BlockEntry<CasingBlock> ZINC_CHECKER_TILES = simpleCasing("zinc_checker_tiles", false, MapColor.COLOR_LIGHT_GRAY, SoundType.METAL, "zinc_checker_tiles")
            .transform(simpleBlockDDBlockStateModelAndItem())
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
    public static final BlockEntry<CasingBlock> STONE_METAL = simpleCasing("stone_metal", false, MapColor.TERRACOTTA_GRAY, DnDSoundTypes.METAL_HEAVY, "stone_metal")
            .transform(simpleBlockDDBlockStateModelAndItem())
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
        var ct = omniDDLocationConnected("stone_metal", colorID);
        return DND_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(DnDSoundTypes.METAL_HEAVY).strength(1.5f,2f))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ct)))
                .onRegister(casingConnectivity((block, cc) -> cc.makeCasing(block, ct)))
                .transform(pickaxeOnly())
                .transform(simpleBlockDDBlockStateModelAndItem())
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

    // Colored Velvet Blocks
    public static final DyedBlockList<Block> DYED_VELVET_BLOCKS = new DyedBlockList<>(color -> {
        var baseID = "velvet_block";
        var colorID = color.getSerializedName();
        var blockID = colorID + "_" + baseID;
        return DND_REGISTRATE
                .block(blockID, Block::new)
                .properties(p -> p.mapColor(color.getMapColor()).sound(SoundType.WOOL).strength(0.5f,0.5f))
                .transform(simpleBlockDDBlockStateModelAndItem())
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
    public static final BlockEntry<Block> DARK_METAL_BLOCK = simpleBlock("dark_metal", true, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .define('#', AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, asResource("crafting/" + c.getName()));
            })
            .register();

    // Dark Metal Plating
    public static final BlockEntry<CasingBlock> DARK_METAL_PLATING = simpleCasing("dark_metal_plating", false, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK, "dark_metal_plating")
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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
    public static final BlockEntry<SlabBlock> DARK_METAL_BLOCK_SLAB = simpleSlabBlock("dark_metal_block", true, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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
    public static final BlockEntry<StairBlock> DARK_METAL_BLOCK_STAIRS = simpleStairBlock("dark_metal_block", true, DnDBlocks.DARK_METAL_BLOCK, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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

    // Dark Metal Bricks
    public static final BlockEntry<Block> DARK_METAL_BRICKS = simpleBlock("dark_metal_bricks", false, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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
    public static final BlockEntry<SlabBlock> DARK_METAL_BRICK_SLAB = simpleSlabBlock("dark_metal_brick",true , MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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
    public static final BlockEntry<StairBlock> DARK_METAL_BRICK_STAIRS = simpleStairBlock("dark_metal_brick", true, DnDBlocks.DARK_METAL_BRICKS, MapColor.TERRACOTTA_BLACK, SoundType.NETHERITE_BLOCK)
            .transform(simpleBlockDDBlockStateModelAndItem(TagKey.create(Registries.ITEM, asResource("dark_metal_decor"))))
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

    // Brass Frontlight
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

    // Andesite Frontlight
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

    // Zinc Frontlight
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

    // Copper Frontlight
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

    // Industrial Frontlight
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

    // Gold Frontlight
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

    // Iron Frontlight
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

    // Netherite Frontlight
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
    public static final BlockEntry<Block> BRASS_FLOOR = simpleBlock("brass_floor",false, MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "ingots/brass"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Andesite Floor
    public static final BlockEntry<Block> ANDESITE_FLOOR = simpleBlock("andesite_floor",false, MapColor.COLOR_CYAN, SoundType.METAL)
            .recipe((c, p) ->
                p.stonecutting(DataIngredient.items(AllItems.ANDESITE_ALLOY.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Zinc Floor
    public static final BlockEntry<Block> ZINC_FLOOR = simpleBlock("zinc_floor",false, MapColor.COLOR_LIGHT_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asExternalResource("forge", "ingots/zinc"))), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Copper Floor
    public static final BlockEntry<Block> COPPER_FLOOR = simpleBlock("copper_floor", false, MapColor.TERRACOTTA_ORANGE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_COPPER), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Industrial Floor
    public static final BlockEntry<Block> INDUSTRIAL_FLOOR = simpleBlock("industrial_floor",false, MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Gold Floor
    public static  final  BlockEntry<Block> GOLD_FLOOR = simpleBlock("gold_floor",false, MapColor.TERRACOTTA_YELLOW, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_GOLD), RecipeCategory.BUILDING_BLOCKS, c, 2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Iron Floor
    public static final BlockEntry<Block> IRON_FLOOR = simpleBlock("iron_floor",false, MapColor.TERRACOTTA_WHITE, SoundType.METAL)
            .recipe((c, p) ->
                    p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c ,2))
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();

    // Netherite Floor
    public static final BlockEntry<Block> NETHERITE_FLOOR = simpleBlock("netherite_floor",false, MapColor.TERRACOTTA_GRAY, SoundType.METAL)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(Items.NETHERITE_INGOT), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .transform(simpleBlockDDBlockStateModelAndItem())
            .register();


    // Raw Stones
    public static final BlockEntry<Block> WEATHERED_LIMESTONE = simpleDDStoneBlock("weathered_limestone", MapColor.COLOR_GRAY, SoundType.STONE, "weathered_limestone").transform(simpleBlockDDBlockStateModelAndItem()).register();
    public static final BlockEntry<Block> GABBRO = simpleDDStoneBlock("gabbro", MapColor.COLOR_GRAY, SoundType.STONE, "gabbro").transform(simpleBlockDDBlockStateModelAndItem()).register();
    public static final BlockEntry<Block> DOLOMITE = simpleDDStoneBlock("dolomite", MapColor.COLOR_GRAY, SoundType.STONE, "dolomite").transform(simpleBlockDDBlockStateModelAndItem()).register();

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
    }

    public static void register() {}
}
