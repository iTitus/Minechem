package minechem.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.handler.EventHandler;

public class CommonProxy {

	public World getClientWorld() {
		return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		return context.getServerHandler().playerEntity;
	}

	public World getWorld(MessageContext context) {
		return context.getServerHandler().playerEntity.worldObj;
	}

	public void registerRenderers() {
		// NOOP
	}

	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	public String getCurrentSaveDir() {
		return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
	}

	public String getCurrentLanguage() {
		return "en_US";
	}

	public void registerFonts() {
		// NOOP
	}
}
