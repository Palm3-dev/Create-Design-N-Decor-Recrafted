package com.palm3.designdecor.helpers;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;

import static com.palm3.designdecor.DDMain.asResource;

public class CTSpriteShiftsHelpers {
    //============================= CTSpriteShifts =============================
    // By "Connected" i mean that the texture name has "_connected" at the end. Location (or Loc) means that the textures are not in the default textures/block folder
    public static CTSpriteShiftEntry omniConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asResource("block/" + texture_name),
                asResource("block/" + texture_name + "_connected")
        );
    }

    public static CTSpriteShiftEntry omniLocationConnected(String texture_dir_loc, String texture_name_accepts_loc) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc + "_connected")
        );
    }

    public static CTSpriteShiftEntry rectangleConnected(String texture_name) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asResource("block/" + texture_name),
                asResource("block/" + texture_name + "_connected")
        );
    }

    public static CTSpriteShiftEntry rectangleLocationConnected(String texture_dir_loc, String texture_name_accepts_loc) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc + "_connected")
        );
    }

    public static CTSpriteShiftEntry horizKryppersLocationConnected(String texture_dir_loc, String texture_name_accepts_loc) {
        return CTSpriteShifter.getCT(
                AllCTTypes.HORIZONTAL_KRYPPERS,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc),
            asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc + "_connected")
        );
    }

    public static CTSpriteShiftEntry verticalLocationConnected(String texture_dir_loc, String texture_name_accepts_loc) {
        return CTSpriteShifter.getCT(
                AllCTTypes.VERTICAL,
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc),
                asResource("block/" + texture_dir_loc + "/" + texture_name_accepts_loc + "_connected")
        );
    }
}
