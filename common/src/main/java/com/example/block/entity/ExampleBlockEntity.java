package com.example.block.entity;

import com.example.ExampleMod;
import com.example.screen.ExampleScreenHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleBlockEntity extends BlockEntity implements MenuProvider
{
    private final SimpleContainer inventory = new SimpleContainer(1);

    private final String NBT_CUSTOM_VALUE_KEY = "CustomValueKey";
    private String customValue = "InitValue: " + System.currentTimeMillis() + "ms";
    

    public ExampleBlockEntity(BlockPos pos, BlockState state) 
    {
        super(ExampleMod.EXAMPLE_ENTITY_BLOCK.get(), pos, state);
    }
    

    public void dropContainer()
    {
        Containers.dropContents(this.level, this.worldPosition, this.inventory);
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) 
    {
        return new ExampleScreenHandler(syncId, playerInventory, this.inventory);
    }

    @Override
    public Component getDisplayName() 
    {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) 
    {
        super.loadAdditional(nbt, registryLookup);
        ContainerHelper.loadAllItems(nbt, this.inventory.getItems(), registryLookup);

        this.customValue = nbt.getString(NBT_CUSTOM_VALUE_KEY);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) 
    {
        super.saveAdditional(nbt, registryLookup);
        ContainerHelper.saveAllItems(nbt, this.inventory.getItems(), registryLookup);

        nbt.putString(NBT_CUSTOM_VALUE_KEY, this.customValue);
    }

}

