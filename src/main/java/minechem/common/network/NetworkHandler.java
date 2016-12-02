package minechem.common.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import minechem.common.Compendium;

public class NetworkHandler {

	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Compendium.Naming.id);

	/**
	 * Initialize the MessageHandler
	 */
	public static void init() {
		int id = 0;
		INSTANCE.registerMessage(AchievementMessage.class, AchievementMessage.class, id++, Side.SERVER);
		INSTANCE.registerMessage(JournalMessage.class, JournalMessage.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ResearchMessage.class, ResearchMessage.class, id++, Side.SERVER);
	}

}
