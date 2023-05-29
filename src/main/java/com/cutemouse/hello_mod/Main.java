package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/*
* 捕获一个事件：玩家进入世界
* 使得当有玩家进入世界时，可以执行某些代码
* */

//此注解能让forge识别这个类，并将该类作为模组的主类
@Mod("hello_mod")
//此注解表明该类是一个事件处理类，在这个类中可以写一些事件处理方法
@Mod.EventBusSubscriber
public class Main {

    //此注解标明该方法为事件处理方法
    @SubscribeEvent
    //暂时认为forge会通过扫描传入的参数类型来根据不同类型的事件调用不同的事件处理方法
    public static void playerJoinWorld(PlayerEvent.PlayerLoggedInEvent event){

        //Minecraft有两个逻辑端，服务端与客户端
        //有的事件只存在于服务端，有的事件只存在于客户端，有的事件两端皆会触发

        //获取触发事件的玩家
        Player eventPlayer = event.getPlayer();

        //获取此玩家所处的世界，Level为世界类的类名
        Level level = eventPlayer.level;

        //给玩家发送消息
        //sendMessage方法的第一个参数是消息的内容，第二个参数是uuid

        //第一个参数可直接（使用构造方法）new一个TextComponent对象，并在括号中给构造方法传入要显示的文本作为参数

        //要获取玩家名称，可调用玩家对象中的getDisplayName获取玩家的名称并转为字符串再进行拼接

        //要明确当前触发事件的逻辑端，可以使用level对象中的isClientSide方法
        //该方法返回一个布尔值，返回值为true则是客户端触发该事件，为false则为服务端触发事件
        //并使用三目运算符变为字符串
        eventPlayer.sendMessage(
                new TextComponent("Hello," + eventPlayer.getDisplayName().getString() + ".From " + (level.isClientSide()?"CLIENT":"SERVER") + "."),
                Util.NIL_UUID);
    }

    //事件处理方法，左键点击方块
    @SubscribeEvent
    public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock event){

        Player player = event.getPlayer();

        player.sendMessage(new TextComponent("LeftClickBlock is fired by " + player.getDisplayName().getString() + ".From " + (player.level.isClientSide()?"Client.":"Server.") +
                "hand = " + event.getHand()),
                Util.NIL_UUID);
        //PlayerInteractEvent下的hand属性带有private关键字，其内部类LeftClickBlock的对象无法直接调用。
    }

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event){

        Player player = event.getPlayer();

        /*player.sendMessage(new TextComponent("RightClickBlock is fired by " + player.getDisplayName().getString() + ".From " + (player.level.isClientSide()?"Client.":"Server.") +
                "hand = " + event.getHand()),
                Util.NIL_UUID);*/

        /*player.sendMessage(new TextComponent(player.level.isClientSide()?"CLIENT":"SERVER"),Util.NIL_UUID);
        return false;*/
    }
    //此处可加断点进行调试，根据player对象中的level属性得知事件是在哪一端触发
    //return false; 使程序不在断点处暂停
    //断点处出现问号说明该断点是一个条件断点
    //条件断点是一种拥有可设置条件属性，满足一定条件才触发的断点。

    /*
    * 右键放置方块时，触发两次事件处理方法。两次event对象的hand属性均为"MAIN_HAND"，即主手触发。但服务端与客户端各触发一次。
    * 右键空手点击方块时，触发四次事件处理方法。主手与副手各触发两次，同样分别由服务端与客户端触发。
    * */

    //当手中持有物品时才会触发该方法
    //当玩家手中物品为钻石剑时，右键传送到准星对准的位置
    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event){
        System.out.println("触发了手中物品右键点击的事件处理函数");
        //获取触发事件的玩家
        Player player = event.getPlayer();
        //游戏数据由服务端处理，但该事件客户端与服务端都会触发，因此需判断事件是否为服务端触发
        boolean clientSide = player.level.isClientSide();
        //如果是由服务端触发，进入分支
        if(!clientSide){
            //获取玩家右键时手中的物品格，一格物品就是一个ItemStack，包含物品名，物品数据，物品数量等属性
            ItemStack itemStack = event.getItemStack();
            System.out.println("手中物品格信息：" + itemStack);
            //返回物品格对应的物品
            Item item = itemStack.getItem();
            //如果该物品为钻石剑
            if (item == Items.DIAMOND_SWORD){
                //获取玩家准星对准的信息
                //public HitResult pick(double p_19908_, float p_19909_, boolean p_19910_){}
                //准星最大捕获范围为20，不包含流体。
                HitResult hitResult = player.pick(20, 0, false);
                //System.out.println("准星对准信息" + hitResult); //这里只会打印出对应的类以及对象在堆区的地址单元
                //获得准星对准的坐标。Vec3即为三维向量，该类的实例可用于存储一个三维坐标
                Vec3 hitResultLocation = hitResult.getLocation();
                //将玩家传送到对准坐标
                player.teleportTo(hitResultLocation.x,hitResultLocation.y,hitResultLocation.z);
            }
        }
        //当手中物品在副手时，也会执行同样的操作。
        //主副手上任意一只手有物品时，都会执行两次该事件处理方法。扫描一次主手，扫描一次副手。
    }
}

