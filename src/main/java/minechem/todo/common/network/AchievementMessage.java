package minechem.todo.common.network;

import io.netty.buffer.ByteBuf;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.common.chemical.Element;
import minechem.common.registry.AchievementRegistry;
import minechem.common.registry.ElementRegistry;

/**
 * Used for triggering achievements from Client only code
 */
public class AchievementMessage extends BaseMessage implements IMessageHandler<AchievementMessage, IMessage> {

	private String achievement;
	private boolean isElement;

	public AchievementMessage() {

	}

	public AchievementMessage(String achievement) {
		this.achievement = achievement;
		this.isElement = false;
	}

	public AchievementMessage(Element element) {
		this.achievement = element.shortName;
		this.isElement = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.isElement = buf.readBoolean();
		int length = buf.readInt();
		this.achievement = new String(buf.readBytes(length).array());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.isElement);
		buf.writeInt(this.achievement.length());
		buf.writeBytes(this.achievement.getBytes());
	}

	@Override
	public IMessage onMessage(AchievementMessage message, MessageContext ctx) {
		if (message.isElement) {
			getServerPlayer(ctx).addStat(AchievementRegistry.getAchievement(ElementRegistry.getInstance().getElement(message.achievement)));
		} else {
			getServerPlayer(ctx).addStat(AchievementRegistry.getAchievement(message.achievement));
		}
		return null;
	}
}
