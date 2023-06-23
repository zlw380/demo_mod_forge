package com.cutemouse.hello_mod.Events;

import com.cutemouse.hello_mod.List.BlockList;
import com.cutemouse.hello_mod.List.FoodList;
import com.cutemouse.hello_mod.List.ItemList;
import com.cutemouse.hello_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.Location;
import java.util.Objects;

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
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FoodList.BEEF_BURGER).stacksTo(4)).setRegistryName(location("beef_burger")),
                ItemList.latte =
                        new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FoodList.LATTE).stacksTo(8)).setRegistryName(location("latte")),
                ItemList.laffeyBlock =
                        new BlockItem(BlockList.laffeyBlock,new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS))
                                .setRegistryName(Objects.requireNonNull(BlockList.laffeyBlock.getRegistryName()))
        );
        //这里的注册名有正则表达式约束，不允许使用大写英文字母，仅允许使用小写字母a-z，数字0-9，字符/._-。
        //Exception message: net.minecraft.ResourceLocationException: Non [a-z0-9/._-] character in path of location: hello_mod:vegetableSoup
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){

        event.getRegistry().registerAll(
                BlockList.laffeyBlock =
                        new Block(BlockBehaviour.Properties.of(Material.STONE,MaterialColor.STONE)
                        .strength(0.5F).sound(SoundType.STONE)).setRegistryName(location("laffey_block"))
        );

        /*
        * public static final Block DIRT =
        *   register("dirt", new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT)
        *   .strength(0.5F).sound(SoundType.GRAVEL)));
        * 沙子的方块定义
        *
        * public static final Block STONE =
        *   register("stone", new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).
        *   requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        * 以上是石头的方块定义
        *
        * public static final Block IRON_BLOCK =
        *   register("iron_block", new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).
        *   requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
        *
        * 以上是铁块的方块定义，可见铁块被设置为需要正确的工具进行采集并掉落，自身的破坏时间为5.0F，抗爆能力为6.0F，会发出金属音。
        *
        * 在BlockBehaviour.Properties中调用requiresCorrectToolForDrops()方法会使方块在被破坏时触发执行BreakEvent类对象中的
        * ForgeHooks.isCorrectToolForDrops(state, player)方法，两个参数分别提供方块状态与玩家信息，判断玩家是否可以成功采集此方块。
        *
        * 执行链：
        * 1.ForgeHooks.isCorrectToolForDrops(state, player)中，若方块的requiresCorrectToolForDrops属性为false，则默认可以成功采集方块；
        * 若方块的requiresCorrectToolForDrops属性为true，则调用玩家对象的hasCorrectToolForDrops(state)方法判断是否可以成功采集方块并返回布尔值。
        *
        * 2.hasCorrectToolForDrops(state)方法中调用Player对象中的
        * doPlayerHarvestCheck(this,
        *                      p_36299_,
        *                      !p_36299_.requiresCorrectToolForDrops() || this.inventory.getSelected().isCorrectToolForDrops(p_36299_))方法。
        * 参数分别为：（1）this代表玩家对象本身；（2）p_36299_为方块状态；
        *           （3）一个布尔值，决定方块是否能够被成功采集。如果该参数为true，则在此次BreakEvent事件中方块能够被成功采集。
        * 因此，根据该实参的表达式可以看出，当方块的requiresCorrectToolForDrops属性为false
        * 或 调用isCorrectToolForDrops(p_36299_)方法的返回值为true时，实参值即为true，意味着方块可以被成功采集。
        *
        * 3.执行isCorrectToolForDrops(p_36299_)方法，该方法中又调用了IForgeItem接口的实现类Item的子类DiggerItem中的
        * isCorrectToolForDrops(ItemStack stack, BlockState state)方法，参数为玩家手中持有物品的物品栈与要被采集方块的方块状态，
        * 以判断玩家手中的物品的挖掘等级是否能够成功采集此方块。
        * Params:
        *   stack – The itemstack used to harvest the block
        *   state – The block trying to harvest
        *   Returns:
        *   true if the stack can harvest the block
        *
        * 注：ItemStack.isCorrectToolForDrops(BlockState p_41736_)中的this.getItem()返回值应为一个Item的子类DiggerItem类的对象，
        * 否则调用isCorrectToolForDrops(ItemStack stack, BlockState state)方法时就会默认调用IForgeItem接口的同名同参数列表方法。
        * */
    }

    private static ResourceLocation location(String name) {

        return new ResourceLocation(MOD_ID,name);
    }
}
