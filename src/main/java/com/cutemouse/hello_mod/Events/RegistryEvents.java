package com.cutemouse.hello_mod.Events;

import com.cutemouse.hello_mod.List.FoodList;
import com.cutemouse.hello_mod.List.ItemList;
import com.cutemouse.hello_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.Location;

//把所有的物品、方块、流体等全部在该类内进行注册
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    //把主类中定义的LOGGER赋给这里的LOGGER
    public static final Logger LOGGER = Main.LOGGER;

    public static final String MOD_ID = Main.MOD_ID;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){

        //统一注册物品列表中的物品
        //针对每一个物品创建新的物品对象并将其赋给物品列表中对应的引用
        //同时可以设置物品的一些属性，比如最大堆叠数量等。

        //这种注册物品的方法与之前为每个物品单独创建实体类并注册到DeferredRegister类常量ITEMS中的方法是等效的。
        event.getRegistry().registerAll(
                ItemList.vegetableSoup =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(16)).setRegistryName(location("vegetable_soup")),
                ItemList.fruitCake =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(8)).setRegistryName(location("fruit_cake")),
                ItemList.fruitPizza =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(4)).setRegistryName(location("fruit_pizza")),
                ItemList.beefBurger =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FoodList.BEEF_BURGER).stacksTo(4)).setRegistryName(location("beef_burger"))
        );
        //这里的注册名有正则表达式约束，不允许使用大写英文字母，仅允许使用小写字母a-z，数字0-9，字符/._-。
        //Exception message: net.minecraft.ResourceLocationException: Non [a-z0-9/._-] character in path of location: hello_mod:vegetableSoup
    }

    private static ResourceLocation location(String name) {

        return new ResourceLocation(MOD_ID,name);
    }
}
