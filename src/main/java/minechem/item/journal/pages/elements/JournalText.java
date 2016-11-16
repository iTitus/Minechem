package minechem.item.journal.pages.elements;

import net.minecraft.entity.player.EntityPlayer;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import repackage.net.afterlifelochie.fontbox.data.FormattedString;
import repackage.net.afterlifelochie.fontbox.document.Element;
import repackage.net.afterlifelochie.fontbox.document.Image;
import repackage.net.afterlifelochie.fontbox.document.Paragraph;
import repackage.net.afterlifelochie.fontbox.document.property.AlignmentMode;

public class JournalText extends JournalElement {
	private String textKey;

	public JournalText(String pageKey) {
		this(pageKey, pageKey + ".text");
	}

	public JournalText(String pageKey, String textKey) {
		super(pageKey);
		this.textKey = "journal." + textKey;
	}

	@Override
	public Element getElement(EntityPlayer player) {
		if (isUnlocked(player, getKey())) {
			String s = LocalizationHelper.getLocalString(textKey);
			return s.isEmpty() ? new Image(Compendium.Resource.GUI.noContent, 301, 294, AlignmentMode.JUSTIFY) : new Paragraph(new FormattedString(s));
		}
		return null;
	}

	@Override
	public Element getElement(String[] keys) {
		if (isUnlocked(keys, getKey())) {
			String s = LocalizationHelper.getLocalString(textKey);
			return s.isEmpty() ? new Image(Compendium.Resource.GUI.noContent, 301, 294, AlignmentMode.JUSTIFY) : new Paragraph(new FormattedString(s));
		}
		return null;
	}
}
