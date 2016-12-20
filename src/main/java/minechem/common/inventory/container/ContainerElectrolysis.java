package minechem.common.inventory.container;

import minechem.common.tile.TileElectrolysis;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerElectrolysis extends ContainerBase<TileElectrolysis> {

	public ContainerElectrolysis(EntityPlayer player, TileElectrolysis tile) {
		super(player, tile);
		addPlayerInventory(8, 84);
	}

}
