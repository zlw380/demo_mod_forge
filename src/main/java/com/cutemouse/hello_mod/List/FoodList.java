package com.cutemouse.hello_mod.List;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

//食物列表
public class FoodList {

    //利用FoodProperties的静态内部类Builder的对象创建一个食物（FoodProperties）对象
    public static final FoodProperties BEEF_BURGER =
            (new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F)
                    .effect((() -> new MobEffectInstance(MobEffects.REGENERATION,600,0)) ,1.0F)
                    .effect((() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE,600,0)) ,1.0F)
                    .alwaysEat().build();
}

/*public static final FoodProperties ENCHANTED_GOLDEN_APPLE = (
        new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F)
        .effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
        .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F)
        .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
        .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F)
        .alwaysEat().build();*/

/*
以上是附魔金苹果的定义，可见附魔金苹果除了提供饥饿值与饱和度之外，还提供了以下效果：
    1.生命恢复II，持续20秒（400ticks）
    2.抗性提升I，持续300秒（6000ticks）
    3.防火，持续300秒
    4.伤害吸收IV，持续120秒
    同时设置触发效果的概率全部为100%（1.0F）。
*/
