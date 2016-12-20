package minechem.common.tile;

import minechem.common.registry.BlockRegistry;

public class TileCrucible extends TileInventory {

	public TileCrucible() {
		super(BlockRegistry.electricCrucible.getRegistryName().toString(), 2);
	}

}
