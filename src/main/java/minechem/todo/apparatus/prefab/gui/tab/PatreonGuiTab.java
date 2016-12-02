package minechem.todo.apparatus.prefab.gui.tab;

import java.util.List;

import org.lwjgl.opengl.GL11;

import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.util.LocalizationHelper;
import minechem.common.util.StringHelper;
import minechem.todo.apparatus.prefab.gui.container.BasicGuiContainer;
import static codechicken.lib.gui.GuiDraw.fontRenderer;

public class PatreonGuiTab extends BasicGuiTab {
	public static boolean enable = Config.enablePatreon;
	private String link;
	private int linkColor;
	private String linkText;

	public PatreonGuiTab(BasicGuiContainer gui) {
		super(gui, LocalizationHelper.getLocalString("tab.patreon.text"), 0);
		this.backgroundColor = Compendium.Color.TrueColor.cyan;// I like cyan.
		this.enabled = Config.enablePatreon;
		this.link = Compendium.MetaData.patreon;
		this.linkText = LocalizationHelper.getLocalString("tab.patreon.linkText");
		this.tabTitle = "tab.patreon.headerText";
		this.tabTooltip = "tab.patreon.tooltip";
		this.linkColor = Compendium.Color.TrueColor.yellow;
	}

	@Override
	public void draw() {
		super.draw();
		if (isEnabled() && isFullyOpened()) {
			getFontRenderer().drawStringWithShadow(linkText, getLinkX(), getLinkY(), linkColor);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void addTooltip(List<String> tooltip) {
		if (isEnabled() && isFullyOpened() && isLinkAtOffsetPosition(gui.getMouseX(), gui.getMouseY())) {
			tooltip.add(link);
		}
	}

	@Override
	public String getIcon() {
		return "patreon";
	}

	public int getLinkX() {
		return posXOffset();
	}

	public int getLinkY() {
		return getPosY() + maxHeight - StringHelper.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText)) - 5;
	}

	public String getLink() {
		return link;
	}

	public boolean isLinkAtOffsetPosition(int mouseX, int mouseY) {
		if (mouseX >= getLinkX()) {
			if (mouseX <= getLinkX() + fontRenderer.getStringWidth(linkText)) {
				if (mouseY >= getLinkY()) {
					if (mouseY <= getLinkY() + StringHelper.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
