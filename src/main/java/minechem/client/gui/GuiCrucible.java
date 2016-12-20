package minechem.client.gui;

import minechem.common.Compendium;
import minechem.common.inventory.container.ContainerCrucible;
import minechem.common.tile.TileCrucible;

public class GuiCrucible extends GuiBase<TileCrucible, ContainerCrucible> {

	public GuiCrucible(ContainerCrucible container) {
		super(container, 176, 166, Compendium.Resource.GUI.electricCrucible);
	}

}
