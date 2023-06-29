package com.cutemouse.hello_mod.Blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class AzurLaneBlock extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.FACING;

    public AzurLaneBlock(Properties properties) {

        super(properties);

        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        //return this.defaultBlockState().setValue(FACING,context.getNearestLookingDirection().getOpposite());
        return this.defaultBlockState();
    }

    /*放置方块时，如不专门设置方块朝向，只按照默认状态放置，就会使用注册方块默认状态时设置的值（Direction.NORTH）来确定方块朝向。
    * 如专门用setValue方法设置了方块朝向，就会使用设定值覆盖掉默认值。*/
}
