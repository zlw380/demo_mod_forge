package com.cutemouse.hello_mod.Items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicMirror extends Item {

    public MagicMirror(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        //如果是在客户端触发该方法直接返回
        if(level.isClientSide()){
            return super.use(level, player, useHand);
        }

        //获取服务端主世界对象
        ServerLevel serverLevel = level.getServer().getLevel(Level.OVERWORLD);
        //获取服务端玩家对象
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer) player : null;
        //获取玩家重生点坐标
        BlockPos respawnPos = serverPlayer.getRespawnPosition();

        if (serverPlayer != null && serverLevel != null){
            //将玩家传送回重生点
            player.teleportTo(respawnPos.getX(),respawnPos.getY(),respawnPos.getZ());
        }

        //父类Item的use方法中，若手中使用物品不是食物，则返回return InteractionResultHolder.pass(player.getItemInHand(useHand));
        return InteractionResultHolder.success(player.getItemInHand(useHand));
    }
}
