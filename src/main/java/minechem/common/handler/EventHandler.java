package minechem.common.handler;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
}
