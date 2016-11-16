package minechem;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import minechem.handler.ElementHandler;
import minechem.handler.GuiHandler;
import minechem.handler.MoleculeHandler;
import minechem.handler.ResearchHandler;
import minechem.helper.LogHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.ItemRegistry;

@Mod(modid = Compendium.Naming.id, name = Compendium.Naming.name, version = Compendium.Version.full, guiFactory = "minechem.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.10.2,)", dependencies = "required-after:Forge@[12.18.2.2099,)")
public class Minechem {

	@Mod.Instance
	public static Minechem instance;

	@SidedProxy(clientSide = "minechem.proxy.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// Load configuration.
		LogHelper.debug("Loading configuration...");
		Config.init(new File(event.getModConfigurationDirectory(), Compendium.Naming.id + "/" + Compendium.Naming.id + ".cfg"));

		// LogHelper.debug("Registering Packets...");
		// MessageHandler.init();

		// Register Elements and Molecules before constructing items
		LogHelper.debug("Registering Elements...");
		ElementHandler.init();

		LogHelper.debug("Registering Molecules...");
		MoleculeHandler.init();

		// Register items and blocks.
		LogHelper.debug("Registering Items...");
		ItemRegistry.init();

		// LogHelper.debug("Registering Blocks...");
		// BlockRegistry.init();

		// LogHelper.debug("Registering Augments...");
		// AugmentRegistry.init();

		// LogHelper.debug("Registering CreativeTabs...");
		// CreativeTabRegistry.init();

		// Register Event Handlers
		// LogHelper.debug("Registering Event Handlers...");
		// proxy.registerEventHandlers();

		// LogHelper.debug("Registering Journal...");
		// JournalRegistry.init();

		LogHelper.debug("Registering Renderers...");
		proxy.registerRenderers();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		// LogHelper.debug("Registering Recipes...");
		// RecipeRegistry.getInstance().init();

		LogHelper.debug("Registering GUI and Container handlers...");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		// LogHelper.debug("Registering Fonts...");
		// proxy.registerFonts();

		// LogHelper.debug("Registering Achievements...");
		// AchievementHandler.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// proxy.registerResourcesListener();

		LogHelper.info("Minechem has loaded");
	}

	@Mod.EventHandler
	public void onServerStarted(FMLServerStartedEvent event) {
		ResearchHandler.readPlayerResearch();
	}

	@Mod.EventHandler
	public void onServerStopping(FMLServerStoppingEvent event) {
		ResearchHandler.saveResearch();
	}
}
