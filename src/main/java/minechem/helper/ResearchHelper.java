package minechem.helper;

import net.minecraft.entity.player.EntityPlayer;

import minechem.registry.ResearchRegistry;

public class ResearchHelper {

	public static void addResearch(EntityPlayer player, String key) {
		/*if (isRemote) {
			MessageHandler.INSTANCE.sendToServer(new ResearchMessage(key));
		} else {*/
		ResearchRegistry.getInstance().addResearch(player, key);
		//}
	}

}
