package com.jamiedev.bygone.common.item;

import com.jamiedev.bygone.common.block.entity.GumboBowlComponent;
import com.jamiedev.bygone.core.registry.BGDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GumboBowlItem extends Item {
    public GumboBowlItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(BGDataComponentTypes.GUMBO_BOWL)) {
            GumboBowlComponent comp = stack.get(BGDataComponentTypes.GUMBO_BOWL);
            tooltipComponents.add(Component.translatable("item.bygone.gumbo_bowl.nutrition", comp.nutrition()));
            tooltipComponents.add(Component.translatable("item.bygone.gumbo_bowl.saturation", comp.saturation()));
            tooltipComponents.add(Component.translatable("item.bygone.gumbo_bowl.ingredients", String.join(", ", comp.ingredients())));
        } else {
            tooltipComponents.add(Component.translatable("item.bygone.gumbo_bowl.empty"));
        }    }


}