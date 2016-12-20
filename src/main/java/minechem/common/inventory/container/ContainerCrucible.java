package minechem.common.inventory.container;

import minechem.common.tile.TileCrucible;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCrucible extends ContainerBase<TileCrucible> {

	public ContainerCrucible(EntityPlayer player, TileCrucible tile) {
		super(player, tile);
		addPlayerInventory(8, 84);
	}

}
