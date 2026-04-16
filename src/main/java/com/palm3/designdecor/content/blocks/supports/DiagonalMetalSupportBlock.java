package com.palm3.designdecor.content.blocks.supports;

import com.palm3.designdecor.content.blocks.sign_blocks.SquareSignBlock;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import net.createmod.catnip.math.VoxelShaper;
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

public class DiagonalMetalSupportBlock extends Block {

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DiagonalMetalSupportBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }

    // Shape
    public static final VoxelShape SHAPE_WALL = SquareSignBlock.box(3, 0,11,13, 10, 16);
    public static final VoxelShape SHAPE_CEILING = SquareSignBlock.box(0, 12, 0, 16, 16 ,16);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_WALL, SHAPE_CEILING);

    @NotNull
    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VoxelShaper.forHorizontal(SHAPE, Direction.NORTH).get(state.getValue(HORIZONTAL_FACING));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    // Behaviour
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.getItem() == AllItems.WRENCH.asItem() && (hit.getDirection() == Direction.UP) || hit.getDirection() == Direction.DOWN) {
            Direction dir = state.getValue(HORIZONTAL_FACING);
            switch (dir) {
                case NORTH -> level.setBlock(pos, state.setValue(HORIZONTAL_FACING, Direction.EAST), 3);
                case EAST -> level.setBlock(pos, state.setValue(HORIZONTAL_FACING, Direction.SOUTH), 3);
                case SOUTH -> level.setBlock(pos, state.setValue(HORIZONTAL_FACING, Direction.WEST), 3);
                case WEST -> level.setBlock(pos, state.setValue(HORIZONTAL_FACING, Direction.NORTH), 3);
            }
            level.playSound(null, pos, AllSoundEvents.WRENCH_ROTATE.getMainEvent(), SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}
