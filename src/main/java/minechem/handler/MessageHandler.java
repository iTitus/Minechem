package minechem.handler;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import minechem.Compendium;
import minechem.handler.message.AchievementMessage;
import minechem.handler.message.JournalMessage;
import minechem.handler.message.ResearchMessage;

public class MessageHandler implements IMessageHandler
{
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Compendium.Naming.id);
    private static int id = 0;

    /**
     * Initialize the MessageHandler
     */
    public static void init()
    {
        INSTANCE.registerMessage(AchievementMessage.class, AchievementMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(JournalMessage.class, JournalMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(ResearchMessage.class, ResearchMessage.class, id++, Side.SERVER);
    }

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply is needed.
     *
     * @param message The message
     * @return an optional return message
     */
    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx)
    {
        return null;
    }
}
