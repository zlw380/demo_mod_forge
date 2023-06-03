package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//实体事件处理类
@Mod.EventBusSubscriber
public class EntityEventSubscriber {

    protected static boolean playerEnterWorld = false;

    //实体被创建事件
    //玩家进入世界、丢弃物品、使用刷怪蛋、加载生物等均为实体生成事件
    @SubscribeEvent
    public static void entityConstructingSub(EntityEvent.EntityConstructing event){

        if (!event.getEntity().level.isClientSide()){

            //System.out.println("在服务端触发了实体生成事件。");
            if (event.getEntity() instanceof Player){

                playerEnterWorld = true;
                //在玩家加入世界之前会先生成玩家实体，再生成在玩家附近的其他生物实体。
                //但如果在生成玩家实体时调用event.getEntity获取玩家信息，会因为获取不到玩家名称而抛出空指针异常使玩家无法加入世界。
                //System.out.println("在服务端生成的该实体是一位玩家。");
            }else if (playerEnterWorld){
                //System.out.println("在服务端生成的该实体不是一位玩家，信息为：" + event.getEntity() + "。");
            }

            //加载世界时无法获取实体对象信息，会发生错误
            //System.out.println("在服务端触发了实体生成事件，实体信息为" + event.getEntity() + "。");
        }else {

            //System.out.println("在客户端触发了实体生成事件。");
            if (event.getEntity() instanceof Player){

                playerEnterWorld = true;
                //System.out.println("在客户端生成的该实体是一位玩家。");
            }else if (playerEnterWorld){

                //System.out.println("在客户端生成的该实体不是一位玩家，信息为：" + event.getEntity() + "。");
            }
        }
    }
    //丢弃手中物品时捕获的实体信息：
    //ItemEntity['空气'/17, l='ServerLevel[新的世界]', x=0.00, y=0.00, z=0.00]
    //不管丢弃什么物品，物品实体名都是空气，坐标均为0。说明该实体还没有创建完成。
    //因此可知EntityConstructing实体生成事件发生在实体开始创建的时候。
    //当物品被吸回来时，客户端会再创建一个实体。

    //实体移动到另一个区块
    @SubscribeEvent
    public static void enteringSectionSub(EntityEvent.EnteringSection event){

        String moveDirection;

        //System.out.println("移动到另一区块的实体信息：" + event.getEntity());
        //移动到另一区块的实体信息：Panda['熊猫'/30, l='ServerLevel[新的世界]', x=282.15, y=-60.00, z=-32.01]
        //移动到另一区块的实体信息：Panda['熊猫'/30, l='ClientLevel', x=282.15, y=-60.00, z=-32.01]

        if (event.getEntity() instanceof Player && event.getEntity().level.isClientSide()){

            Player player = (Player) event.getEntity();

            boolean didChunkChange = event.didChunkChange();

            if (didChunkChange){
                moveDirection = "水平";
            }else{
                moveDirection = "垂直";
            }

            /*System.out.println("客户端触发玩家" + player.getDisplayName().getString() + "移动到另一区块，新区块的坐标为" + event.getNewPos());
            player.sendMessage(new TextComponent("客户端触发玩家" +
                    player.getDisplayName().getString() +
                    "移动到另一区块，新区块的坐标为" +
                    event.getNewPos() +
                    "此时玩家" + moveDirection + "移动。"
            ), Util.NIL_UUID);*/
        }
    }

    //实体加入世界事件
    //该事件触发时实体已经创建完成，因此此时实体的属性是完整的，不会像实体生成事件时出现实体名全是空气的现象。
    //同时也可以获取实体所在世界了
    @SubscribeEvent
    public static void entityJoinWorldSub(EntityJoinWorldEvent event){

        String loadedFromDisk;

        //仅在服务端有效。在客户端，该方法总是会返回false。
        if (event.loadedFromDisk()){
            loadedFromDisk = "实体原本已存在，从硬盘中加载。";
        }else {
            loadedFromDisk = "实体是新创建的。";
        }

        if (event.getEntity() instanceof Player){

            event.getEntity().sendMessage(new TextComponent("一个名为" +
                    event.getEntity().getDisplayName().getString() + "的玩家实体加入了世界！" +
                    "该实体所处的世界是" + event.getWorld() + "。" +
                    loadedFromDisk),Util.NIL_UUID);
        }

        System.out.println("一个名为" + event.getEntity().getDisplayName().getString() + "的实体加入了世界！" +
                "该实体所处的世界是" + event.getWorld() + "。" +
                loadedFromDisk);
    }
}


