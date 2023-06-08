package com.cutemouse.hello_mod;

import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//物品事件处理类
@Mod.EventBusSubscriber
public class ItemEventSubscriber {

    //物品生命周期结束事件
    @SubscribeEvent
    public static void ItemExpireSub(ItemExpireEvent event){

        System.out.println("触发事件的物品：" + event.getEntityItem() +
                "获取生命周期：" + event.getExtraLife());
/*
        当物品的生命周期达到其最大值时触发的事件。
        取消此事件将阻止物品被标记为dead，从而阻止其从世界上删除。
        如果取消此次事件，就将延长物品的生命周期，延长的时间与设置的extraLife相等。
*/
        //event.setExtraLife(100);
        //event.setCanceled(true);
        //使用游戏内指令修改物品的生命周期时，只可以选择某一个物品进行修改，并且仅会生效一次。
        // /data modify entity @e[type=minecraft:item,limit=1,sort=nearest] Lifespan set value 100
        //触发事件的物品ItemEntity['末影珍珠'/4, l='ServerLevel[新的世界]', x=-7.75, y=-60.00, z=3.67]获取生命周期6000
    }

    //物品被丢弃事件
    @SubscribeEvent
    public static void ItemTossSub(ItemTossEvent event){

        System.out.println("被丢弃的物品：" + event.getEntityItem() +
                "。丢弃物品的玩家：" + event.getPlayer().getDisplayName().getString() +
                "。获取事件实体：" + event.getEntity() +
                "。物品块：" + event.getEntityItem().getItem() +
                "。物品名：" + event.getEntityItem().getItem().getDisplayName().getString());
    }
}
