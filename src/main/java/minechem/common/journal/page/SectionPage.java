package minechem.common.journal.page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import net.minecraft.entity.player.EntityPlayer;

import minechem.common.journal.element.JournalHeader;
import repackage.net.afterlifelochie.fontbox.data.FormattedString;
import repackage.net.afterlifelochie.fontbox.document.CompilerHint;
import repackage.net.afterlifelochie.fontbox.document.Element;
import repackage.net.afterlifelochie.fontbox.document.Heading;

public class SectionPage extends JournalPage {
	private final Map<String, IJournalPage> pages = new LinkedHashMap<String, IJournalPage>();
	JournalHeader heading;

	public SectionPage(String page) {
		this(page, new ArrayList<IJournalPage>());
	}

	public SectionPage(String page, List<IJournalPage> pageList) {
		this(page, "", pageList);
	}

	public SectionPage(String page, String chapter, List<IJournalPage> pageList) {
		super(page, chapter);
		for (IJournalPage jPage : pageList) {
			pages.put(jPage.getPageName(), jPage);
		}
		heading = new JournalHeader(getPageKey());
	}

	@Override
	public IJournalPage getPage(String key) {
		IJournalPage result = super.getPage(key);
		if (result == null) {
			if (pages.containsKey(key)) {
				return pages.get(key);
			}
			Matcher matcher = subPagePattern.matcher(key);
			if (matcher.find()) {
				if (pages.containsKey(matcher.group(1))) {
					return pages.get(matcher.group(1)).getPage(matcher.group(2));
				}
			}
		}
		return result;
	}

	@Override
	public boolean hasSubPages() {
		return true;
	}

	@Override
	public void addSubPage(IJournalPage page) {
		pages.put(page.getPageName(), page);
	}

	@Override
	public int getSubPages() {
		int total = pages.size();
		for (IJournalPage page : pages.values()) {
			total += page.getSubPages();
		}
		return total;
	}

	public List<Element> getPageElements(EntityPlayer player) {
		List<Element> result = new ArrayList<Element>();
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(player)) {
				// @TODO: for every unlocked page add a link.
			}
		}
		return result;
	}

	public List<Element> getPageElements(String[] keys) {
		List<Element> result = new ArrayList<Element>();
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(keys)) {
				// @TODO: for every unlocked page add a link.
			}
		}
		return result;
	}

	@Override
	public List<Element> getElements(EntityPlayer player) {
		List<Element> result = new ArrayList<Element>();
		for (IJournalPage page : pages.values()) {
			List<Element> elements = page.getElements(player);
			if (elements.size() > 0 && page instanceof SectionPage) {
				result.addAll(((SectionPage) page).getIndexPage(player, 0));
				result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
			}
			result.addAll(elements);
			result.addAll(page.getElements(player));
		}
		if (!result.isEmpty()) {
			result.addAll(0, getPageElements(player));
		}
		return result;
	}

	@Override
	public List<Element> getElements(String[] keys) {
		List<Element> result = new ArrayList<Element>();
		for (IJournalPage page : pages.values()) {
			List<Element> elements = page.getElements(keys);
			if (elements.size() > 0 && page instanceof SectionPage) {
				result.addAll(((SectionPage) page).getIndexPage(keys, 0));
				result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
			}
			result.addAll(elements);
		}
		if (!result.isEmpty()) {
			result.addAll(0, getPageElements(keys));
		}
		return result;
	}

	@Override
	public boolean isUnlocked(EntityPlayer player) {
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(player)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isUnlocked(String[] keys) {
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(keys)) {
				return true;
			}
		}
		return false;
	}

	public List<Element> getIndexPage(String[] keys, int indent) {
		List<Element> result = new ArrayList<Element>();
		result.add(heading.getElement(indent));
		if (indent > 1) {
			return result;
		}
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(keys)) {
				if (page instanceof SectionPage) {
					result.addAll(((SectionPage) page).getIndexPage(keys, indent + 1));
				} else if (indent < 1) {
					String sIndent = "";
					for (int i = 0; i < indent + 1; i++) {
						sIndent += "--";
					}
					result.add(new Heading(page.getPageKey(), new FormattedString(sIndent + " " + page.getPageName())));
				}
			}
		}
		return result;
	}

	public List<Element> getIndexPage(EntityPlayer player, int indent) {
		List<Element> result = new ArrayList<Element>();
		result.add(heading.getElement(indent));
		if (indent > 1) {
			return result;
		}
		for (IJournalPage page : pages.values()) {
			if (page.isUnlocked(player)) {
				if (page instanceof SectionPage) {
					result.addAll(((SectionPage) page).getIndexPage(player, indent + 1));
				} else {
					String sIndent = "";
					for (int i = 0; i < indent + 1; i++) {
						sIndent += "--";
					}
					result.add(new Heading(page.getPageKey(), new FormattedString(sIndent + " " + page.getPageName())));
				}
			}
		}
		return result;
	}
}
