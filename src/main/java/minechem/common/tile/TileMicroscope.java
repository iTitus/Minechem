package minechem.common.tile;

import net.minecraft.item.ItemStack;
import minechem.common.item.ItemChemical;
import minechem.common.registry.BlockRegistry;

public class TileMicroscope extends TileInventory {

	public TileMicroscope() {
		super(BlockRegistry.opticalMicroscope.getRegistryName().toString(), 1);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return ItemChemical.getChemicalBase(stack) != null;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

}
