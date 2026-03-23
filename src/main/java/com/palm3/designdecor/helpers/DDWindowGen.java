package com.palm3.designdecor.helpers;

import com.palm3.designdecor.DDMain;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.GlassPaneBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.GlassPaneCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.palm3.designdecor.DDMain.DD_REGISTRATE;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

@SuppressWarnings({"deprecated", "removal"})
public class DDWindowGen {

    //---------------------- WITH blockstate and item model ----------------------------

    // Dear developer that wants to read/modify this code, please know that i know less than you on what this thing does. I never copy-pasted more in my life than when i was doing this.
    // I don't understand almost anything what's happening here, i started modding 3 months ago without knowing java, so chill out. Plus, i don't understand the connected texture thing.
    // Thanks to the Create team to make their code open source anyway, i love you guys, best mod ever.

    // Like, what does this do? I understand it returns this whole thing so you can put .register() at the end and you have your block, but how does it work?
    private static <G extends GlassPaneBlock> BlockBuilder<G, CreateRegistrate> glassPane(String name,
                                                                                          Supplier<? extends Block> parent, ResourceLocation sideTexture, ResourceLocation topTexture,
                                                                                          NonNullFunction<BlockBehaviour.Properties, G> factory, Supplier<Supplier<RenderType>> renderType,
                                                                                          NonNullConsumer<? super G> connectedTextures,
                                                                                          NonNullBiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider, boolean colorless) {
        name += "_pane";


        ItemBuilder<BlockItem, BlockBuilder<G, CreateRegistrate>> itemBuilder = DD_REGISTRATE.block(name, factory)
                .onRegister(connectedTextures)
                .addLayer(renderType)
                .initialProperties(() -> Blocks.GLASS_PANE)
                .properties(p -> p.mapColor(parent.get()
                        .defaultMapColor()))
                .transform(DDBlockBuildingHelpers.noBlockState())
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 16)
                            .pattern("###")
                            .pattern("###")
                            .define('#', parent.get())
                            .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(parent.get()))
                            .save(p::accept);
                    if (colorless)
                        p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_PANES_COLORLESS), RecipeCategory.BUILDING_BLOCKS,
                                c::get);
                })
                .loot((t, g) -> t.dropWhenSilkTouch(g))
                .item();

        if (colorless)
            itemBuilder.tag(Tags.Items.GLASS_PANES, Tags.Items.GLASS_PANES_COLORLESS);
        else
            itemBuilder.tag(Tags.Items.GLASS_PANES);

        BlockBuilder<G, CreateRegistrate> blockBuilder = itemBuilder
                .model((c, p) -> p.generated(c, sideTexture))
                .build();

        if (colorless)
            blockBuilder.tag(Tags.Blocks.GLASS_PANES, Tags.Blocks.GLASS_PANES_COLORLESS);
        else
            blockBuilder.tag(Tags.Blocks.GLASS_PANES);

        return blockBuilder;
    }

    private static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> connectedGlassPane(String name,
                                                                                              Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift, ResourceLocation sideTexture,
                                                                                              ResourceLocation itemSideTexture, ResourceLocation topTexture, Supplier<Supplier<RenderType>> renderType, boolean colorless) {
        NonNullConsumer<? super ConnectedGlassPaneBlock> connectedTextures = ctshift == null ? $ -> {
        } : connectedTextures(() -> new GlassPaneCTBehaviour(ctshift.get()));
        String CGPparents = "block/connected_glass_pane/";
        String prefix = name + "_pane_";

        Function<RegistrateBlockstateProvider, ModelFile> post =
                getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
                side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
                sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
                noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
                noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

        NonNullBiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
                (c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p),
                        noSideAlt.apply(p));

        return glassPane(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new, renderType,
                connectedTextures, stateProvider, colorless);
    }

    private static Function<RegistrateBlockstateProvider, ModelFile> getPaneModelProvider(String CGPparents,
                                                                                          String prefix, String partial, ResourceLocation sideTexture, ResourceLocation topTexture) {
        return p -> p.models()
                .withExistingParent(prefix + partial, DDMain.asResource(CGPparents + partial))
                .texture("pane", sideTexture)
                .texture("edge", topTexture);
    }

    /*public static BlockBuilder<WindowBlock, CreateRegistrate> windowBlock(String name,
                                                                          Supplier<? extends ItemLike> ingredient, Supplier<CTSpriteShiftEntry> ct,
                                                                          Supplier<Supplier<RenderType>> renderType, boolean translucent,
                                                                          NonNullFunction<String, ResourceLocation> endTexture, NonNullFunction<String, ResourceLocation> sideTexture,
                                                                          Supplier<MapColor> color) {
        return DDB_REGISTRATE.block(name, p -> new WindowBlock(p, translucent))
                .onRegister(ct == null ? $ -> {
                } : connectedTextures(() -> new HorizontalCTBehaviour(ct.get())))
                .addLayer(renderType)
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                        .pattern(" # ")
                        .pattern("#X#")
                        .define('#', ingredient.get())
                        .define('X', DataIngredient.tag(Tags.Items.GLASS_COLORLESS))
                        .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(ingredient.get()))
                        .save(p::accept))
                .initialProperties(() -> Blocks.GLASS)
                .properties(p -> p
                        .isValidSpawn((state, getter, pos, entityType) -> false)
                        .isRedstoneConductor((state, getter, pos) -> false)
                        .isSuffocating((state, getter, pos) -> false)
                        .isViewBlocking((state, getter, pos) -> false))
                .properties(p -> p.mapColor(color.get()))
                .loot((t, g) -> t.dropWhenSilkTouch(g))
                .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                        .cubeColumn(c.getName(), sideTexture.apply(c.getName()), endTexture.apply(c.getName()))))
                .tag(BlockTags.IMPERMEABLE)
                .simpleItem();
    }*/

    public static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> customWindowPane(String name,
                                                                                           Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift,
                                                                                           Supplier<Supplier<RenderType>> renderType) {
        ResourceLocation topTexture = DDMain.asResource("block/palettes" + name + "_pane_top");
        ResourceLocation sideTexture = DDMain.asResource("block/palettes" + name);
        return connectedGlassPane(name, parent, ctshift, sideTexture, sideTexture, topTexture, renderType, false);
    }

    /*public static BlockEntry<WindowBlock> customWindowBlock(String name, Supplier<? extends ItemLike> ingredient,
                                                            Supplier<CTSpriteShiftEntry> ct, Supplier<Supplier<RenderType>> renderType, boolean translucent,
                                                            Supplier<MapColor> color) {
        NonNullFunction<String, ResourceLocation> end_texture = n -> DDBMain.location("block/palettes" + name + "_end");
        NonNullFunction<String, ResourceLocation> side_texture = n -> DDBMain.location("block/palettes" + n);
        return windowBlock(name, ingredient, ct, renderType, translucent, end_texture, side_texture, color).register();
    }*/



    //-------------------- WITHOUT blockstate/item model -------------------------

    private static <G extends GlassPaneBlock> BlockBuilder<G, CreateRegistrate> glassPaneWithoutBlockStateAndItemModel(String name,
                                                                                          Supplier<? extends Block> parent, ResourceLocation sideTexture, ResourceLocation topTexture,
                                                                                          NonNullFunction<BlockBehaviour.Properties, G> factory, Supplier<Supplier<RenderType>> renderType,
                                                                                          NonNullConsumer<? super G> connectedTextures,
                                                                                          NonNullBiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider, boolean colorless) {
        name += "_pane";


        ItemBuilder<BlockItem, BlockBuilder<G, CreateRegistrate>> itemBuilder = DD_REGISTRATE.block(name, factory)
                .onRegister(connectedTextures)
                .addLayer(renderType)
                .initialProperties(() -> Blocks.GLASS_PANE)
                .properties(p -> p.mapColor(parent.get()
                        .defaultMapColor()))
                .transform(DDBlockBuildingHelpers.noBlockState())
                .recipe((c, p) -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 16)
                            .pattern("###")
                            .pattern("###")
                            .define('#', parent.get())
                            .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(parent.get()))
                            .save(p::accept);
                    if (colorless)
                        p.stonecutting(DataIngredient.tag(Tags.Items.GLASS_PANES_COLORLESS), RecipeCategory.BUILDING_BLOCKS,
                                c::get);
                })
                .loot((t, g) -> t.dropWhenSilkTouch(g)).item();

        if (colorless)
            itemBuilder.tag(Tags.Items.GLASS_PANES, Tags.Items.GLASS_PANES_COLORLESS);
        else
            itemBuilder.tag(Tags.Items.GLASS_PANES);

        BlockBuilder<G, CreateRegistrate> blockBuilder = itemBuilder
                .model((c, p) -> p.generated(c, sideTexture))
                .build();

        if (colorless)
            //blockBuilder.tag(Tags.Blocks.GLASS_PANES, Tags.Blocks.GLASS_PANES_COLORLESS);
            return blockBuilder.transform(DDBlockBuildingHelpers.itemWithoutModel(Tags.Items.GLASS_PANES, Tags.Items.GLASS_PANES_COLORLESS));
        else
            return blockBuilder.transform(DDBlockBuildingHelpers.itemWithoutModel(Tags.Items.GLASS_PANES));
            //blockBuilder.tag(Tags.Blocks.GLASS_PANES);

        //return blockBuilder.transform(BlockBuildingHelpers.noBlockState()).transform(BlockBuildingHelpers.itemWithoutModel());
    }

    private static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> connectedGlassPaneWithoutBlockStateAndItemModel(String name,
                                                                                              Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift, ResourceLocation sideTexture,
                                                                                              ResourceLocation itemSideTexture, ResourceLocation topTexture, Supplier<Supplier<RenderType>> renderType, boolean colorless) {
        NonNullConsumer<? super ConnectedGlassPaneBlock> connectedTextures = ctshift == null ? $ -> {
        } : connectedTextures(() -> new GlassPaneCTBehaviour(ctshift.get()));
        String CGPparents = "block/connected_glass_pane/";
        String prefix = name + "_pane_";

        Function<RegistrateBlockstateProvider, ModelFile> post =
                getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
                side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
                sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
                noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
                noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

        NonNullBiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
                (c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p),
                        noSideAlt.apply(p));

        return glassPaneWithoutBlockStateAndItemModel(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new, renderType,
                connectedTextures, stateProvider, colorless);
    }



    public static BlockBuilder<ConnectedGlassPaneBlock, CreateRegistrate> customWindowPaneWithoutBlockStateAndItemModel(String name,
                                                                                           Supplier<? extends Block> parent, Supplier<CTSpriteShiftEntry> ctshift,
                                                                                           Supplier<Supplier<RenderType>> renderType) {
        ResourceLocation topTexture = DDMain.asResource("block/palettes" + name + "_pane_top");
        ResourceLocation sideTexture = DDMain.asResource("block/palettes" + name);
        return connectedGlassPaneWithoutBlockStateAndItemModel(name, parent, ctshift, sideTexture, sideTexture, topTexture, renderType, false).transform(DDBlockBuildingHelpers.noBlockState());
    }

}
// I'll leave you with this: "Things works according to how one manages to make them work". Have a good day.