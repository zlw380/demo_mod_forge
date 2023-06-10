package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//生物事件
@Mod.EventBusSubscriber
public class LivingEventSubscriber {

    //生物可见性事件
    @SubscribeEvent
    public static void LivingVisibilitySub(LivingEvent.LivingVisibilityEvent event){

        /*System.out.println("触发可见性事件的生物实体："+ event.getEntityLiving() +
                "。原可见性因数为：" + event.getVisibilityModifier() +
                "。尝试观测本生物实体的实体：" + event.getLookingEntity());*/
        //该事件会以很短的间隔被不断触发，玩家本身会被所有其他生物以很高的频率不断尝试观测。（如果是敌对生物观测到玩家的话就会开始攻击玩家）
        //玩家喝下隐身药水之后：可见性因数为：0.07000000104308128。

        if (event.getVisibilityModifier() > 0.08 && (event.getLookingEntity() instanceof Monster)){
            //System.out.println("本实体可见性大于0.08");
            event.modifyVisibility(0.0);
            //System.out.println("乘以系数0.07之后，可见性：" + event.getVisibilityModifier());
        }
        //event.modifyVisibility(mod)方法修改可见性后不能作用到全局作用域，仅在本对象中有效。
        //在针对怪物降低玩家可见度为0.07后，除非距离极近，否则怪物无法探测到玩家。
        //在针对怪物降低玩家可见度为0.0后，除非贴身，否则怪物无法探测到玩家。
    }

    //动物驯服事件
    @SubscribeEvent
    public static void AnimalTameSub(AnimalTameEvent event){

        if (event.getTamer() != null){

            event.getTamer().sendMessage(new TextComponent(
                    "玩家" + event.getTamer().getDisplayName().getString() +
                            "驯服了" + event.getAnimal().getDisplayName().getString()
            ), Util.NIL_UUID);
        }
    }
}
