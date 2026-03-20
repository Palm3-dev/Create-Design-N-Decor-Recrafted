package com.palm3.designdecor.blocks.frontlight;

import net.minecraft.util.StringRepresentable;

public enum Frontlight implements StringRepresentable {
    NORMAL("empty"),
    TOP("top"),
    CAGE("grate");

    private final String name;

    Frontlight(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}