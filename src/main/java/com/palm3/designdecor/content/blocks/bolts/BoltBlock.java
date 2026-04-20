package com.palm3.designdecor.content.blocks.bolts;

import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class BoltBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Bolt> BOLT_ROTATION = EnumProperty.create("bolt_rotation", Bolt.class);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BoltBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.UP)
                .setValue(BOLT_ROTATION, Bolt.DEG_0)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOLT_ROTATION, FACING, WATERLOGGED);
    }

    // Shape
    public static final VoxelShape SHAPE_BOLT = SquareSignBlock.box(3, 0,3,13, 3, 13);

    @NotNull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VoxelShaper.forDirectional(SHAPE_BOLT, Direction.UP).get(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean isWaterlogged = fluidstate.getType() == Fluids.WATER;

        return defaultBlockState()
                .setValue(FACING, context.getClickedFace())
                .setValue(BOLT_ROTATION, Bolt.DEG_0)
                .setValue(WATERLOGGED, isWaterlogged);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.getItem() == AllItems.WRENCH.asItem()) {
            Bolt rot = state.getValue(BOLT_ROTATION);
            switch (rot) {
                case DEG_0 -> level.setBlock(pos, state.setValue(BOLT_ROTATION, Bolt.DEG_45), 3);
                case DEG_45 -> level.setBlock(pos, state.setValue(BOLT_ROTATION, Bolt.DEG_90), 3);
                case DEG_90 -> level.setBlock(pos, state.setValue(BOLT_ROTATION, Bolt.DEG_135), 3);
                case DEG_135 -> level.setBlock(pos, state.setValue(BOLT_ROTATION, Bolt.DEG_0), 3);
                default -> throw new IllegalArgumentException("Bolt rotation cannot be: '" + rot + "'");
            }
            level.playSound(null, pos, AllSoundEvents.WRENCH_ROTATE.getMainEvent(), SoundSource.BLOCKS, 0.5f, 1.1f);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    @MethodsReturnNonnullByDefault
    public void neighborChanged(BlockState state, Level level, @NotNull BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        Direction facing = state.getValue(FACING);

        BlockPos checkAt = switch (facing) {
            case UP -> checkAt = pos.below();
            case DOWN -> checkAt = pos.above();
            case EAST -> checkAt = pos.west();
            case WEST -> checkAt = pos.east();
            case SOUTH -> checkAt = pos.north();
            case NORTH -> checkAt = pos.south();
        };

        boolean canSurvive = !level.getBlockState(checkAt).isAir();

        if (!canSurvive) level.destroyBlock(pos, true);
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