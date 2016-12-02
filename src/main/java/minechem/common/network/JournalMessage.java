package minechem.common.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.common.item.ItemJournal;

/**
 * Message used to write knowledge in on an journal on the server side
 */
public class JournalMessage extends BaseMessage implements IMessageHandler<JournalMessage, IMessage> {

	private String uuid;

	public JournalMessage() {

	}

	public JournalMessage(EntityPlayer player) {
		this(player.getUniqueID());
	}

	public JournalMessage(UUID uuid) {
		this.uuid = uuid.toString();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		this.uuid = new String(buf.readBytes(length).array());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.uuid.length());
		buf.writeBytes(this.uuid.getBytes());
	}

	@Override
	public IMessage onMessage(JournalMessage message, MessageContext ctx) {
		EntityPlayer player = getServerPlayer(ctx);
		if (player.getUniqueID().equals(UUID.fromString(message.uuid))) {
			ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
			if (stack == null || !(stack.getItem() instanceof ItemJournal)) {
				stack = player.getHeldItem(EnumHand.OFF_HAND);
			}
			if (stack != null && stack.getItem() instanceof ItemJournal) {
				((ItemJournal) stack.getItem()).writeKnowledge(stack, player);
			}
		}
		return null;
	}
}
