package com.cutemouse.hello_mod.Items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

//定义物品Soup，汤
public class Soup extends Item {

    public Soup(){
        //利用父类Item的构造方法来实例化对象
        //Properties类为父类Item的静态内部类，用来构建Item及其子类的实例。

        //Properties类的tab方法用来设置物品的分类
        //这里的CreativeModeTab类参数就表示该物品会在哪个分类下显示，这里的TAB_MATERIALS就是杂项分类（显示为岩浆桶）。
        super(new Properties().tab(CreativeModeTab.TAB_MATERIALS));
    }
}
