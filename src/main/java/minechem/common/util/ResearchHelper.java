package minechem.common.util;

import net.minecraft.entity.player.EntityPlayer;

import minechem.common.registry.ResearchRegistry;

public class ResearchHelper {

	public static void addResearch(EntityPlayer player, String key) {
		/*if (isRemote) {
			MessageHandler.INSTANCE.sendToServer(new ResearchMessage(key));
		} else {*/
		ResearchRegistry.getInstance().addResearch(player, key);
		//}
	}

}
