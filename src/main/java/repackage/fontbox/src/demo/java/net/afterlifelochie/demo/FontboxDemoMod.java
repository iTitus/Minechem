package net.afterlifelochie.demo;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "fontbox-demo", modid = FontboxDemoMod.modid)
public class FontboxDemoMod {

	public static final String modid = "fontbox-demo";

	@SidedProxy(clientSide = "net.afterlifelochie.demo.FontboxClient", serverSide = "net.afterlifelochie.demo.FontboxServer")
	public static FontboxServer proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
