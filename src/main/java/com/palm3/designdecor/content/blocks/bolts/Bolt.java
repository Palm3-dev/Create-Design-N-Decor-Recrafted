package com.palm3.designdecor.content.blocks.bolts;

import net.minecraft.util.StringRepresentable;

public enum Bolt implements StringRepresentable {
    DEG_0("deg_0"),
    DEG_45("deg_45"),
    DEG_135("deg_135");

    private final String name;

    Bolt(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
