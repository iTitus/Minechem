package minechem.item.journal.pages.elements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import minechem.Compendium;
import repackage.net.afterlifelochie.fontbox.document.Element;
import repackage.net.afterlifelochie.fontbox.document.Image;
import repackage.net.afterlifelochie.fontbox.document.ImageItemStack;
import repackage.net.afterlifelochie.fontbox.document.property.AlignmentMode;
import repackage.net.afterlifelochie.fontbox.document.property.FloatMode;

public class JournalImage extends JournalElement {
	private Element element;

	public JournalImage(String pageKey, String imageDir, int width, int height, AlignmentMode alignmentMode, FloatMode floatMode) {
		super(pageKey);
		element = new Image(new ResourceLocation(Compendium.Naming.id, imageDir), width, height, alignmentMode, floatMode);
	}

	public JournalImage(String pageKey, ResourceLocation image, int width, int height, AlignmentMode alignmentMode, FloatMode floatMode) {
		super(pageKey);
		element = new Image(image, width, height, alignmentMode, floatMode);
	}

	public JournalImage(String pageKey, ItemStack stack, int width, int height, AlignmentMode alignmentMode, FloatMode floatMode) {
		super(pageKey);
		element = new ImageItemStack(stack, width, height, alignmentMode, floatMode);
	}

	@Override
	public Element getElement(EntityPlayer player) {
		return isUnlocked(player, getKey()) ? element : null;
	}

	@Override
	public Element getElement(String[] keys) {
		return isUnlocked(keys, getKey()) ? element : null;
	}
}
