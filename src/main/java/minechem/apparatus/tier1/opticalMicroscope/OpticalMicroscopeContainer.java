package minechem.apparatus.tier1.opticalMicroscope;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import minechem.apparatus.prefab.gui.container.BasicContainer;

public class OpticalMicroscopeContainer extends BasicContainer {
	/**
	 * Container object for the opticalMicroscope
	 *
	 * @param inventoryPlayer   the player's inventory
	 * @param opticalMicroscope the microscope TileEntity
	 */
	public OpticalMicroscopeContainer(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope) {
		bindPlayerInventory(inventoryPlayer);
		addSlotToContainer(new Slot(opticalMicroscope, 0, 32, 32));
	}
}
