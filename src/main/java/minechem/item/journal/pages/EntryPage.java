package minechem.item.journal.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import minechem.item.journal.pages.elements.IJournalElement;
import minechem.item.journal.pages.elements.JournalHeader;
import repackage.net.afterlifelochie.fontbox.document.CompilerHint;
import repackage.net.afterlifelochie.fontbox.document.Element;

public class EntryPage extends JournalPage {
	List<IJournalElement> elements;

	public EntryPage(String page, String chapter, IJournalElement... elements) {
		this(page, chapter, new ArrayList<IJournalElement>(Arrays.asList(elements)));
	}

	public EntryPage(String page, String chapter, List<IJournalElement> elements) {
		super(page, chapter);
		if (elements.size() == 0 || !(elements.get(0) instanceof JournalHeader)) {
			elements.add(0, new JournalHeader(getPageKey()));
		}
		this.elements = elements;
	}

	@Override
	public List<Element> getElements(EntityPlayer player) {
		List<Element> result = new ArrayList<Element>();
		if (isUnlocked(player)) {
			for (IJournalElement element : elements) {
				Element e = element.getElement(player);
				if (e != null) {
					result.add(e);
				}
			}
			result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
		}
		return result;
	}

	@Override
	public List<Element> getElements(String[] keys) {
		List<Element> result = new ArrayList<Element>();
		if (isUnlocked(keys)) {
			for (IJournalElement element : elements) {
				Element e = element.getElement(keys);
				if (e != null) {
					result.add(e);
				}
			}
			result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
		}
		return result;
	}
}
