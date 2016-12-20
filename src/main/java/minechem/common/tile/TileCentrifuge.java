package minechem.common.tile;

import minechem.common.registry.BlockRegistry;

public class TileCentrifuge extends TileInventory {

	public TileCentrifuge() {
		super(BlockRegistry.centrifuge.getRegistryName().toString(), 2);
	}

}
