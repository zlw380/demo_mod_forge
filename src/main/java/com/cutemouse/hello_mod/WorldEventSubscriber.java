package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//世界事件处理类
@Mod.EventBusSubscriber
public class WorldEventSubscriber {

    //使用工具修改方块事件
    @SubscribeEvent
    public static void blockToolInteractSub(BlockEvent.BlockToolModificationEvent event){

        Player player = event.getPlayer();

        assert player != null;
        if (player.level.isClientSide()){
            player.sendMessage(new TextComponent(
                            "玩家" + player.getDisplayName().getString() +
                                    "使用" + event.getHeldItemStack() + "工具做了" +
                                    event.getToolAction().name() + "行为，交互的方块最终状态是" +
                                    event.getFinalState()),
                    Util.NIL_UUID);
        }
    }

    //破坏方块事件
    @SubscribeEvent
    public static void breakBlockSub(BlockEvent.BreakEvent event){

        Player player = event.getPlayer();

        player.sendMessage(new TextComponent(
                        "玩家" + player.getDisplayName().getString() + "破坏了方块，获得了" +
                        event.getExpToDrop() + "点经验。" + "被破坏的方块状态：" + event.getState()),
                Util.NIL_UUID);

        //System.out.println("被破坏的方块状态：" + event.getState());
    }

    //实体放置方块事件
    @SubscribeEvent
    public static void entityPlaceBlockSub(BlockEvent.EntityPlaceEvent event){

        if (event.getEntity() instanceof Player){

            Player player = (Player) event.getEntity();
            player.sendMessage(new TextComponent("玩家" + player.getDisplayName().getString() +
                    "放置了方块。放置快照信息：" + event.getBlockSnapshot() +
                    "。放置的方块状态：" + event.getPlacedBlock() +
                    "。是对着" + event.getPlacedAgainst() + "方块放置的。"
            ),Util.NIL_UUID);
        }
    }
}
