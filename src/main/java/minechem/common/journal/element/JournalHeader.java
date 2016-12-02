package minechem.common.journal.element;

import net.minecraft.entity.player.EntityPlayer;

import minechem.common.util.LocalizationHelper;
import repackage.net.afterlifelochie.fontbox.data.FormattedString;
import repackage.net.afterlifelochie.fontbox.document.Element;
import repackage.net.afterlifelochie.fontbox.document.Heading;

public class JournalHeader extends JournalElement {
	private String titleKey;

	public JournalHeader(String pageKey) {
		super(pageKey);
		titleKey = "journal" + (pageKey.isEmpty() ? "" : "." + pageKey) + ".title";
	}

	@Override
	public Element getElement(EntityPlayer player) {
		return getElement(0);
	}

	@Override
	public Element getElement(String[] keys) {
		return getElement(0);
	}

	public Element getElement(int indent) {
		String sIndent = "";
		for (int i = 0; i < indent; i++) {
			sIndent += "--";
		}
		return new Heading(getKey(), new FormattedString(sIndent + " " + LocalizationHelper.getLocalString(titleKey)));
	}
}
