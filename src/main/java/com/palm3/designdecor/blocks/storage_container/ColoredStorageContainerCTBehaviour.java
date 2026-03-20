package com.palm3.designdecor.blocks.storage_container;

import com.palm3.designdecor.register.DDSpriteShifts;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredStorageContainerCTBehaviour /*extends ConnectedTextureBehaviour.Base*/ {

    /*public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
        Direction.Axis vaultBlockAxis = ItemVaultBlock.getVaultBlockAxis(state);
        boolean small = !ItemVaultBlock.isLarge(state);
        if (vaultBlockAxis == null) {
            return null;
        } else {
            DyeColor color = (DyeColor)state.getValue(ColoredStorageContainerBlock.COLOR);
            if (direction.getAxis() == vaultBlockAxis) {
                return DDSpriteShifts.getColoredStorageFront(color, small);
            } else if (direction == Direction.UP) {
                return DDSpriteShifts.getColoredStorageTop(color, small);
            } else {
                return direction == Direction.DOWN ? DDSpriteShifts.getColoredStorageBottom(color, small) : DDSpriteShifts.getColoredStorageSide(color, small);
            }
        }
    }

    protected Direction getUpDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        Direction.Axis vaultBlockAxis = ItemVaultBlock.getVaultBlockAxis(state);
        boolean alongX = vaultBlockAxis == Axis.X;
        if (face.getAxis().isVertical() && alongX) {
            return super.getUpDirection(reader, pos, state, face).getClockWise();
        } else if (face.getAxis() != vaultBlockAxis && !face.getAxis().isVertical()) {
            assert vaultBlockAxis != null;

            return Direction.fromAxisAndDirection(vaultBlockAxis, alongX ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
        } else {
            return super.getUpDirection(reader, pos, state, face);
        }
    }

    protected Direction getRightDirection(BlockAndTintGetter reader, BlockPos pos, BlockState state, Direction face) {
        Direction.Axis vaultBlockAxis = ItemVaultBlock.getVaultBlockAxis(state);
        if (face.getAxis().isVertical() && vaultBlockAxis == Axis.X) {
            return super.getRightDirection(reader, pos, state, face).getClockWise();
        } else {
            return face.getAxis() != vaultBlockAxis && !face.getAxis().isVertical() ? Direction.fromAxisAndDirection(Axis.Y, face.getAxisDirection()) : super.getRightDirection(reader, pos, state, face);
        }
    }

    public boolean buildContextForOccludedDirections() {
        return super.buildContextForOccludedDirections();
    }

    public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
        return state == other && ConnectivityHandler.isConnected(reader, pos, otherPos);
    }*/
}
