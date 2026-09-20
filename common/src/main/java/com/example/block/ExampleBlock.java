package com.example.block;

import com.example.block.entity.ExampleBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class ExampleBlock extends BaseEntityBlock  {

    private static final MapCodec<ExampleBlock> CODEC = simpleCodec(ExampleBlock::new);


    public ExampleBlock()
    {
        this(
            BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(0.2f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
            .noOcclusion()
        );
    }

	public ExampleBlock(Block.Properties prop)
    {
		super(prop);
	}


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) 
    {
        System.out.println("useItemOn " + stack);
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) 
    {
        
        System.out.println("useWithoutItem " + world + " " + pos);
        if (world.isClientSide) 
        {
            return InteractionResult.SUCCESS;
        } 
        else 
        {
            player.openMenu(state.getMenuProvider(world, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) 
    {
        
        System.out.printf("onRemove state: %s, newState: %s, moved:%s", state, newState, moved);

        //破壊後が同じブロックである場合、何もしない
        if (state.getBlock() == newState.getBlock())
        {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ExampleBlockEntity implBlockEntity) 
        {
            //中身をドロップする
            implBlockEntity.dropContainer();

            //周囲の赤石信号を更新
            world.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) 
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) 
    {
        return 7;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
    {
        return new ExampleBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() 
    {
        return ExampleBlock.CODEC;
    }
}
