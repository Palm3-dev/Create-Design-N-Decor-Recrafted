package com.palm3.designdecor.register;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;

import static com.palm3.designdecor.DnDMain.*;
import static com.palm3.designdecor.foundation.helpers.create_registrate.RegistrateBlockBuildingHelpers.ItemUtils.itemTag;

public class DnDItems {
    public static final ItemEntry<Item> INDUSTRIAL_IRON_INGOT = DND_REGISTRATE
            .item("industrial_iron_ingot", Item::new)
            .properties(p -> p.stacksTo(64))
            .tag(itemTag("forge", "ingots/industrial_iron"))
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                        .requires(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem())
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem()))
                        .save(p);
            })
            .model((c, p) -> p.withExistingParent(c.getName(), asNamespaceResource("minecraft", "item/generated")).texture("layer0", asResource("item/industrial_iron_ingot")))
            .register();

    public static void register() {}
}
