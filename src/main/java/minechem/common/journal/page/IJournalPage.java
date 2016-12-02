package minechem.common.journal.page;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import repackage.net.afterlifelochie.fontbox.document.Element;

public interface IJournalPage {
	String getPageKey();

	IJournalPage getPage(String key);

	String getPageName();

	boolean hasSubPages();

	void addSubPage(IJournalPage page);

	void setChapter(String chapter);

	int getSubPages();

	List<Element> getElements(EntityPlayer player);

	List<Element> getElements(String[] keys);

	boolean isUnlocked(EntityPlayer player);

	boolean isUnlocked(String[] keys);
}
