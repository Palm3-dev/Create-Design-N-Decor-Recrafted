package com.palm3.designdecor.content.blocks.sign_blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum RotableSquareSign implements StringRepresentable {
    /* The rotation is represented in angles.
       For "top" I mean the normal position in which the sign would be positioned.
       For example, the sign with letter 'A' oriented in this position (default, on a wall) is 0 degrees, oriented like this ↑
       For 90 is -→, for 180 is ↓ (so the 'A' is now like this: ∀) and so on. This is valid with the sign both on the floor or on a wall, obviously.
    */

    ROT0("rot0"),
    ROT90("rot90"),
    ROT180("rot180"),
    ROT270("rot270");

    private final String name;

    RotableSquareSign(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
