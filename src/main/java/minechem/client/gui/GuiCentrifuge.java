package minechem.client.gui;

import minechem.common.Compendium;
import minechem.common.inventory.container.ContainerCentrifuge;
import minechem.common.tile.TileCentrifuge;

public class GuiCentrifuge extends GuiBase<TileCentrifuge, ContainerCentrifuge> {

	public GuiCentrifuge(ContainerCentrifuge container) {
		super(container, 176, 166, Compendium.Resource.GUI.centrifuge);
	}

}
