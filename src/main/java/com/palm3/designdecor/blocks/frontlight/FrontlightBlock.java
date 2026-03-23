// This class has been partially rewritten

package com.palm3.designdecor.blocks.frontlight;

import com.palm3.designdecor.DDMain;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class FrontlightBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty CAGE_TYPE = EnumProperty.create("additive", Frontlight.class);
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

    public FrontlightBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LIT) ? 15 : 0));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(CAGE_TYPE, Frontlight.TOP)
                .setValue(ROTATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, CAGE_TYPE, ROTATED);
    }

    //------------------- Shape -------------------
    public static final VoxelShape SHAPE_UP = FrontlightBlock.box(3, 0,3,13, 8, 13);
    public static final VoxelShape SHAPE_DOWN = FrontlightBlock.box(3, 9, 3, 13, 16 ,13);
    public static final VoxelShape SHAPE_SOUTH = FrontlightBlock.box(3, 3, 0, 13, 13, 8);
    public static final VoxelShape SHAPE_NORTH = FrontlightBlock.box(3, 3, 8, 13, 13, 16);
    public static final VoxelShape SHAPE_EAST = FrontlightBlock.box(0, 3, 3, 8, 13, 13);
    public static final VoxelShape SHAPE_WEST = FrontlightBlock.box(8, 3, 3, 16, 13, 13);

    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
            Direction.UP, SHAPE_UP,
            Direction.DOWN, SHAPE_DOWN,
            Direction.NORTH, SHAPE_NORTH,
            Direction.SOUTH, SHAPE_SOUTH,
            Direction.EAST, SHAPE_EAST,
            Direction.WEST, SHAPE_WEST
    );

    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.getOrDefault(state.getValue(FACING), SHAPE_UP);
    }

    //----------------- Behaviour -----------------

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getClickedFace())
                .setValue(LIT, context.getLevel().hasNeighborSignal(new BlockPos(context.getClickedPos())))
                .setValue(CAGE_TYPE, Frontlight.NORMAL)
                .setValue(ROTATED, false);
    }

    @Override
    @MethodsReturnNonnullByDefault
    public void neighborChanged(BlockState state, Level level, @NotNull BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
        } else {
            level.setBlock(pos, state.setValue(LIT, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.getItem() != AllItems.WRENCH.asItem()) {  // No wrench
            if (state.getValue(LIT)) {
                level.setBlock(pos, state.setValue(LIT, false), 3);
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.5f, 1.2f);
            } else {
                level.setBlock(pos, state.setValue(LIT, true), 3);
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.5f, 1.4f);
            }

        } else {  // With wrench
            if (!player.isShiftKeyDown()) {
                if (state.getValue(FACING).getAxis() == Direction.Axis.Y) {
                    if (hit.getDirection() == Direction.DOWN || hit.getDirection() == Direction.UP) {
                        boolean rotated = state.getValue(ROTATED);
                        level.setBlock(pos, state.setValue(ROTATED, !rotated), 3);
                    } else {
                        if (state.getValue(CAGE_TYPE) == Frontlight.NORMAL)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.TOP), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.TOP)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.CAGE), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.CAGE)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.NORMAL), 3);
                    }
                } else if (state.getValue(FACING).getAxis() == Direction.Axis.X) {
                    if (hit.getDirection() == Direction.EAST || hit.getDirection() == Direction.WEST) {
                        boolean rotated = state.getValue(ROTATED);
                        level.setBlock(pos, state.setValue(ROTATED, !rotated), 3);
                    } else {
                        if (state.getValue(CAGE_TYPE) == Frontlight.NORMAL)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.TOP), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.TOP)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.CAGE), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.CAGE)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.NORMAL), 3);
                    }
                } else {
                    if (hit.getDirection() == Direction.NORTH || hit.getDirection() == Direction.SOUTH) {
                        boolean rotated = state.getValue(ROTATED);
                        level.setBlock(pos, state.setValue(ROTATED, !rotated), 3);
                    } else {
                        if (state.getValue(CAGE_TYPE) == Frontlight.NORMAL)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.TOP), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.TOP)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.CAGE), 3);
                        if (state.getValue(CAGE_TYPE) == Frontlight.CAGE)
                            level.setBlock(pos, state.setValue(CAGE_TYPE, Frontlight.NORMAL), 3);
                    }
                }
                level.playSound(null, pos, AllSoundEvents.WRENCH_ROTATE.getMainEvent(), SoundSource.BLOCKS, 0.5f, 1.5f);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
