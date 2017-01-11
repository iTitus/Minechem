package minechem.common.achievement;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.font.Font;
import minechem.common.Compendium;
import minechem.common.chemical.Element;
import minechem.common.util.ColourHelper;
import minechem.common.util.LocalizationHelper;
import minechem.todo.common.achievement.IAchievementRenderer;

/**
 * {@link net.minecraft.stats.Achievement} wrapper for {@link minechem.common.chemical.Element}s
 */
public class ElementAchievement extends Achievement implements IAchievementRenderer {

	private final static String achievementPrefix = "achievement.";
	private final static String defaultElementTitle = "achievement.minechem.element";
	private final static String defaultElementDescription = "achievement.minechem.element.desc";
	private final static Achievement nullAchievement = null;
	private static Font regularFont, smallFont;
	
	private final Element element;

	public ElementAchievement(Element element, int row, int column) {
		super(achievementPrefix + element.shortName, element.shortName, column, row, element.getItemStack(), nullAchievement);
		this.element = element;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDescription() {
		return String.format(LocalizationHelper.getLocalString(defaultElementDescription), element.fullName);
	}

	/**
	 * Returns the title
	 *
	 * @return an {@link net.minecraft.util.ITextComponent}
	 */
	@Override
	public ITextComponent getStatName() {
		ITextComponent iTextComponent = new TextComponentTranslation(defaultElementTitle, element.shortName);
		iTextComponent.getStyle().setColor(this.getSpecial() ? TextFormatting.DARK_PURPLE : TextFormatting.GREEN);
		return iTextComponent;
	}

	/**
	 * Background colour for the achievement icon
	 *
	 * @return an int representation of the colour
	 */
	@Override
	public int recolourBackground(float greyScale) {
		if (greyScale != 1.0F) {
			return ColourHelper.blend(getColour(element), ColourHelper.RGB(greyScale, greyScale, greyScale));
		}
		return getColour(element);
	}

	private int getColour(Element element) {
		switch (element.type) {
			case alkaliMetal:
				return 0xF63FFF;
			case alkalineEarth:
				return 0xA84DFF;
			case transitionMetal:
				return 0x3DD4FF;
			case basicMetal:
				return 0xFFBA50;
			case semiMetal:
				return 0x0AFF76;
			case nonMetal:
				return 0x329EFF;
			case halogen:
				return 0xFFCB08;
			case nobleGas:
				return 0xFFD148;
			case lanthanide:
				return 0xC2FF00;
			case actinide:
				return 0xFF0D0B;
			default:
				return Compendium.Color.TrueColor.white;
		}
	}

	@Override
	public boolean hasSpecialIconRenderer() {
		return true;
	}

	@Override
	public void renderIcon(FontRenderer fontRenderer, TextureManager textureManager, ItemStack stack, int left, int top) {
		if (regularFont == null) {
			regularFont = new Font(fontRenderer);
		}
		if (smallFont == null) {
			smallFont = new Font(fontRenderer).setFontSize(8);
		}
		regularFont.print(element.shortName, left + 10 - (element.shortName.length() - 1) * 5, top + 8, Compendium.Color.TrueColor.white, true);
		smallFont.print(element.atomicNumber, left, top, Compendium.Color.TrueColor.white, true);
	}
}
