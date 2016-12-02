package minechem.client.gui;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import minechem.common.Compendium;
import minechem.common.Config;

public class GuiModConfig extends GuiConfig {

	public GuiModConfig(GuiScreen guiScreen) {
		super(guiScreen, new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Compendium.Naming.id, false, false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
	}

}
