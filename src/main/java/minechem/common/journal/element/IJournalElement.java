package minechem.common.journal.element;

import net.minecraft.entity.player.EntityPlayer;

import repackage.net.afterlifelochie.fontbox.document.Element;

public interface IJournalElement {
	Element getElement(EntityPlayer player);

	Element getElement(String[] keys);
}
