package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.common.block.entity.GumboBowlComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GumboIngredientRegistry
{

    public static void addIngredients(BiConsumer<Item, Consumer<BiConsumer<DataComponentType<GumboBowlComponent>, GumboBowlComponent>>> event) {
        event.accept(
                Items.ROTTEN_FLESH, builder -> builder.accept(
                        BGDataComponentTypes.GUMBO_BOWL,
                        new GumboBowlComponent(1, 1, List.of(Items.SPIDER_EYE.toString()))
                )
        );

    }
}
