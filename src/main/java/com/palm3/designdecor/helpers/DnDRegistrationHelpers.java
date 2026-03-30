/* Feel free to use this class (or methods) in your mod if you want! */

/* The block registers DON'T need the .register() method. */

package com.palm3.designdecor.helpers;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.content.decoration.palettes.LayeredBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;

import static com.palm3.designdecor.helpers.DesignDecorBlockBuildingHelpers.*;

public class DnDRegistrationHelpers {
    public static final Map<String, BlockEntry<Block>> BLOCKS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<SlabBlock>> SLABS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<StairBlock>> STAIRS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<WallBlock>> WALLS_MAP = new HashMap<>();
    public static final Map<String, BlockEntry<ConnectedPillarBlock>> PILLARS_MAP = new HashMap<>();

    /// Registers a full stone block set (blocks, slabs, stairs, walls, pillar, layered) in cut, brick and polished version.
    /// Takes the pillar assets from /block/palettes/stone_types --> /pillar (side) & /cap (top).
    public static void registerStoneBlockSet(String material, MapColor generalMapColor, SoundType generalSoundType) {
        BLOCKS_MAP.put("cut_" + material, simpleDDStoneBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("polished_cut_" + material, simpleDDStoneBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("cut_" + material + "_bricks", simpleDDStoneBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, material).register());
        BLOCKS_MAP.put("small_" + material + "_bricks", simpleDDStoneBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, material).register());

        SLABS_MAP.put("cut_" + material + "_slab", simpleDDStoneSlabBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("polished_cut_" + material + "_slab", simpleDDStoneSlabBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("cut_" + material + "_brick_slab", simpleDDStoneSlabBlock("cut_" + material + "_brick", generalMapColor, generalSoundType, material).register());
        SLABS_MAP.put("small_" + material + "_brick_slab", simpleDDStoneSlabBlock("small_" + material + "_brick", generalMapColor, generalSoundType, material).register());

        STAIRS_MAP.put("cut_" + material + "_stairs", simpleDDStoneStairBlock("cut_" + material, () -> BLOCKS_MAP.get("cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("polished_cut_" + material + "_stairs", simpleDDStoneStairBlock("polished_cut_" + material, () -> BLOCKS_MAP.get("polished_cut_" + material).get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("cut_" + material + "_brick_stairs", simpleDDStoneStairBlock("cut_" + material + "_brick", () -> BLOCKS_MAP.get("cut_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());
        STAIRS_MAP.put("small_" + material + "_brick_stairs", simpleDDStoneStairBlock("small_" + material + "_brick", () -> BLOCKS_MAP.get("small_" + material + "_bricks").get(), generalMapColor, generalSoundType, material).register());

        WALLS_MAP.put("cut_" + material + "_wall", simpleDDStoneWallBlock("cut_" + material, generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("polished_cut_" + material + "_wall", simpleDDStoneWallBlock("polished_cut_" + material, generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("cut_" + material + "_bricks_wall", simpleDDStoneWallBlock("cut_" + material + "_bricks", generalMapColor, generalSoundType, material).register());
        WALLS_MAP.put("small_" + material + "_bricks_wall", simpleDDStoneWallBlock("small_" + material + "_bricks", generalMapColor, generalSoundType, material).register());

        PILLARS_MAP.put("layered_" + material, simpleDDStoneLayeredBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "layered/" + material + "_cut_layered", "cap/" + material + "_cut_cap", material).register());

        PILLARS_MAP.put(material + "_pillar", simpleDDStonePillarBlock(material, generalMapColor, generalSoundType, "palettes/stone_types", "pillar/" + material + "_cut_pillar", "cap/" + material + "_cut_cap", material).register());
    }
}
