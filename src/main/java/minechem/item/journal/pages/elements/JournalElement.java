package minechem.item.journal.pages.elements;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;

import minechem.registry.ResearchRegistry;

public abstract class JournalElement implements IJournalElement {
	private String pageKey;

	protected JournalElement(String pageKey) {
		this.pageKey = pageKey;
	}

	public String getKey() {
		return pageKey;
	}

	public boolean isUnlocked(EntityPlayer player, String key) {
		return ResearchRegistry.getInstance().hasUnlockedResearch(player, key);
	}

	public boolean isUnlocked(String[] keys, String key) {
		return Arrays.asList(keys).contains(key);
	}
}
