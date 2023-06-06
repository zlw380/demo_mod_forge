package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.*;
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

            /*event.getEntity().sendMessage(new TextComponent("一个名为" +
                    event.getEntity().getDisplayName().getString() + "的玩家实体加入了世界！" +
                    "该实体所处的世界是" + event.getWorld() + "。" +
                    loadedFromDisk),Util.NIL_UUID);*/
        }

        /*System.out.println("一个名为" + event.getEntity().getDisplayName().getString() + "的实体加入了世界！" +
                "该实体所处的世界是" + event.getWorld() + "。" +
                loadedFromDisk);*/
    }
    //服务端渲染距离一般要比客户端渲染距离长，所以有时候实体加入世界事件只会在服务端触发，而等到该实体进入客户端渲染范围时才会在客户端触发。

    //Mob是LivingEntity的一个子类，因此Mob是一类生物实体。
    //EntityMobGriefingEvent是当Mob类实体进行搞破坏一类的行为时触发的事件
    @SubscribeEvent
    public static void entityMobGriefingSub(EntityMobGriefingEvent event){

        //非玩家生物踩平耕地、村民种植和收割作物、羊吃草、狐狸吃浆果、兔子偷袭胡萝卜作物
        //雪傀儡生成雪片、凋零破坏方块、苦力怕爆炸、末影人放置或搬走方块
        //唤魔者发出呜噜噜声音并将蓝色绵羊变成红色、劫掠兽破坏方块
        //蠹虫钻进石头、蠹虫召唤同伴、猪灵捡起物品、任何弹射物如火球
        //烈焰弹破坏或产生方块、非玩家生物践踏海龟蛋
        //System.out.println("触发了MobGriefing事件，" + event.getEntity());

    }

    //实体骑乘事件
    @SubscribeEvent
    public static void entityMountSub(EntityMountEvent event){

        String isMounting;

        if (event.isMounting()){
            isMounting = "骑了上去。";
        }else {
            isMounting = "跳了下来。";
        }

        //System.out.println("触发了骑乘事件：" + event.getEntity());
        if (event.getEntity() instanceof Player && event.getWorldObj().isClientSide()){

            event.getEntity().sendMessage(new TextComponent("玩家" + event.getEntity().getDisplayName().getString() +
                    "进行了骑乘操作。他骑的是" + event.getEntityBeingMounted().getDisplayName().getString() +
                    "。他" + isMounting),Util.NIL_UUID);
        }
    }

    //实体传送事件
    //写在事件处理方法中的传送行为不会触发传送事件
    //不同的传送方式（末影珍珠、指令）对应的内部类也不同
    @SubscribeEvent
    public static void entityTeleportSub(EntityTeleportEvent event){

        //System.out.println("传送事件对象对应的类是：" + event.getClass().getName() + EntityTeleportEvent.EnderPearl.class.getName());

        String teleportClass;

        if (event.getClass().equals(EntityTeleportEvent.EnderPearl.class)){
            teleportClass = "末影珍珠";
        }else if (event.getClass().equals(EntityTeleportEvent.TeleportCommand.class)){
            teleportClass = "传送指令";
        }else {
            teleportClass = "其他";
        }

        if (event.getEntity() instanceof Player){

            event.getEntity().sendMessage(new TextComponent(
                    "玩家" + event.getEntity().getDisplayName().getString() +
                            "通过" + teleportClass + "的方式" +
                            "从" + event.getPrev() + "传送到了" + event.getTarget()
            ),Util.NIL_UUID);
        }
        //玩家Dev通过末影珍珠的方式从(8.930171646179426, -60.0, -2.2741389591475047)传送到了(6.414525276261045, -59.506929962978646, -1.0172242406623313)
        //玩家Dev通过传送指令的方式从(6.414525276261045, -60.0, -1.0172242406623313)传送到了(2.45, -59.99, 1.0)
    }

    //进入维度事件
    @SubscribeEvent
    public static void entityTravelToDimensionSub(EntityTravelToDimensionEvent event){

        String world;

        ResourceKey<Level> dimension = event.getDimension();
        //System.out.println("进入的维度为：" + dimension);

        if (dimension == Level.OVERWORLD){
            world = "主世界";
        }else if (dimension == Level.NETHER){
            world = "下界";
        }else if (dimension == Level.END){
            world = "末地";
        }else {
            world = "其他维度";
        }

        if (event.getEntity() instanceof Player){

            event.getEntity().sendMessage(new TextComponent(
                    "玩家" + event.getEntity().getDisplayName().getString() +
                            "进入了另一维度。进入的是" + world + "。"
            ),Util.NIL_UUID);
        }
    }

    //实体发出声音事件
    @SubscribeEvent
    public static void entitySoundSub(PlaySoundAtEntityEvent event){

        //System.out.println("实体发出声音信息：" + event.getSound().getLocation() + "|" + event.getCategory());
    }
}


