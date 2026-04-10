package com.palm3.designdecor.foundation.helpers.create_registrate;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import org.jetbrains.annotations.NotNull;

import static com.palm3.designdecor.DnDMain.asNamespaceResource;

public class CTSpriteShiftsHelpers {
    //============================= CTSpriteShifts =============================
    // By "Conn" (connected) I mean that the texture name has "_connected" at the end.

    /**
     * Searches for the texture (plus the connected one) in 'textures/block/.' directory.
     * @param namespace The namespace where to search the textures.
     * @param texture_name_accepts_path The name of the texture. Can also be a path, like 'my_textures/cool_thing' searches 'textures/block/my_textures/cool_thing.png' and 'cool_thing_connected.png'
     * @return CTSpriteShifter.getCT(...)
     */
    public static CTSpriteShiftEntry omniConn(@NotNull String namespace, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.OMNIDIRECTIONAL,
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path),
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path + "_connected")
        );
    }

    /**
     * Searches for the texture (plus the connected one) in 'textures/block/.' directory.
     * @param namespace The namespace where to search the textures.
     * @param texture_name_accepts_path The name of the texture. Can also be a path, like 'my_textures/cool_thing' searches 'textures/block/my_textures/cool_thing.png' and 'cool_thing_connected.png'
     * @return CTSpriteShifter.getCT(...)
     */
    public static CTSpriteShiftEntry rectangleConn(@NotNull String namespace, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.RECTANGLE,
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path),
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path + "_connected")
        );
    }

    /**
     * Searches for the texture (plus the connected one) in 'block/.' directory.
     * @param namespace The namespace where to search the textures.
     * @param texture_name_accepts_path The name of the texture. Can also be a path, like 'my_textures/cool_thing' searches 'textures/block/my_textures/cool_thing.png' and 'cool_thing_connected.png'
     * @return CTSpriteShifter.getCT(...)
     */
    public static CTSpriteShiftEntry horizKryppersConn(@NotNull String namespace, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.HORIZONTAL_KRYPPERS,
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path),
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path + "_connected")
        );
    }

    /**
     * Searches for the texture (plus the connected one) in 'block/.' directory.
     * @param namespace The namespace where to search the textures.
     * @param texture_name_accepts_path The name of the texture. Can also be a path, like 'my_textures/cool_thing' searches 'textures/block/my_textures/cool_thing.png' and 'cool_thing_connected.png'
     * @return CTSpriteShifter.getCT(...)
     */
    public static CTSpriteShiftEntry verticalConn(@NotNull String namespace, String texture_name_accepts_path) {
        return CTSpriteShifter.getCT(
                AllCTTypes.VERTICAL,
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path),
                asNamespaceResource(namespace, "block/" + texture_name_accepts_path + "_connected")
        );
    }
}
