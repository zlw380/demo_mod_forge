package com.cutemouse.hello_mod.Blocks.entity;

import com.cutemouse.hello_mod.Blocks.BlockEntityRegistry;
import com.cutemouse.hello_mod.Blocks.IronBucketBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IronBucketBlockEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(27,ItemStack.EMPTY);

    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        //抽象类ContainerOpenersCounter的匿名内部子类，继承并覆盖抽象类中的方法
        //打开容器时
        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {

            level.setBlock(blockPos,blockState.setValue(IronBucketBlock.OPEN,true),3);
        }

        //关闭容器时
        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {

            level.setBlock(blockPos,blockState.setValue(IronBucketBlock.OPEN,false),3);
        }

        //开闭状态转换时
        @Override
        protected void openerCountChanged(Level p_155463_, BlockPos p_155464_, BlockState p_155465_, int p_155466_, int p_155467_) {

        }

        //判断开关容器的玩家是否为该容器的主人？
        @Override
        protected boolean isOwnContainer(Player player) {

            if (player.containerMenu instanceof ChestMenu){

                Container container = ((ChestMenu) player.containerMenu).getContainer();

                return container == IronBucketBlockEntity.this;
            }else {
                return false;
            }
        }
    };

    public IronBucketBlockEntity(BlockPos p_155630_, BlockState p_155631_) {
        super(BlockEntityRegistry.IRON_BUCKET_BLOCK_ENTITY.get(),p_155630_, p_155631_);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("block.hello_mod.iron_bucket");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory inventory) {

        return ChestMenu.threeRows(p_58627_, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    //保存存储在容器中的物品
    @Override
    protected void saveAdditional(CompoundTag compoundTag) {

        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)){
            ContainerHelper.saveAllItems(compoundTag,this.items);
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {

        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(),ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)){
            ContainerHelper.loadAllItems(compoundTag,this.items);
        }
    }

    public void recheckOpen(){

        if (this.remove){
            this.openersCounter.recheckOpeners(this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player){

        if (this.remove && !player.isSpectator()){
            this.openersCounter.decrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    public void startOpen(Player player){

        if (this.remove && !player.isSpectator()){
            this.openersCounter.incrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    void updateBlockState(BlockState p_58607_, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), p_58607_.setValue(IronBucketBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
    }
}
