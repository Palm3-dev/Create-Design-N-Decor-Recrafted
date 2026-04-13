package com.palm3.designdecor.content.blocks.storage_container;

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
