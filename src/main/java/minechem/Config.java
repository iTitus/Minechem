package minechem;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.MinecraftForge;
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
		List<IConfigElement> list = Lists.newArrayList();
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}

	public static void init(File suggestedConfigurationFile) {
		config = new Configuration(suggestedConfigurationFile);
		//new Configuration(new File(Compendium.Config.configPrefix + "Minechem.cfg"));
		loadConfig();
		MinecraftForge.EVENT_BUS.register(Config.class);
	}

	private static void loadConfig() {

		String desc;

		//config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, I18n.translateToLocal("config.general.description"));

		//prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", debugMode);
		//prop.comment = I18n.translateToLocal("config.debugMode");
		//prop.setLanguageKey("config.debugMode.tooltip");
		//debugMode = prop.getBoolean();
		desc = "";
		debugMode = loadPropBool("debugMode", desc, debugMode);

		//prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultElements", Config.useDefaultElements);
		//prop.comment = I18n.translateToLocal("config.useDefaultElements");
		//prop.setLanguageKey("config.useDefaultElements.tooltip");
		//useDefaultElements = prop.getBoolean();
		desc = "";
		useDefaultElements = loadPropBool("useDefaultElements", desc, useDefaultElements);

		//prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultMolecules", Config.useDefaultMolecules);
		//prop.comment = I18n.translateToLocal("config.useDefaultMolecules");
		//prop.setLanguageKey("config.useDefaultMolecules.tooltip");
		//useDefaultMolecules = prop.getBoolean();
		desc = "";
		useDefaultMolecules = loadPropBool("useDefaultMolecules", desc, useDefaultMolecules);

		//prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultResearchPages", Config.useDefaultResearchPages);
		//prop.comment = I18n.translateToLocal("config.useDefaultResearchPages");
		//prop.setLanguageKey("config.useDefaultResearchPages.tooltip");
		//useDefaultResearchPages = prop.getBoolean();
		desc = "";
		useDefaultResearchPages = loadPropBool("useDefaultResearchPages", desc, useDefaultResearchPages);

		//prop = config.get(Configuration.CATEGORY_GENERAL, "enablePatreon", Config.enablePatreon);
		//prop.comment = I18n.translateToLocal("config.enablePatreon");
		//prop.setLanguageKey("config.enablePatreon.tooltip");
		//enablePatreon = prop.getBoolean();
		desc = "";
		enablePatreon = loadPropBool("enablePatreon", desc, enablePatreon);

		//prop = config.get(Configuration.CATEGORY_GENERAL, "playerPrivateKnowledge", Config.playerPrivateKnowledge);
		//prop.comment = I18n.translateToLocal("config.playerPrivateKnowledge");
		//prop.setLanguageKey("config.playerPrivateKnowledge.tooltip");
		//playerPrivateKnowledge = prop.getBoolean();
		desc = "";
		playerPrivateKnowledge = loadPropBool("playerPrivateKnowledge", desc, playerPrivateKnowledge);

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static int loadPropInt(String propName, String desc, int default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		return prop.getInt(default_);
	}

	public static double loadPropDouble(String propName, String desc, double default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		return prop.getDouble(default_);
	}

	public static boolean loadPropBool(String propName, String desc, boolean default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		return prop.getBoolean(default_);
	}

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Compendium.Naming.id)) {
			loadConfig();
		}
	}
}
