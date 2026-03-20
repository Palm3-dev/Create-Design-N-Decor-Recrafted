package com.palm3.designdecor.register;

import com.palm3.designdecor.DDMain;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class DDSpriteShifts {

    /*private static final Map<DyeColor, Couple<CTSpriteShiftEntry>> COLORED_STORAGE_TOP = new HashMap();
    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>> COLORED_STORAGE_FRONT = new HashMap();
    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>> COLORED_STORAGE_SIDE = new HashMap();
    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>> COLORED_STORAGE_BOTTOM = new HashMap();

    private static void populateMaps() {
        for(DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            COLORED_STORAGE_TOP.put(color, storage("top", color));
            COLORED_STORAGE_FRONT.put(color, storage("front", color));
            COLORED_STORAGE_SIDE.put(color, storage("side", color));
            COLORED_STORAGE_BOTTOM.put(color, storage("bottom", color));
        }

    }

    public static CTSpriteShiftEntry getColoredStorageTop(DyeColor color, boolean small) {
        return (CTSpriteShiftEntry)((Couple)COLORED_STORAGE_TOP.get(color)).get(small);
    }

    public static CTSpriteShiftEntry getColoredStorageFront(DyeColor color, boolean small) {
        return (CTSpriteShiftEntry)((Couple)COLORED_STORAGE_FRONT.get(color)).get(small);
    }

    public static CTSpriteShiftEntry getColoredStorageSide(DyeColor color, boolean small) {
        return (CTSpriteShiftEntry)((Couple)COLORED_STORAGE_SIDE.get(color)).get(small);
    }

    public static CTSpriteShiftEntry getColoredStorageBottom(DyeColor color, boolean small) {
        return (CTSpriteShiftEntry)((Couple)COLORED_STORAGE_BOTTOM.get(color)).get(small);
    }

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DDMain.asResource("block/" + blockTextureName), DDMain.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    private static Couple<CTSpriteShiftEntry> storage(String name, DyeColor color) {
        String var10000 = color.getSerializedName();
        String prefixed = "block/storage_container/" + var10000 + "_storage_container_" + name;
        return Couple.createWithContext((medium) -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, DDMain.asResource(prefixed + "_small"), DDMain.asResource(medium ? prefixed + "_medium" : prefixed + "_large")));
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(DDMain.asResource(originalLocation), DDMain.asResource(targetLocation));
    }

    public static void init() {
    }

    static {
        populateMaps();
    }*/
}
