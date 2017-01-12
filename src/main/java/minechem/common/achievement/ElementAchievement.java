package minechem.common.achievement;

import net.minecraft.stats.Achievement;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import minechem.common.Compendium;
import minechem.common.chemical.Element;

/**
 * {@link Achievement} wrapper for {@link Element}s
 */
//TODO: Fancy Achievement Render
public class ElementAchievement extends Achievement /* implements IAchievementRenderer */ {
	
	private static final String ACHIEVEMENT_NAME = Compendium.Naming.id + ":element";
	
	// private static Font regularFont, smallFont;

	private final Element element;

	public ElementAchievement(Element element, int x, int y) {
		super("achievement." + ACHIEVEMENT_NAME + "." + element.shortName, ACHIEVEMENT_NAME, x, y, element.getItemStack(), null);
		this.element = element;
		setStatStringFormatter(str -> {
			try
            {
                return String.format(str, element.fullName);
            }
            catch (Exception exception)
            {
                return "Error: " + exception.getMessage();
            }
		});
	}

	public Element getElement() {
		return element;
	}
	
	 @Override
	 public ITextComponent getStatName() {
		 ITextComponent iTextComponent = new TextComponentTranslation("achievement." + ACHIEVEMENT_NAME, element.shortName);
		 iTextComponent.getStyle().setColor(getSpecial() ? TextFormatting.DARK_PURPLE : TextFormatting.GREEN);
		 iTextComponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new TextComponentString(statId)));
		 return iTextComponent;
	 }

	// /**
	// * Background colour for the achievement icon
	// *
	// * @return an int representation of the colour
	// */
	// @Override
	// public int recolourBackground(float greyScale) {
	// if (greyScale != 1.0F) {
	// return ColourHelper.blend(getColour(element), ColourHelper.RGB(greyScale,
	// greyScale, greyScale));
	// }
	// return getColour(element);
	// }
	//
	// private int getColour(Element element) {
	// switch (element.type) {
	// case alkaliMetal:
	// return 0xF63FFF;
	// case alkalineEarth:
	// return 0xA84DFF;
	// case transitionMetal:
	// return 0x3DD4FF;
	// case basicMetal:
	// return 0xFFBA50;
	// case semiMetal:
	// return 0x0AFF76;
	// case nonMetal:
	// return 0x329EFF;
	// case halogen:
	// return 0xFFCB08;
	// case nobleGas:
	// return 0xFFD148;
	// case lanthanide:
	// return 0xC2FF00;
	// case actinide:
	// return 0xFF0D0B;
	// default:
	// return Compendium.Color.TrueColor.white;
	// }
	// }
	//
	// @Override
	// public boolean hasSpecialIconRenderer() {
	// return true;
	// }
	//
	// @Override
	// public void renderIcon(FontRenderer fontRenderer, TextureManager
	// textureManager, ItemStack stack, int left, int top) {
	// if (regularFont == null) {
	// regularFont = new Font(fontRenderer);
	// }
	// if (smallFont == null) {
	// smallFont = new Font(fontRenderer).setFontSize(8);
	// }
	// regularFont.print(element.shortName, left + 10 -
	// (element.shortName.length() - 1) * 5, top + 8,
	// Compendium.Color.TrueColor.white, true);
	// smallFont.print(element.atomicNumber, left, top,
	// Compendium.Color.TrueColor.white, true);
	// }
}
