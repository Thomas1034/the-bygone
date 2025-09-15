package com.jamiedev.bygone.common.block.entity;

import com.jamiedev.bygone.core.registry.BGBlockEntities;
import com.jamiedev.bygone.core.registry.BGDataComponentTypes;
import com.jamiedev.bygone.core.registry.BGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GumboPotBlockEntity extends BlockEntity {
    public static final int MAX_LEVELS = 8;

    private int soupLevel = 0;
    private int nutrition = 0;
    private float saturation = 0f;
    private List<String> ingredientNames = new ArrayList<>();

    public GumboPotBlockEntity(BlockPos pos, BlockState state) {
        super(BGBlockEntities.GUMBO_POT.get(), pos, state);
    }

    public void cookSoup(List<ItemStack> addedIngredients) {
        soupLevel = Math.min(addedIngredients.size(), MAX_LEVELS);
        ingredientNames.clear();
        for (ItemStack stack : addedIngredients) {
            ingredientNames.add(stack.getDisplayName().getString());
        }
        setChanged();
    }

    public ItemStack extractGumboBowl(Player player) {
        if (soupLevel <= 0) return ItemStack.EMPTY;
        ItemStack gumboBowl = new ItemStack(BGItems.GUMBO_BOWL.get());
        gumboBowl.set(BGDataComponentTypes.GUMBO_BOWL, new GumboBowlComponent(nutrition, saturation, List.copyOf(ingredientNames)));
        soupLevel--;
        setChanged();
        return gumboBowl;
    }

    public boolean isEmpty() {
        return soupLevel <= 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("SoupLevel", soupLevel);
        tag.putInt("Nutrition", nutrition);
        tag.putFloat("Saturation", saturation);
        tag.putString("Ingredients", String.join(",", ingredientNames));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        soupLevel = tag.getInt("SoupLevel");
        nutrition = tag.getInt("Nutrition");
        saturation = tag.getFloat("Saturation");
        ingredientNames.clear();
        String ingredientsStr = tag.getString("Ingredients");
        if (!ingredientsStr.isEmpty()) {
            ingredientNames.addAll(Arrays.asList(ingredientsStr.split(",")));
        }
    }
}