package com.cutemouse.hello_mod.Blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class IronBucketBlock extends Block {
    //朝向属性
    private static final DirectionProperty FACING = BlockStateProperties.FACING;
    //开闭属性
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public IronBucketBlock(Properties properties) {
        super(properties);
        //注册默认状态
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(OPEN,false));
    }

}
