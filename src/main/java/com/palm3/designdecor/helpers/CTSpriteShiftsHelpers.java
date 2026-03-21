package com.palm3.designdecor.helpers;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;

import static com.palm3.designdecor.DDMain.asResource;

public class CTSpriteShiftsHelpers {
    //============================= CTSpriteShifts =============================
    // By "Connected" i mean that the texture name has "_connected" at the end. Location (or Loc) means that the textures are not in the default textures/block folder

    /// Only for textures that are in /block directory.
    public static CTSpriteShiftEntry omniConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asResource("block/" + texture_name),
                asResource("block/" + texture_name + "_connected")
        );
    }


    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry omniLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// Only for textures that are in /block directory.
    public static CTSpriteShiftEntry rectangleConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asResource("block/" + texture_name),
                asResource("block/" + texture_name + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry rectangleLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry horizKryppersLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.HORIZONTAL_KRYPPERS,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
            asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry verticalLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.VERTICAL,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    // The path can be put in the texture_name so that in the BlockBuildingHelpers you can use subfolders in the texture name.
    // like for the pillar that has two textures in two folders under /block/palettes/stone_types
}
