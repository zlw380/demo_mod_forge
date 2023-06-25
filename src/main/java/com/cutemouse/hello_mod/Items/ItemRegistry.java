package com.cutemouse.hello_mod.Items;

import com.cutemouse.hello_mod.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

//将每一个物品单独注册到DeferredRegister类常量ITEMS中，仅建议在添加少量物品时使用。
public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"hello_mod");

    //register方法的第二个参数是一个实现了Supplier接口的实现类的对象
    //因此在第二个参数中需要直接定义一个实现类，这可以通过匿名内部类实现，也可以通过lambda表达式或方法引用实现。
    //这里的lambda表达式相当于编写了Supplier接口中抽象方法get的实现。同时可以省略掉方法名get。
    //public static RegistryObject<Item> Soup = ITEMS.register("soup", ()->{return new Soup();});
    //进一步将lambda表达式简化为方法引用
    public static RegistryObject<Item> Soup = ITEMS.register("soup", com.cutemouse.hello_mod.Items.Soup::new);

    public static RegistryObject<Item> PINK_INGOT = ITEMS.register("pink_ingot",
            new Supplier<Item>() {
                @Override
                public Item get() {
                    return new Item(new Item.Properties().tab(Main.HELLO_MOD_BLOCK_TAB));
                }
            });
}

/*
接口实现类的简化路线：实现类->匿名内部类->lambda表达式->方法引用

使用匿名内部类可以简化掉接口的实现类，直接在创建一个接口对应的实例时把原本实现类的语句写在后面。
如：
public class InnerClassTest05 {
    public static void main(String[] args) {
        InnerClassTest05 innerClassTest05 = new InnerClassTest05();
        innerClassTest05.method1(new MyInterface() {
            public void add() {
                System.out.println("-------add------");
            }
        });
    }

    private void method1(MyInterface myInterface) {
        myInterface.add();
    }
}

interface MyInterface {
    public void add();
}

这里就省去了原本应该编写的实现类MyInterfaceImpl，直接省略掉类名并将类体放在了new MyInterface()后面的大括号内。
*/
