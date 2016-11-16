package minechem.helper;

import net.minecraft.entity.player.EntityPlayer;

import minechem.handler.MessageHandler;
import minechem.handler.message.ResearchMessage;
import minechem.registry.ResearchRegistry;

public class ResearchHelper {
	public static void addResearch(EntityPlayer player, String key, boolean isRemote) {
		if (isRemote) {
			MessageHandler.INSTANCE.sendToServer(new ResearchMessage(key));
		} else {
			ResearchRegistry.getInstance().addResearch(player, key);
		}
	}
}
