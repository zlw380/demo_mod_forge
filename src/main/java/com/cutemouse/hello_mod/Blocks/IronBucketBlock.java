package com.cutemouse.hello_mod.Blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class IronBucketBlock extends Block {
    //朝向属性
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    //开闭属性
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public IronBucketBlock(Properties properties) {
        super(properties);

        //StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        //this.createBlockStateDefinition(builder);

        //注册默认状态
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(OPEN,false));
    }

    //由构造方法调用，创建方块状态定义
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        //将定义的方块朝向与开闭状态添加到方块状态建造器（建造者）的状态属性（properties）Map集合中
        builder.add(FACING,OPEN);
    }

    //获取放置时方块状态
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //默认朝向与玩家视线相对的方向放置
        return this.defaultBlockState().setValue(FACING,context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {

        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {

        return state.setValue(FACING,rotation.rotate(state.getValue(FACING)));
    }
}
