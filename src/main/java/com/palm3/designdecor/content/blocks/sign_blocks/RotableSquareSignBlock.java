package com.palm3.designdecor.content.blocks.sign_blocks;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

import static com.palm3.designdecor.content.blocks.sign_blocks.RotableSquareSign.*;

@SuppressWarnings("deprecated")
public class RotableSquareSignBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<RotableSquareSign> AXIS_ROT = EnumProperty.create("axis_rot", RotableSquareSign.class);

    public RotableSquareSignBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(AXIS_ROT, ROT0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AXIS_ROT);
    }

    //------------------- Shape -------------------
    public static final VoxelShape SHAPE_UP = RotableSquareSignBlock.box(1, 0,1,15, 1, 15);
    public static final VoxelShape SHAPE_DOWN = RotableSquareSignBlock.box(1, 15, 1, 15, 16 ,15);
    public static final VoxelShape SHAPE_SOUTH = RotableSquareSignBlock.box(1, 1, 0, 15, 15, 1);
    public static final VoxelShape SHAPE_NORTH = RotableSquareSignBlock.box(1, 1, 15, 15, 15, 16);
    public static final VoxelShape SHAPE_EAST = RotableSquareSignBlock.box(0, 1, 1, 1, 15, 15);
    public static final VoxelShape SHAPE_WEST = RotableSquareSignBlock.box(15, 1, 1, 16, 15, 15);

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
                .setValue(FACING, context.getClickedFace())
                .setValue(AXIS_ROT, ROT0);
    }

    @Override
    @NotNull
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);
        if (item.getItem() == AllItems.WRENCH.asItem()) {
            var axisRot = state.getValue(AXIS_ROT);
            if (!player.isShiftKeyDown()) {
                switch (axisRot) {
                    case ROT0 -> level.setBlock(pos, state.setValue(AXIS_ROT, ROT90), 3);
                    case ROT90 -> level.setBlock(pos, state.setValue(AXIS_ROT, ROT180), 3);
                    case ROT180 -> level.setBlock(pos, state.setValue(AXIS_ROT, ROT270), 3);
                    case ROT270 -> level.setBlock(pos, state.setValue(AXIS_ROT, ROT0), 3);
                }
                level.playSound(null, pos, AllSoundEvents.WRENCH_ROTATE.getMainEvent(), SoundSource.BLOCKS, 0.5f, 1.6f);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
