package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

//生物事件
@Mod.EventBusSubscriber
public class LivingEventSubscriber {

    //生物可见性事件
    @SubscribeEvent
    public static void LivingVisibilitySub(LivingEvent.LivingVisibilityEvent event){

        /*System.out.println("触发可见性事件的生物实体："+ event.getEntityLiving() +
                "。原可见性因数为：" + event.getVisibilityModifier() +
                "。尝试观测本生物实体的实体：" + event.getLookingEntity());*/
        //该事件会以很短的间隔被不断触发，玩家本身会被所有其他生物以很高的频率不断尝试观测。（如果是敌对生物观测到玩家的话就会开始攻击玩家）
        //玩家喝下隐身药水之后：可见性因数为：0.07000000104308128。

        if (event.getVisibilityModifier() > 0.08 && (event.getLookingEntity() instanceof Monster)){
            //System.out.println("本实体可见性大于0.08");
            event.modifyVisibility(0.0);
            //System.out.println("乘以系数0.07之后，可见性：" + event.getVisibilityModifier());
        }
        //event.modifyVisibility(mod)方法修改可见性后不能作用到全局作用域，仅在本对象中有效。
        //在针对怪物降低玩家可见度为0.07后，除非距离极近，否则怪物无法探测到玩家。
        //在针对怪物降低玩家可见度为0.0后，除非贴身，否则怪物无法探测到玩家。
    }

    //动物驯服事件
    @SubscribeEvent
    public static void AnimalTameSub(AnimalTameEvent event){

        if (event.getTamer() != null){

            event.getTamer().sendMessage(new TextComponent(
                    "玩家" + event.getTamer().getDisplayName().getString() +
                            "驯服了" + event.getAnimal().getDisplayName().getString()
            ), Util.NIL_UUID);
        }
    }

    //子实体产生事件
    @SubscribeEvent
    public static void BabyEntitySpawnSub(BabyEntitySpawnEvent event){

        if (event.getCausedByPlayer() != null){

            if (event.getParentA() instanceof Sheep){
                event.setChild(new Wolf(EntityType.WOLF,Objects.requireNonNull(event.getChild()).level));
                //event.setChild(EntityType.WOLF.create(event.getChild().level));
            }else if (event.getParentA() instanceof Wolf){
                event.setChild(EntityType.SHEEP.create(Objects.requireNonNull(event.getChild()).level));
            }else if (event.getParentA() instanceof Pig){
                event.setChild(EntityType.CHICKEN.create(Objects.requireNonNull(event.getChild()).level));
            }
            //让狼生小羊，羊生小狼，猪生小鸡

            event.getCausedByPlayer().sendMessage(new TextComponent(
                    "玩家" + event.getCausedByPlayer().getDisplayName().getString() +
                            "让" + event.getParentA() + "和" + event.getParentB() +
                            "进行了繁殖。生育了" + event.getChild()
            ),Util.NIL_UUID);
        }
    }

    //实体受到伤害事件，当实体被攻击的时候触发，被雪球砸这种不造成真正伤害的行为也会触发。
    @SubscribeEvent
    public static void LivingAttackSub(LivingAttackEvent event){

        System.out.println("受到伤害的实体：" + event.getEntityLiving().getDisplayName().getString() +
                "。伤害源：" + event.getSource() +
                "。伤害数值：" + event.getAmount());
        //public DamageSource getSource(){} 获取伤害源，返回的是一个DamageSource类型的对象。
        /*
        * 当玩家攻击其它生物实体时，会调用玩家对象中的LivingEntity.hurt(DamageSource, float)方法，
        * 同时经由ForgeHooks.onLivingAttack(LivingEntity, DamageSource, float)方法触发实体受到伤害事件，
        * 创建对应的LivingAttackEvent对象并给属性赋值。
        * */

        //此事件可以取消，直接让某个实体无敌。
        if (event.getEntityLiving() instanceof Sheep) {
            event.setCanceled(true);
            //当受到伤害的实体为羊时，让羊不受伤害，即无敌。
        }
    }
    /*
    Player.java
    livingentity.hurt(DamageSource.playerAttack(this), f3);
    玩家攻击其它生物实体，调用hurt方法，将当前的玩家对象作为参数传入hurt方法，作为伤害源

    LivingEntity.java
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
      if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(this, p_21016_, p_21017_)) return false;
      ...
    }
    hurt方法调用onLivingAttack方法，第一个参数为被攻击的生物实体，第二个参数为伤害源，即玩家本身，第三个参数为伤害值。

    DamageSource.java
    public static DamageSource playerAttack(Player p_19345_) {
      return new EntityDamageSource("player", p_19345_);
    }
    playerAttack方法中创建EntityDamageSource即伤害源对象并返回作为hurt方法的参数，Player类参数p_19345_即为当前玩家对象。

    ForgeHooks.java
    public static boolean onLivingAttack(LivingEntity entity, DamageSource src, float amount)
    {
        return entity instanceof Player || !MinecraftForge.EVENT_BUS.post(new LivingAttackEvent(entity, src, amount));
    }
    onLivingAttack方法中创建LivingAttackEvent即生物实体被攻击事件对象，并传入被攻击实体，伤害源，伤害值。
    */

    /*
    * public static final DamageSource IN_FIRE = (new DamageSource("inFire")).bypassArmor().setIsFire();
    * DamageSource类中有很多这种DamageSource类型的静态常量属性，这些属性都是DamageSource类的对象。
    * 它们会在项目启动时就作为DamageSource类的成员被实例化并放入方法区。
    * 它们均被final关键字修饰，
    * 因此这些属性的引用（如IN_FIRE）只能指向最初在方法区中被实例化的DamageSource类对象（如(new DamageSource("inFire")).bypassArmor().setIsFire()）。
    * 这样一来，其他任何一个类的任何一个方法中都可以随时访问获取这些DamageSource类型的常量作为伤害源的参数。
    *
    * 如CampfireBlock类中的entityInside(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_)方法中的
    * p_51272_.hurt(DamageSource.IN_FIRE, (float)this.fireDamage);
    * 就表示有实体踏入营火中的时候，会受到来自伤害源DamageSource.IN_FIRE的伤害。
    * */

    //实体受到真实伤害事件
    //当实体受到真实伤害时触发，因此不包括被雪球砸等不会造成真正伤害的事件。
    //本事件是在真实伤害作用到实体的前一刻触发。
    //此时的伤害值已经经过护甲、药品、BUFF等的补正。
    //并且此时对应的防护资源，如护甲耐久、额外的减伤耗材等已经被消耗掉了。
    @SubscribeEvent
    public static void livingDamageSub(LivingDamageEvent event){

        System.out.println("受到真实伤害的实体：" + event.getEntityLiving().getDisplayName().getString() +
                "。真实伤害源：" + event.getSource() +
                "。实际伤害值：" + event.getAmount());
    }
}
