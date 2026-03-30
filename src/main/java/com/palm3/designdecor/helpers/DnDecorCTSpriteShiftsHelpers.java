package com.palm3.designdecor.helpers;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;

import static com.palm3.designdecor.DDMain.asDDResource;

public class DnDecorCTSpriteShiftsHelpers {
    //============================= CTSpriteShifts =============================
    // By "Connected" i mean that the texture name has "_connected" at the end. Location (or Loc) means that the textures are not in the default textures/block folder

    /// Only for textures that are in /block directory.
    public static CTSpriteShiftEntry omniDDConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asDDResource("block/" + texture_name),
                asDDResource("block/" + texture_name + "_connected")
        );
    }


    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry omniDDLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// Only for textures that are in /block directory.
    public static CTSpriteShiftEntry rectangleDDConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asDDResource("block/" + texture_name),
                asDDResource("block/" + texture_name + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry rectangleDDLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry horizKryppersDDLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.HORIZONTAL_KRYPPERS,
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    /// You can specify paths within the /block directory.
    /// The texture parameter can also accept paths if you need it.
    public static CTSpriteShiftEntry verticalDDLocationConnected(String texture_dir_loc, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.VERTICAL,
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path),
                asDDResource("block/" + texture_dir_loc + "/" + texture_name_accepts_path + "_connected")
        );
    }

    // The path can be put in the texture_name so that in the BlockBuildingHelpers you can use subfolders in the texture name.
    // like for the pillar that has two textures in two folders under /block/palettes/stone_types
}
