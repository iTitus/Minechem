package minechem.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import minechem.common.handler.ResearchHandler;
import minechem.common.proxy.CommonProxy;

@Mod(modid = Compendium.Naming.id, name = Compendium.Naming.name, version = Compendium.Version.full, guiFactory = "minechem.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.10.2,)", dependencies = "required-after:Forge@[12.18.2.2099,)")
public class Minechem {

	@Mod.Instance
	public static Minechem instance;

	@SidedProxy(clientSide = "minechem.common.proxy.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
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
