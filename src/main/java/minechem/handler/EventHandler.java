package minechem.handler;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Holds all event triggered methods
 */
public class EventHandler {
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		ResearchHandler.saveResearch();
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		ResearchHandler.saveResearch();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerIcons(TextureStitchEvent.Pre paramPre) {
		//IconHandler.registerIcons(paramPre);
	}
}
