package minechem.common.item;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.Minechem;
import minechem.common.registry.AchievementRegistry;
import minechem.common.registry.ResearchRegistry;
import minechem.common.util.ArrayHelper;
import minechem.common.util.LocalizationHelper;

public class ItemJournal extends ItemBase {

	public ItemJournal() {
		super(Compendium.Naming.journal);
		setMaxStackSize(1);
	}

	/**
	 * Gets a list of authors
	 *
	 * @param stack the journal Stack
	 * @return an array of authors can be empty
	 */
	public static String[] getAuthors(ItemStack stack) {
		if (!stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("authors", Constants.NBT.TAG_LIST)) {
			NBTTagList authorsTag = stack.getTagCompound().getTagList("authors", Constants.NBT.TAG_STRING);
			String[] authors = new String[authorsTag.tagCount()];
			for (int i = 0; i < authorsTag.tagCount(); i++) {
				authors[i] = authorsTag.getStringTagAt(i);
			}
			return ArrayHelper.removeNulls(authors, String.class);
		}
		return new String[0];
	}

	/**
	 * Gets a list of knowledgeKeys
	 *
	 * @param stack the journal Stack
	 * @return an array of knowledgeKeys can be empty
	 */
	public static String[] getKnowledgeKeys(ItemStack stack) {
		if (!stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("research", Constants.NBT.TAG_LIST)) {
			NBTTagList authorsTag = stack.getTagCompound().getTagList("research", Constants.NBT.TAG_STRING);
			String[] knowledgeKeys = new String[authorsTag.tagCount()];
			for (int i = 0; i < authorsTag.tagCount(); i++) {
				knowledgeKeys[i] = authorsTag.getStringTagAt(i);
			}
			return ArrayHelper.removeNulls(knowledgeKeys, String.class);
		}
		return new String[0];
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty()) {
			if (player.isSneaking()) {
				if (!world.isRemote && !Config.playerPrivateKnowledge) {
					writeKnowledge(stack, player);
				}
			} else {
				BlockPos pos = player.getPosition();
				player.openGui(Minechem.instance, Compendium.Gui.JOURNAL_ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
			player.addStat(AchievementRegistry.journal);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	/**
	 * Writes knowledge to the journal
	 *
	 * @param stack the journal stack
	 * @param player    the player that writes the knowledge
	 * @param isRemote  is the world remote on true it will send a
	 *                  {@link minechem.common.network.JournalMessage} to the server
	 */
	public void writeKnowledge(ItemStack stack, EntityPlayer player) {
		// if (isRemote) {
		// MessageHandler.INSTANCE.sendToServer(new JournalMessage(player));
		// return;
		// }

		NBTTagCompound tagCompound = stack.getTagCompound();
		Set<String> playerKnowledge = ResearchRegistry.getInstance().getResearchFor(player);
		if (playerKnowledge == null) {
			return;
		}
		Set<String> bookKnowledge = new LinkedHashSet<String>();
		if (tagCompound == null) {
			tagCompound = new NBTTagCompound();
		} else if (tagCompound.hasKey("research")) {
			NBTTagList bookKnowledgeTag = tagCompound.getTagList("research", 8);
			for (int i = 0; i < bookKnowledgeTag.tagCount(); i++) {
				bookKnowledge.add(bookKnowledgeTag.getStringTagAt(i));
			}
		}
		bookKnowledge.addAll(playerKnowledge);
		NBTTagList bookKnowledgeTag = new NBTTagList();
		for (String knowledgeKey : bookKnowledge) {
			bookKnowledgeTag.appendTag(new NBTTagString(knowledgeKey));
		}
		tagCompound.setTag("research", bookKnowledgeTag);

		Set<String> authors = new LinkedHashSet<String>();
		if (tagCompound.hasKey("authors")) {
			NBTTagList authorsTag = tagCompound.getTagList("authors", 8);
			for (int i = 0; i < authorsTag.tagCount(); i++) {
				authors.add(authorsTag.getStringTagAt(i));
			}
		}
		authors.add(player.getDisplayName().getUnformattedText());
		NBTTagList authorsTag = new NBTTagList();
		for (String author : authors) {
			authorsTag.appendTag(new NBTTagString(author));
		}
		tagCompound.setTag("authors", authorsTag);
		stack.setTagCompound(tagCompound);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		if (!Config.playerPrivateKnowledge) {
			String[] authors = getAuthors(stack);
			if (authors == null || authors.length < 1) {
				return;
			}
			tooltip.add(LocalizationHelper.getLocalString("gui.journal.writtenBy") + ":");
			for (String author : authors) {
				tooltip.add("- " + author);
			}
		}
	}
}
