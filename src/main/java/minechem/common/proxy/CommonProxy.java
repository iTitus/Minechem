package minechem.common.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.Minechem;
import minechem.common.handler.AchievementHandler;
import minechem.common.handler.ElementHandler;
import minechem.common.handler.EventHandler;
import minechem.common.handler.GuiHandler;
import minechem.common.handler.MoleculeHandler;
import minechem.common.network.NetworkHandler;
import minechem.common.registry.AugmentRegistry;
import minechem.common.registry.BlockRegistry;
import minechem.common.registry.CreativeTabRegistry;
import minechem.common.registry.ItemRegistry;
import minechem.common.registry.JournalRegistry;
import minechem.common.registry.RecipeRegistry;
import minechem.common.util.LogHelper;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		// Load configuration.
		LogHelper.debug("Loading configuration...");
		Config.init(new File(event.getModConfigurationDirectory(), Compendium.Naming.id + "/" + Compendium.Naming.id + ".cfg"));

		LogHelper.debug("Registering Packets...");
		NetworkHandler.init();

		// Register Elements and Molecules before constructing items
		LogHelper.debug("Registering Elements...");
		ElementHandler.init();

		LogHelper.debug("Registering Molecules...");
		MoleculeHandler.init();

		// Register items and blocks.
		LogHelper.debug("Registering Items...");
		ItemRegistry.init();

		LogHelper.debug("Registering Blocks...");
		BlockRegistry.init();

		LogHelper.debug("Registering Augments...");
		AugmentRegistry.init();

		LogHelper.debug("Registering CreativeTabs...");
		CreativeTabRegistry.init();

		LogHelper.debug("Registering Journal...");
		JournalRegistry.init();

		// Register Event Handlers
		LogHelper.debug("Registering Event Handlers...");
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	public void init(FMLInitializationEvent event) {
		LogHelper.debug("Registering Recipes...");
		RecipeRegistry.getInstance().init();

		LogHelper.debug("Registering GUI and Container handlers...");
		NetworkRegistry.INSTANCE.registerGuiHandler(Minechem.instance, new GuiHandler());

		LogHelper.debug("Registering Achievements...");
		AchievementHandler.init();

	}

	public World getClientWorld() {
		return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		return context.getServerHandler().playerEntity;
	}

	public World getWorld(MessageContext context) {
		return context.getServerHandler().playerEntity.world;
	}

	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Minechem has loaded");
	}

	public String getCurrentSaveDir() {
		return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
	}

	public String getCurrentLanguage() {
		return "en_US";
	}

}
