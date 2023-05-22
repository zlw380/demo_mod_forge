package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
}
