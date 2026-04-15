package com.palm3.designdecor.content.blocks.supports;

import com.palm3.designdecor.DnDMain;
import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.palm3.designdecor.content.blocks.supports.MetalSupport.*;

@SuppressWarnings({"deprecated"})
public class MetalSupportBlock extends Block {

    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<MetalSupport> BLOCK_TYPE = EnumProperty.create("block_type", MetalSupport.class);

    public MetalSupportBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BLOCK_TYPE, TOP_BOTTOM)
                .setValue(HORIZONTAL_AXIS, Direction.Axis.X));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCK_TYPE, HORIZONTAL_AXIS);
    }

    // Shape
    public static final VoxelShape SHAPE_POLE = SquareSignBlock.box(4, 0,4,12, 16, 12);
    public static final VoxelShape SHAPE_UP_PIECE_X = SquareSignBlock.box(0, 13, 4, 16, 16 ,12);
    public static final VoxelShape SHAPE_UP_PIECE_Z = SquareSignBlock.box(4, 13, 0, 12, 16 ,16);
    public static final VoxelShape SHAPE_T_X = Shapes.or(SHAPE_POLE, SHAPE_UP_PIECE_X);
    public static final VoxelShape SHAPE_T_Z = Shapes.or(SHAPE_POLE, SHAPE_UP_PIECE_Z);



    @NotNull
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var blockType = state.getValue(BLOCK_TYPE);
        var axis = state.getValue(HORIZONTAL_AXIS);
        if (blockType == TOP || blockType == TOP_BOTTOM) {
            if (axis == Direction.Axis.X) return SHAPE_T_X;
            else return SHAPE_T_Z;
        } else {
            return SHAPE_POLE;
        }
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(BLOCK_TYPE, TOP_BOTTOM)
                .setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis());
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (level.getBlockState(pos.below()).is(this.asBlock())) {
            MetalSupport belowBlockType = level.getBlockState(pos.below()).getValue(BLOCK_TYPE);
            boolean belowIsValid = belowBlockType.equals(TOP) || belowBlockType.equals(TOP_BOTTOM);

            if (belowIsValid) level.setBlock(pos, state.setValue(BLOCK_TYPE, TOP), 3);  // Becomes TOP
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        BlockState aboveState = level.getBlockState(pos.above());
        BlockState belowState = level.getBlockState(pos.below());

        if (aboveState.isAir() && belowState.is(this.asBlock())) {  // Up AIR - down THIS block
            if (belowState.getValue(BLOCK_TYPE).equals(BOTTOM) || belowState.getValue(BLOCK_TYPE).equals(MIDDLE)) {  // If below is BOTTOM or MIDDLE sets to TOP
                level.setBlock(pos, state.setValue(BLOCK_TYPE, TOP), 3);
            }
        } else if (aboveState.isAir() && !belowState.is(this.asBlock())) {  // Up AIR - down NOT THIS block
            level.setBlock(pos, state.setValue(BLOCK_TYPE, TOP_BOTTOM), 3);  // Sets THIS to TOP_BOTTOM
        } else if (aboveState.is(this.asBlock()) && belowState.is(this.asBlock())) {  // Up THIS - down THIS block
            level.setBlock(pos, state.setValue(BLOCK_TYPE, MIDDLE), 3);  // Sets THIS to MIDDLE
        } else if (aboveState.is(this.asBlock()) && !belowState.is(this.asBlock())) {  // Up THIS - down NOT THIS block
            level.setBlock(pos, state.setValue(BLOCK_TYPE, BOTTOM), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.getItem() == AllItems.WRENCH.asItem()) {
            Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);
            switch (axis) {
                case X -> level.setBlock(pos, state.setValue(HORIZONTAL_AXIS, Direction.Axis.Z), 3);
                case Z -> level.setBlock(pos, state.setValue(HORIZONTAL_AXIS, Direction.Axis.X), 3);
            }
            level.playSound(null, pos, AllSoundEvents.WRENCH_ROTATE.getMainEvent(), SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}
