package com.palm3.designdecor.blocks.storage_container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.item.ItemHelper;
//import dev.lopyluna.dndecor.register.DnDecorBETypes;
//import dev.lopyluna.dndecor.register.DnDecorBlocks;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class ColoredStorageContainerBlock /*extends ItemVaultBlock implements IWrenchable*//*, IBE<ColoredStorageContainerBlockEntity>*/ {
    /*public static final Property<Direction.Axis> HORIZONTAL_AXIS;
    public static final BooleanProperty LARGE;
    public static final EnumProperty<DyeColor> COLOR;

    public ColoredStorageContainerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(LARGE, false)).setValue(COLOR, DyeColor.WHITE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_AXIS, LARGE, COLOR);
        super.createBlockStateDefinition(builder);
    }

    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);
        if (item instanceof DyeItem) {
            if (this.applyDye(state, level, pos, item.getBarColor(), player, stack)) {
                level.playSound((Player)null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.1F - level.random.nextFloat() * 0.2F);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public boolean applyDye(BlockState pState, Level pLevel, BlockPos pPos, @Nullable DyeColor pDyeColor, Player pPlayer, ItemStack pDyeStack) {
        assert pDyeColor != null;

        if (pState.hasProperty(COLOR) && !pDyeStack.isEmpty()) {
            BlockEntity var8 = pLevel.getBlockEntity(pPos);
            if (var8 instanceof ItemVaultBlockEntity) {
                ItemVaultBlockEntity be = (ItemVaultBlockEntity)var8;
                ItemVaultBlockEntity controllerBE = be.getControllerBE();
                if (controllerBE == null) {
                    return false;
                }

                BlockState blockState = pLevel.getBlockState(controllerBE.getBlockPos());
                BlockState newState = (BlockState)blockState.setValue(COLOR, pDyeColor);
                if (blockState.getValue(COLOR) == newState.getValue(COLOR)) {
                    return false;
                }

                List<BlockPos> positions = new ArrayList();
                if (be.isController()) {
                    int radius = be.radius;
                    int length = be.length;

                    for(int y = 0; y < radius; ++y) {
                        for(int z = 0; z < (controllerBE.axis == Axis.X ? radius : length); ++z) {
                            for(int x = 0; x < (controllerBE.axis == Axis.Z ? radius : length); ++x) {
                                BlockPos pos = controllerBE.getBlockPos().offset(x, y, z);
                                BlockState stateAtPos = pLevel.getBlockState(pos);
                                BlockEntity var20 = pLevel.getBlockEntity(pos);
                                if (var20 instanceof ColoredStorageContainerBlockEntity) {
                                    ColoredStorageContainerBlockEntity be2 = (ColoredStorageContainerBlockEntity)var20;
                                    if (be.getController() != be2.getController()) {
                                        continue;
                                    }
                                }

                                if (!stateAtPos.isAir() && stateAtPos.hasProperty(COLOR)) {
                                    positions.add(pos);
                                }
                            }
                        }
                    }
                }

                pLevel.setBlockAndUpdate(controllerBE.getBlockPos(), (BlockState)controllerBE.getBlockState().setValue(COLOR, pDyeColor));

                for(BlockPos pos : positions) {
                    pLevel.setBlockAndUpdate(pos, (BlockState)pLevel.getBlockState(pos).setValue(COLOR, pDyeColor));
                }

                if (!pPlayer.isCreative()) {
                    pDyeStack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }
/*

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getPlayer() == null || !pContext.getPlayer().isShiftKeyDown()) {
            BlockState placedOn = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite()));
            Direction.Axis preferredAxis = getVaultBlockAxis(placedOn);
            if (preferredAxis != null) {
                return (BlockState)this.defaultBlockState().setValue(HORIZONTAL_AXIS, preferredAxis);
            }
        }

        return (BlockState)this.defaultBlockState().setValue(HORIZONTAL_AXIS, pContext.getHorizontalDirection().getAxis());
    }

    public void onPlace(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pOldState.getBlock() != pState.getBlock()) {
            if (!pIsMoving) {
                this.withBlockEntityDo(pLevel, pPos, ColoredStorageContainerBlockEntity::updateConnectivity);
            }
        }
    }

    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getClickedFace().getAxis().isVertical()) {
            BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
            if (be instanceof ColoredStorageContainerBlockEntity) {
                ColoredStorageContainerBlockEntity vault = (ColoredStorageContainerBlockEntity)be;
                ConnectivityHandler.splitMulti(vault);
                vault.removeController(true);
            }

            state = (BlockState)state.setValue(LARGE, false);
        }

        return super.onWrenched(state, context);
    }

    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean pIsMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof ColoredStorageContainerBlockEntity)) {
                return;
            }

            ColoredStorageContainerBlockEntity vaultBE = (ColoredStorageContainerBlockEntity)be;
            ItemHelper.dropContents(world, pos, vaultBE.inventory);
            world.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(vaultBE);
        }

    }

    public static boolean isVault(BlockState state) {
        return DnDecorBlocks.DYED_STORAGE_CONTAINER.has(state);
    }

    @Nullable
    public static Direction.Axis getVaultBlockAxis(BlockState state) {
        return !isVault(state) ? null : (Direction.Axis)state.getValue(HORIZONTAL_AXIS);
    }

    public static boolean isLarge(BlockState state) {
        return !isVault(state) ? false : (Boolean)state.getValue(LARGE);
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        Direction.Axis axis = (Direction.Axis)state.getValue(HORIZONTAL_AXIS);
        return (BlockState)state.setValue(HORIZONTAL_AXIS, rot.rotate(Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE)).getAxis());
    }

    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirrorIn) {
        return state;
    }

    public @NotNull SoundType getSoundType(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos, Entity entity) {
        SoundType soundType = super.getSoundType(state, world, pos, entity);
        return entity != null && entity.getPersistentData().contains("SilenceVaultSound") ? SILENCED_METAL : soundType;
    }

    public boolean hasAnalogOutputSignal(@NotNull BlockState p_149740_1_) {
        return true;
    }

    public int getAnalogOutputSignal(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return ItemHelper.calcRedstoneFromBlockEntity(this, pLevel, pPos);
    }

    public BlockEntityType<? extends ColoredStorageContainerBlockEntity> getBlockEntityType() {
        return (BlockEntityType)DnDecorBETypes.COLORED_STORAGE_CONTAINER.get();
    }

    public Class<ColoredStorageContainerBlockEntity> getBlockEntityClass() {
        return ColoredStorageContainerBlockEntity.class;
    }

    static {
        HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
        LARGE = BooleanProperty.create("large");
        COLOR = EnumProperty.create("colors", DyeColor.class);
        SILENCED_METAL = new DeferredSoundType(0.1F, 1.5F, () -> SoundEvents.NETHERITE_BLOCK_BREAK, () -> SoundEvents.NETHERITE_BLOCK_STEP, () -> SoundEvents.NETHERITE_BLOCK_PLACE, () -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    }*/
}
