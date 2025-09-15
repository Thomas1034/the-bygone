package com.jamiedev.bygone.common.block;

import com.jamiedev.bygone.common.block.entity.GumboPotBlockEntity;
import com.jamiedev.bygone.core.registry.BGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GumboPotBlock extends Block implements EntityBlock {
    protected static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 9, 15);
    public static final int MAX_INGREDIENT_AGE = 800; // how long before ingredient is deleted


    public GumboPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            GumboPotBlockEntity entity = (GumboPotBlockEntity) level.getBlockEntity(pos);
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (entity == null) return InteractionResult.PASS;

            if (isBowl(stack)) {
                ItemStack gumbo = entity.extractGumboBowl(player);
                if (!gumbo.isEmpty()) {
                    if (!player.addItem(gumbo)) {
                        player.drop(gumbo, false);
                    }
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            } else if (isIngredient(stack)) {
                    level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;    }


    private boolean isIngredient(ItemStack stack) {
        return !isBowl(stack) && !stack.isEmpty();
    }

    private boolean isBowl(ItemStack stack) {
        return stack.getItem() == Items.BOWL || stack.getItem() == BGItems.GUMBO_BOWL.get();
    }

    @Nullable
    @Override
    public GumboPotBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GumboPotBlockEntity(pos, state);
    }
}