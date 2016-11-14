package minechem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
	public static Configuration config;

	// turns on extra logging printouts
	public static boolean debugMode = true;

	// turns on to show Patreon tab in GUI
	public static boolean enablePatreon = true;

	// turns on to copy the newest elements list from jar
	public static boolean useDefaultElements = true;
	// turns on to copy the newest molecules list from jar
	public static boolean useDefaultMolecules = true;
	// turns on to copy the newest researchPages list from jar
	public static boolean useDefaultResearchPages = true;

	// makes it that the player can only see his own work in the book
	public static boolean playerPrivateKnowledge = false;

	public static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}

	public static void init() {

		if (config == null) {
			config = new Configuration(new File(Compendium.Config.configPrefix + "Minechem.cfg"));
			loadConfig();
		}
	}

	private static void loadConfig() {
		Property prop;
		List<String> configList = new ArrayList<String>();

		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, I18n.translateToLocal("config.general.description"));

		prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", Config.debugMode);
		prop.comment = I18n.translateToLocal("config.debugMode");
		prop.setLanguageKey("config.debugMode.tooltip");
		debugMode = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultElements", Config.useDefaultElements);
		prop.comment = I18n.translateToLocal("config.useDefaultElements");
		prop.setLanguageKey("config.useDefaultElements.tooltip");
		useDefaultElements = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultMolecules", Config.useDefaultMolecules);
		prop.comment = I18n.translateToLocal("config.useDefaultMolecules");
		prop.setLanguageKey("config.useDefaultMolecules.tooltip");
		useDefaultMolecules = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultResearchPages", Config.useDefaultResearchPages);
		prop.comment = I18n.translateToLocal("config.useDefaultResearchPages");
		prop.setLanguageKey("config.useDefaultResearchPages.tooltip");
		useDefaultResearchPages = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "enablePatreon", Config.enablePatreon);
		prop.comment = I18n.translateToLocal("config.enablePatreon");
		prop.setLanguageKey("config.enablePatreon.tooltip");
		enablePatreon = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Configuration.CATEGORY_GENERAL, "playerPrivateKnowledge", Config.playerPrivateKnowledge);
		prop.comment = I18n.translateToLocal("config.playerPrivateKnowledge");
		prop.setLanguageKey("config.playerPrivateKnowledge.tooltip");
		playerPrivateKnowledge = prop.getBoolean();
		configList.add(prop.getName());

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Compendium.Naming.id)) {
			loadConfig();
		}
	}
}
