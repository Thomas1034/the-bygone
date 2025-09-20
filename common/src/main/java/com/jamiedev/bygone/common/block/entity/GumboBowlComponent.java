package com.jamiedev.bygone.common.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record GumboBowlComponent(int nutrition, float saturation, List<String> ingredients) {
    public static final Codec<GumboBowlComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("nutrition").forGetter(GumboBowlComponent::nutrition),
            Codec.FLOAT.fieldOf("saturation").forGetter(GumboBowlComponent::saturation),
            Codec.STRING.listOf().fieldOf("ingredients").forGetter(GumboBowlComponent::ingredients)
    ).apply(instance, GumboBowlComponent::new));

    static StreamCodec CODEC1;
}