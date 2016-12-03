package minechem.client.gui;

import minechem.client.gui.widget.WidgetMicroscope;
import minechem.common.Compendium;
import minechem.common.inventory.container.ContainerMicroscope;
import minechem.common.tile.TileMicroscope;

public class GuiMicroscope extends GuiBase<TileMicroscope, ContainerMicroscope> {

	public GuiMicroscope(ContainerMicroscope container) {
		super(container, 176, 166, Compendium.Resource.GUI.opticalMicroscope);
		addWidget(new WidgetMicroscope(13, 16, this));
	}

}
