package com.cutemouse.hello_mod;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        /*System.out.println("受到伤害的实体：" + event.getEntityLiving().getDisplayName().getString() +
                "。伤害源：" + event.getSource() +
                "。伤害数值：" + event.getAmount());*/
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

        /*System.out.println("受到真实伤害的实体：" + event.getEntityLiving().getDisplayName().getString() +
                "。真实伤害源：" + event.getSource() +
                "。实际伤害值：" + event.getAmount());*/

        if (event.getEntityLiving() instanceof Zombie && event.getSource().getEntity() instanceof Player){
            event.setAmount(99999);
            System.out.println("玩家对僵尸修改过的真伤值：" + event.getAmount());
        }
        //如果玩家对僵尸造成伤害，一刀99999。
    }

    //实体死亡事件
    @SubscribeEvent
    public static void livingDeathSub(LivingDeathEvent event){

        if (event.getEntityLiving() instanceof Player){
            //在不设置血量的情况下，即使取消死亡事件，角色依然会死亡。
            //同时不会发送死亡信息，背包内物品不会掉落到世界，但依然会清空背包。
            event.setCanceled(true);
            //设置实体血量为20点，这样角色就不会死亡，背包也不会被清空。
            event.getEntityLiving().setHealth(20);
        }
    }

    //生物实体破坏方块事件
    @SubscribeEvent
    public static void livingDestroyBlockSub(LivingDestroyBlockEvent event){

        /*System.out.println("生物实体" + event.getEntityLiving().getDisplayName().getString() +
                "。破坏了位于：" + event.getPos() +
                "的方块：" + event.getState());*/
        //玩家破坏方块时不会触发
        if (event.getEntityLiving() instanceof Player){
            System.out.println("本次生物实体破坏方块事件由玩家触发。");
        }
        //从该事件的类的注释来看，只有末影龙、凋零、僵尸可以触发此事件。
    }

    //实体掉落物品事件
    @SubscribeEvent
    public static void livingDropsSub(LivingDropsEvent event){

        /*System.out.println("掉落物品的实体：" + event.getEntityLiving() +
                "。使其掉落物品的实体是：" + event.getSource() +
                "。\n掉落的物品集合：" + event.getDrops() +
                "。武器抢夺附魔的等级：" + event.getLootingLevel() +
                "。是否因受攻击掉落：" + event.isRecentlyHit());*/
        //掉落物品的实体：Spider['蜘蛛'/26, l='ServerLevel[新的世界]', x=57.97, y=-60.00, z=5.97]。
        //使其掉落物品的实体是：EntityDamageSource (ServerPlayer['Dev'/128, l='ServerLevel[新的世界]', x=55.20, y=-60.00, z=4.75])。
        //掉落的物品集合：[ItemEntity['线'/296, l='ServerLevel[新的世界]', x=57.97, y=-60.00, z=5.97], ItemEntity['蜘蛛眼'/297, l='ServerLevel[新的世界]', x=57.97, y=-60.00, z=5.97]]。
        //武器抢夺附魔的等级：3。是否因受攻击掉落：true
    }

    //实体使用物品事件
    //会在实体长时间使用物品时触发，例如吃食物，喝药水，喝牛奶，拉弓等。
    //非玩家实体同样会触发此事件，比如小白拉弓。
    /*@SubscribeEvent
    public static void livingEntityUseItemSub(LivingEntityUseItemEvent event){

        if (event.getEntityLiving() instanceof Player) {
            System.out.println("玩家" + event.getEntityLiving().getDisplayName().getString() +
                    "使用了" + event.getItem() +
                    "。剩余时间单位：" + event.getDuration() +
                    "。触发端：" + (event.getEntityLiving().level.isClientSide()?"客户端":"服务端"));
        }
    }*/
    /*该事件会在使用物品时的每一tick同时在服务端与客户端触发
    * 不提倡直接使用此父类
    * 该父类的构造方法用private修饰，因此实际上都是先实例化其内部子类再调用父类的构造方法
    * 再专门写父类的事件处理方法就多此一举了*/

    /*实体使用物品事件类的四个子类：
    * Start：开始使用物品时触发
    * Tick：使用物品时的每一tick触发
    * Stop：中止使用物品时触发
    * Finish：物品使用完成时触发*/
    @SubscribeEvent
    public static void livingEntityUseItemFinishSub(LivingEntityUseItemEvent.Finish event){

        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                System.out.println("玩家使用" + event.getItem() + "后剩下了" + event.getResultStack() +
                        "。剩余时间单位：" + event.getDuration());
                //getResultStack()方法是获取玩家使用物品后手中剩下的物品栈
                //如果是创造模式，玩家手中的物品不会被消耗掉，就还是原来的物品栈
            }
        }
        if (event.getItem().getItem().toString().equals("apple")){
            //吃完一个苹果后手里会出现64个附魔金苹果
            event.setResultStack(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE,64));
            System.out.println("吃完一个苹果之后，手中的物品栈会变为：" + event.getResultStack());
        }
    }

    @SubscribeEvent
    public static void livingEntityUseItemStartSub(LivingEntityUseItemEvent.Start event){

        String drinkItem = event.getItem().getItem().toString();
        //一个getItem获得物品栈ItemStack，第二个getItem获得物品Item。
        //System.out.println("drinkItem:" + drinkItem);
        //drinkItem:milk_bucket

        //若喝牛奶，则时间缩短为8ticks
        if (drinkItem.equals("milk_bucket")){
            event.setDuration(8);
        }
        //拉弓时，默认的使用时间非常长。
    }

    //实体切换装备事件
    @SubscribeEvent
    public static void livingEquipmentChangeSub(LivingEquipmentChangeEvent event){

        System.out.println("实体" + event.getEntityLiving() +
                "切换了" + event.getSlot() + "部位的装备。从" +
                event.getFrom() + "切换到了" + event.getTo());
    }

    //实体掉落经验事件
    @SubscribeEvent
    public static void livingExpDropSub(LivingExperienceDropEvent event){

        //如果打的是僵尸，掉落999经验。
        if (event.getEntityLiving() instanceof Zombie){
            event.setDroppedExperience(999);
        }

        if (event.getAttackingPlayer() != null){

            event.getAttackingPlayer().sendMessage(new TextComponent("玩家" +
                    event.getAttackingPlayer().getDisplayName().getString() +
                    "攻击了" + event.getEntityLiving().getDisplayName().getString() +
                    "。掉落了" + event.getDroppedExperience() + "经验" +
                    "。原本应掉落的经验是" + event.getOriginalExperience()),Util.NIL_UUID);
        }
        /*System.out.println("实体" + event.getEntityLiving() +
                "掉落了" + event.getDroppedExperience() + "点经验。");*/
    }

    //实体摔落事件
    @SubscribeEvent
    public static void livingFallSub(LivingFallEvent event){

        //实体美西螈摔了下去。摔落距离是23.435236。伤害系数为1.0
        if (event.getEntityLiving().getDisplayName().getString().equals("美西螈")){
            event.setDistance(5);
        }
        //实体美西螈摔了下去。摔落距离是5.0。伤害系数为1.0

        /*System.out.println("实体" + event.getEntityLiving().getDisplayName().getString() +
                "摔了下去。摔落距离是" + event.getDistance() +
                "。伤害系数为" + event.getDamageMultiplier());*/
    }

}
