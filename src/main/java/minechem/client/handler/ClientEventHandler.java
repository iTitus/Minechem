package minechem.client.handler;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre paramPre) {
		// IconHandler.registerIcons(paramPre);
	}

}
