/* Feel free to use this class (or methods) in your mod if you want! */

/* The block builders need the .register() method. */

/* This */

package com.palm3.designdecor.foundation.helpers.create_registrate;

import com.palm3.designdecor.content.blocks.sign_blocks.RotableSquareSignBlock;
import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.simibubi.create.foundation.block.connected.RotatedPillarCTBehaviour;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.palm3.designdecor.DnDMain.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.CTSpriteShiftsHelpers.*;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

@SuppressWarnings("removal")
public class RegistrateBlockBuildingHelpers {

    public static class ItemUtils {
        /// Creates a mod tag with the given name.
        /// @return A TagKey.create() with the given name.
        public static TagKey<Item> itemTag(String tagName) {
            return TagKey.create(Registries.ITEM, asResource(tagName));
        }

        /// Creates an external tag with the given name.
        /// @return A TagKey.create() with the given namespace and name.
        public static TagKey<Item> itemTag(String namespace, String tagName) {
            return TagKey.create(Registries.ITEM, asNamespaceResource(namespace, tagName));
        }
        
        /**
         * This method applies one of the two given tags.
         * <p>
         * If the custom tag is null, it applies the default.
         * @throws IllegalArgumentException If both tags are null. The default tag needs to be not null if the custom is null.
         * @return TagKey.create() with mod namespace.
         */
        // NOTE: if you want to use this for yourself, you obviously need an asResource() method!
        public static TagKey<Item> customTagOrDefault(String defaultTag, @Nullable String customTag) {
            if (defaultTag == null && customTag == null)
                throw new IllegalArgumentException("If the custom tag is null, the default tag cannot be null! This could also mean that the custom tag is required.");
            else if (defaultTag != null && customTag == null)
                return TagKey.create(Registries.ITEM, asResource(defaultTag));
            else if (defaultTag == null)
                return TagKey.create(Registries.ITEM, asResource(customTag));
            else  // All not null, priority to custom tag
                return TagKey.create(Registries.ITEM, asResource(customTag));
        }

        /**
         * This method applies one of the two given tags.
         * <p>
         * If the custom tag is null, it applies the default.
         * @throws IllegalArgumentException If both tags are null. The default tag needs to be not null if the custom is null.
         * @return TagKey.create() with given namespace.
         */
        // NOTE: if you want to use this for yourself, you obviously need an asNamespaceResource() method!
        public static TagKey<Item> customTagOrDefault(@NotNull String namespace, String defaultTag, @Nullable String customTag) {
            if (defaultTag == null && customTag == null)
                throw new IllegalArgumentException("If the custom tag is null, the default tag cannot be null! This could also mean that the custom tag is required.");
            else if (defaultTag != null && customTag == null)
                return TagKey.create(Registries.ITEM, asNamespaceResource(namespace, defaultTag));
            else if (defaultTag == null)
                return TagKey.create(Registries.ITEM, asNamespaceResource(namespace, customTag));
            else  // All not null, priority to custom tag
                return TagKey.create(Registries.ITEM, asNamespaceResource(namespace, customTag));
        }

        /**
         * This transformer adds tag(s) to your item.
         * <p>
         * If the custom tag is null, it applies the default. If both are null, it does nothing.
         * @return ItemBuilder with one of the two tags, or the same ItemBuilder.
         */
        @SafeVarargs
        public static <BI extends BlockItem, P> NonNullUnaryOperator<ItemBuilder<BI, P>> customTagOrDefault(@Nullable TagKey<Item> defaultTag, @Nullable TagKey<Item>... customTags) {
            if (defaultTag == null && customTags == null)
                return b -> b;
            else if (defaultTag != null && customTags == null)
                return b -> b.tag(defaultTag);
            else if (defaultTag == null)
                return b -> b.tag(customTags);
            else  // All not null, priority to custom tag
                return b -> b.tag(customTags);
        }
    }


    public static class BlockBuilders {
        /* ****************** CASING ****************** */
        /**
         * @param nameOrMaterial The name of the block.
         * @param mapColor The map color of the block.
         * @param soundType The sound type of the block.
         * @param texture The texture name of the block. Can also accept paths, like 'casings/my_casing' searches in 'textures/block/casings/my_casing.png' and 'my_casing_connected.png'
         * @return Basic BlockBuilder for a CasingBlock
         */
        public static BlockBuilder<CasingBlock, CreateRegistrate> simpleCasing(String nameOrMaterial, MapColor mapColor, SoundType soundType, String texture) {
            return getRegistrate()
                    .block(nameOrMaterial, CasingBlock::new)
                    .transform(BuilderTransformers.casing(() -> omniConn(ORIGINAL_MOD_ID, texture)))
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
        }


        /* ****************** GLASS ****************** */

        /// Returns the base BlockBuilder for a ConnectedGlassBlock with properties and connected textures.
        public static BlockBuilder<ConnectedGlassBlock, CreateRegistrate> simpleConnectedGlass(String name, String texturePath, String topTexturePath) {
            return getRegistrate()
                    .block(name, ConnectedGlassBlock::new)
                    .onRegister(connectedTextures(() -> new HorizontalCTBehaviour(omniConn(ORIGINAL_MOD_ID, texturePath), omniConn(ORIGINAL_MOD_ID, topTexturePath))))
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
//todo revise pillar and glass
        /**
         * @param nameOrMaterial The name of the block.
         * @param _pillarSuffix If true, _pillar will be automatically added, unless it's already at the end of the name.
         * @param mapColor The map color of the block.
         * @param soundType The sound type color of the block.
         * @param texture The name of the side texture, located in block/.
         * @param topTexture The name of the top texture, located in block/.
         * @return Basic BlockBuilder for a ConnectedPillarBlock
         */
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleConnectedPillar(String nameOrMaterial, boolean _pillarSuffix, MapColor mapColor, SoundType soundType, String texture, String topTexture) {
            if (_pillarSuffix && !nameOrMaterial.endsWith("_pillar")) nameOrMaterial += "_pillar";
            return getRegistrate()
                    .block(nameOrMaterial, ConnectedPillarBlock::new)
                    .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleConn(ORIGINAL_MOD_ID, texture), omniConn(ORIGINAL_MOD_ID, topTexture))))
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }

        /**
         * @param nameOrMaterial The name of the block.
         * @param _pillarSuffix If true, _pillar will be automatically added, unless it's already at the end of the name.
         * @param mapColor The map color of the block.
         * @param soundType The sound type color of the block.
         * @param texturePath The path where to find the required textures in 'block/.' directory.
         * @param texture The name of the side texture.
         * @param topTexture The name of the top texture
         * @return Basic BlockBuilder for a ConnectedPillarBlock
         */
        public static BlockBuilder<ConnectedPillarBlock, CreateRegistrate> simpleConnectedPillar(String nameOrMaterial, boolean _pillarSuffix, MapColor mapColor, SoundType soundType, String texturePath, String texture, String topTexture) {
            if (_pillarSuffix && !nameOrMaterial.endsWith("_pillar")) nameOrMaterial += "_pillar";
            return getRegistrate()
                    .block(nameOrMaterial, ConnectedPillarBlock::new)
                    .onRegister(connectedTextures(() -> new RotatedPillarCTBehaviour(rectangleConn(ORIGINAL_MOD_ID, texturePath + "/" + texture), omniConn(ORIGINAL_MOD_ID, texturePath + "/" + topTexture))))
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** BLOCK ****************** */
        /**
         * @param nameOrMaterial The name of the block.
         * @param mapColor Map color of the block.
         * @param soundType Sound of the block.
         * @return Basic BlockBuilder for a Block.
         */
        public static BlockBuilder<Block, CreateRegistrate> simpleBlock(String nameOrMaterial, MapColor mapColor, SoundType soundType) {
            return getRegistrate()
                    .block(nameOrMaterial, Block::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(mapColor).sound(soundType).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** SLAB ****************** */
        /**
         * @param nameOrMaterial The name of the slab. Notice that _slab will automatically be added unless already present.
         * @param mapColor Map color of the slab.
         * @param sound Sound of the slab.
         * @return Basic BlockBuilder for a SlabBlock.
         */
        public static BlockBuilder<SlabBlock, CreateRegistrate> simpleSlabBlock(String nameOrMaterial, MapColor mapColor, SoundType sound) {
            if (!nameOrMaterial.endsWith("_slab")) nameOrMaterial += "_slab";
            return getRegistrate()
                    .block(nameOrMaterial, SlabBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** STAIR ****************** */
        /**
         * @param nameOrMaterial The name of the stair. Notice that _stairs will automatically be added unless already present.
         * @param mapColor Map color of the stair.
         * @param sound Sound of the stair.
         * @return Basic BlockBuilder for a StairBlock.
         */
        public static BlockBuilder<StairBlock, CreateRegistrate> simpleStairBlock(String nameOrMaterial, Supplier<Block> parentBlock, MapColor mapColor, SoundType sound) {
            if (!nameOrMaterial.endsWith("_stairs")) nameOrMaterial += "_stairs";
            return getRegistrate()
                    .block(nameOrMaterial, p -> new StairBlock(parentBlock.get().defaultBlockState(), p))
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);
        }


        /* ****************** WALL ****************** */
        /**
         * @param nameOrMaterial The name of the wall. Notice that _wall will automatically be added unless already present.
         * @param mapColor Map color of the wall.
         * @param sound Sound of the wall.
         * @return Basic BlockBuilder for a WallBlock.
         */
        public static BlockBuilder<WallBlock, CreateRegistrate> simpleWallBlock(String nameOrMaterial, MapColor mapColor, SoundType sound) {
            if (!nameOrMaterial.endsWith("_wall")) nameOrMaterial += "_wall";
            return getRegistrate()
                    .block(nameOrMaterial, WallBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.requiresCorrectToolForDrops().sound(sound).mapColor(mapColor))
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.WALLS);
        }

        /* ****************** SIGNS ****************** */

        /**
         * @param name The block name. Sign (_sign) will be automatically added unless already present.
         * @param itemModelTexture The resource location of the item texture.
         * @param itemTag The name of the item tag that the block (item) will have. Also used for the stonecutting recipe. If null, default will be applied (square_signs).
         * @param forgeIngotTagForRecipe If not null, the block can be crafted with the specified ingot (result is 2 from 1 ingot).
         * @return Base BlockBuilder for a SquareSignBlock.
         * */
        public static BlockBuilder<SquareSignBlock, CreateRegistrate> simpleSquareSignBlock(String name, ResourceLocation itemModelTexture, @Nullable String itemTag, @Nullable String forgeIngotTagForRecipe) {
            if (!name.endsWith("_sign")) name += "_sign";
            return getRegistrate()
                    .block(name, SquareSignBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SIGNS)
                    .item().model((c, p) -> p.generated(c, itemModelTexture)).tag(ItemUtils.customTagOrDefault("square_signs", itemTag)).build()
                    .recipe((c, p) -> {
                        if (itemTag != null)
                            p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asResource(itemTag))), RecipeCategory.BUILDING_BLOCKS, c, 1);
                        if (forgeIngotTagForRecipe != null)
                            p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asNamespaceResource("forge", "ingots/" + forgeIngotTagForRecipe))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                    });
        }

        /**
         * @param name The block name. Sign (_sign) will be automatically added unless already present.
         * @param itemModelTexture The resource location of the item texture.
         * @param itemTag The name of the item tag that the block (item) will have. Also used for the stonecutting recipe. If null, default will be applied (square_signs), also for the stonecutting recipe.
         * @param forgeIngotTagForRecipe If not null, the block can be crafted with the specified ingot (result is 2 from 1 ingot).
         * @return Base BlockBuilder for a RotableSquareSignBlock.
         * */
        public static BlockBuilder<RotableSquareSignBlock, CreateRegistrate> rotableSquareSignBlock(String name, ResourceLocation itemModelTexture, @Nullable String itemTag, @Nullable String forgeIngotTagForRecipe) {
            if (!name.endsWith("_sign")) name += "_sign";
            return getRegistrate()
                    .block(name, RotableSquareSignBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops())
                    .loot(RegistrateBlockLootTables::dropSelf)
                    .tag(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SIGNS)
                    .item().model((c, p) -> p.generated(c, itemModelTexture)).tag(ItemUtils.customTagOrDefault("square_signs", itemTag)).build()
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.tag(ItemUtils.customTagOrDefault("square_signs", itemTag)), RecipeCategory.BUILDING_BLOCKS, c, 1);
                        if (forgeIngotTagForRecipe != null)
                            p.stonecutting(DataIngredient.tag(TagKey.create(Registries.ITEM, asNamespaceResource("forge", "ingots/" + forgeIngotTagForRecipe))), RecipeCategory.BUILDING_BLOCKS, c, 2);
                    });
        }
    }
}
