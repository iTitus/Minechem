package minechem.common.inventory.container;

import minechem.common.tile.TileCentrifuge;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCentrifuge extends ContainerBase<TileCentrifuge> {

	public ContainerCentrifuge(EntityPlayer player, TileCentrifuge tile) {
		super(player, tile);
		addPlayerInventory(8, 84);
	}

}
