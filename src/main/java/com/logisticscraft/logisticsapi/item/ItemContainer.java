package com.logisticscraft.logisticsapi.item;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.logisticscraft.logisticsapi.BlockSide;
import com.logisticscraft.logisticsapi.LogisticObject;

import de.robotricker.transportpipes.pipeitems.ItemData;
import de.robotricker.transportpipes.pipes.FilteringMode;

public interface ItemContainer extends LogisticObject {

	public ItemStack extractItem(BlockSide extractDirection, int extractAmount, List<ItemData> filterItems, FilteringMode filteringMode);

	public ItemStack insertItem(BlockSide insertDirection, ItemStack insertion);

	public int howMuchSpaceForItemAsync(BlockSide insertDirection, ItemStack insertion);

	public boolean isInterfaceable(BlockSide face);

}