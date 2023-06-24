package com.cutemouse.hello_mod.Items;

import com.cutemouse.hello_mod.Events.RegistryEvents;
import com.cutemouse.hello_mod.List.FoodList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

//定义物品Soup，汤
public class Soup extends Item {

    public Soup(){
        //利用父类Item的构造方法来实例化对象
        //Properties类为父类Item的静态内部类，用来构建Item及其子类的实例。

        //Properties类的tab方法用来设置物品的分类
        //这里的CreativeModeTab类参数就表示该物品会在哪个分类下显示，这里的TAB_MATERIALS就是杂项分类（显示为岩浆桶）。
        super(new Properties().tab(RegistryEvents.HELLO_MOD_ITEM).food(FoodList.LATTE));
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    /*单独创建每种食品类的好处就是可以方便地重写父类Item中的方法以修改进食时的动画与声音等。*/
}
