package minechem.common.tile;

import net.minecraft.item.ItemStack;

import minechem.common.item.ItemChemical;
import minechem.common.registry.BlockRegistry;

public class TileMicroscope extends TileInventory {

	public TileMicroscope() {
		super(BlockRegistry.opticalMicroscope.getRegistryName().toString(), 1);
	}

	@Override
	protected boolean canInsert(int slot, ItemStack stack) {
		return ItemChemical.getChemicalBase(stack) != null;
	}

}
