package com.cutemouse.hello_mod.Events;

import com.cutemouse.hello_mod.List.ItemList;
import com.cutemouse.hello_mod.Main;
import net.minecraft.resources.ResourceLocation;
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
        //针对每一个物品创建新的物品对象并赋给物品列表中对应的引用
        event.getRegistry().registerAll(
                ItemList.vegetableSoup =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(16)).setRegistryName(location("vegetableSoup")),
                ItemList.fruitCake =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(8)).setRegistryName(location("fruitCake")),
                ItemList.fruitPizza =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(4)).setRegistryName(location("fruitPizza"))
        );
    }

    private static ResourceLocation location(String name) {

        return new ResourceLocation(MOD_ID,name);
    }
}
