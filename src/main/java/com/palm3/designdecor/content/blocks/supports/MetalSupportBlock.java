package com.palm3.designdecor.content.blocks.supports;

import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.palm3.designdecor.content.blocks.supports.MetalSupport.*;

@SuppressWarnings({"deprecated"})
public class MetalSupportBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<MetalSupport> BLOCK_TYPE = EnumProperty.create("block_type", MetalSupport.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MetalSupportBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BLOCK_TYPE, TOP_BOTTOM)
                .setValue(HORIZONTAL_AXIS, Direction.Axis.X)
                .setValue(WATERLOGGED, false));
    }

    @ParametersAreNonnullByDefault
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCK_TYPE, HORIZONTAL_AXIS, WATERLOGGED);
    }

    // Shape
    public static final VoxelShape SHAPE_POLE = SquareSignBlock.box(4, 0,4,12, 16, 12);
    public static final VoxelShape SHAPE_UP_PIECE_X = SquareSignBlock.box(0, 13, 4, 16, 16 ,12);
    public static final VoxelShape SHAPE_UP_PIECE_Z = SquareSignBlock.box(4, 13, 0, 12, 16 ,16);
    public static final VoxelShape SHAPE_T_X = Shapes.or(SHAPE_POLE, SHAPE_UP_PIECE_X);  // T shape with X axis
    public static final VoxelShape SHAPE_T_Z = Shapes.or(SHAPE_POLE, SHAPE_UP_PIECE_Z);  // T shape with Z axis



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

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean isWaterlogged = fluidstate.getType() == Fluids.WATER;

        MetalSupport finalType = TOP_BOTTOM;
        BlockState aboveState = context.getLevel().getBlockState(context.getClickedPos().above());
        BlockState belowState = context.getLevel().getBlockState(context.getClickedPos().below());

        if (belowState.is(this.asBlock()) && !aboveState.is(this.asBlock())) {  // Below THIS - above OTHER
            MetalSupport belowBlockType = belowState.getValue(BLOCK_TYPE);
            boolean belowIsValid = belowBlockType.equals(TOP) || belowBlockType.equals(TOP_BOTTOM);
            if (belowIsValid)
                finalType = TOP;

        } else if (!belowState.is(this.asBlock()) && aboveState.is(this.asBlock())) {  // Below OTHER - above THIS
            MetalSupport aboveBlockType = aboveState.getValue(BLOCK_TYPE);
            boolean aboveIsValid = aboveBlockType.equals(BOTTOM) || aboveBlockType.equals(TOP_BOTTOM);
            if (aboveIsValid)
                finalType = BOTTOM;

        } else if (aboveState.is(this.asBlock()) && belowState.is(this.asBlock())) {  // Below THIS - above THIS
            finalType = MIDDLE;
        }

        return defaultBlockState()
                .setValue(BLOCK_TYPE, finalType)
                .setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis())
                .setValue(WATERLOGGED, isWaterlogged);
    }

    @ParametersAreNonnullByDefault
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        BlockState aboveState = level.getBlockState(pos.above());
        BlockState belowState = level.getBlockState(pos.below());

        if (!belowState.is(this.asBlock()) && !aboveState.is(this.asBlock())) {  // Below OTHER - above OTHER
            level.setBlock(pos, state.setValue(BLOCK_TYPE, TOP_BOTTOM), 3);  // Becomes TOP_BOTTOM

        } else if (belowState.is(this.asBlock()) && !aboveState.is(this.asBlock())) {  // Below THIS - above OTHER
            level.setBlock(pos, state.setValue(BLOCK_TYPE, TOP), 3);  // Becomes TOP

        } else if (!belowState.is(this.asBlock()) && aboveState.is(this.asBlock())) {  // Below OTHER - above THIS
            level.setBlock(pos, state.setValue(BLOCK_TYPE, BOTTOM), 3);  // Becomes BOTTOM

        } else if (belowState.is(this.asBlock()) && aboveState.is(this.asBlock())) {  // Below THIS - above THIS
            level.setBlock(pos, state.setValue(BLOCK_TYPE, MIDDLE), 3);  // Becomes MIDDLE
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.getItem() == AllItems.WRENCH.asItem() && (state.getValue(BLOCK_TYPE) == TOP || state.getValue(BLOCK_TYPE) == TOP_BOTTOM)) {
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

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        return super.updateShape(state, direction, blockState, level, pos, neighborPos);
    }
}
