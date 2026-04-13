package com.palm3.designdecor.content.blocks.sign_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@SuppressWarnings("deprecated")
public class SquareSignBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public SquareSignBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //------------------- Shape -------------------
    public static final VoxelShape SHAPE_UP = SquareSignBlock.box(1, 0,1,15, 1, 15);
    public static final VoxelShape SHAPE_DOWN = SquareSignBlock.box(1, 15, 1, 15, 16 ,15);
    public static final VoxelShape SHAPE_SOUTH = SquareSignBlock.box(1, 1, 0, 15, 15, 1);
    public static final VoxelShape SHAPE_NORTH = SquareSignBlock.box(1, 1, 15, 15, 15, 16);
    public static final VoxelShape SHAPE_EAST = SquareSignBlock.box(0, 1, 1, 1, 15, 15);
    public static final VoxelShape SHAPE_WEST = SquareSignBlock.box(15, 1, 1, 16, 15, 15);

    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
            Direction.UP, SHAPE_UP,
            Direction.DOWN, SHAPE_DOWN,
            Direction.NORTH, SHAPE_NORTH,
            Direction.SOUTH, SHAPE_SOUTH,
            Direction.EAST, SHAPE_EAST,
            Direction.WEST, SHAPE_WEST
    );

    @ParametersAreNonnullByDefault
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.getOrDefault(state.getValue(FACING), SHAPE_UP);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getClickedFace());
    }
}
