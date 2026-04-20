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
public class RotableSquareSignBlock extends SquareSignBlock {
    public static final EnumProperty<RotableSquareSign> AXIS_ROT = EnumProperty.create("axis_rot", RotableSquareSign.class);

    public RotableSquareSignBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_ROT, ROT0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_ROT);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        RotableSquareSign placeRot = switch (context.getHorizontalDirection()) {
            case UP, DOWN, SOUTH -> ROT0;
            case NORTH -> ROT180;
            case WEST -> ROT90;
            case EAST -> ROT270;
        };
        return state.setValue(AXIS_ROT, placeRot);
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
            return InteractionResult.SUCCESS;
        } else return InteractionResult.PASS;

    }
}
