package com.palm3.designdecor.content.blocks.supports;

import net.minecraft.util.StringRepresentable;

public enum MetalSupport implements StringRepresentable {
    BOTTOM("bottom"),
    TOP("top"),
    TOP_BOTTOM("top_bottom"),
    MIDDLE("middle");

    private final String name;

    MetalSupport(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}

