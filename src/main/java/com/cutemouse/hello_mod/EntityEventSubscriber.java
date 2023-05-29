package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//实体事件处理类
@Mod.EventBusSubscriber
public class EntityEventSubscriber {

    //实体被创建事件
    //玩家进入世界、丢弃物品、使用刷怪蛋、加载生物均为实体生成事件
    @SubscribeEvent
    public static void entityConstructingSub(EntityEvent.EntityConstructing event){

        if (!event.getEntity().level.isClientSide()){

            System.out.println("在服务端触发了实体生成事件。");
        }

        //event.getEntity().sendMessage(new TextComponent("生成的实体信息：" + event.getEntity()), Util.NIL_UUID);
    }
}
