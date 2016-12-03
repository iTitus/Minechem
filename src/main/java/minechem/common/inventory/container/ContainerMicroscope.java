package minechem.common.inventory.container;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.items.SlotItemHandler;

import minechem.common.tile.TileMicroscope;

public class ContainerMicroscope extends ContainerBase<TileMicroscope> {

	public ContainerMicroscope(EntityPlayer player, TileMicroscope tile) {
		super(player, tile);
		addPlayerInventory(8, 84);
		addSlotToContainer(new SlotItemHandler(tile.getItemStackHandler(), 0, 122, 35));
	}

}
