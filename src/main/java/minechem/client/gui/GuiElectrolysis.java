package minechem.client.gui;

import minechem.common.Compendium;
import minechem.common.inventory.container.ContainerElectrolysis;
import minechem.common.tile.TileElectrolysis;

public class GuiElectrolysis extends GuiBase<TileElectrolysis, ContainerElectrolysis> {

	public GuiElectrolysis(ContainerElectrolysis container) {
		super(container, 176, 166, Compendium.Resource.GUI.electrolysis);
	}

}
