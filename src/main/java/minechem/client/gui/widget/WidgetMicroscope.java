package minechem.client.gui.widget;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import minechem.client.gui.GuiMicroscope;
import minechem.client.util.RenderHelper;
import minechem.common.Compendium;
import minechem.common.chemical.ChemicalBase;
import minechem.common.chemical.Molecule;
import minechem.common.item.ItemChemical;
import minechem.common.tile.TileMicroscope;

public class WidgetMicroscope extends WidgetBase {

	public WidgetMicroscope(int x, int y, GuiMicroscope gui) {
		super(x, y, 54, 54, 176, 0, Compendium.Resource.GUI.opticalMicroscope, gui);
	}

	@Override
	public void drawBackground(float partialTicks, int mouseX, int mouseY) {
		gui.bindTexture(texture);
		float zLevel = gui.getZLevel();
		gui.setZLevel(200);
		gui.drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), u, v, bounds.getWidth(), bounds.getHeight());
		gui.setZLevel(zLevel);
		gui.drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), u, v + bounds.getHeight(), bounds.getWidth(), bounds.getHeight());

		ItemStack stack = ((TileMicroscope) gui.getTile()).getItemStackHandler().getStackInSlot(0);
		ChemicalBase chemicalBase = ItemChemical.getChemicalBase(stack);

		if (chemicalBase == null) {
			return;
		}

		if (!chemicalBase.isElement()) {
			RenderHelper.drawScaledTexturedRectUV(gui.getGuiLeft() + bounds.getX() + bounds.getWidth(), gui.getGuiTop() + bounds.getY(), 0, 0, 0, 200, 200, 0.3F, ((Molecule) chemicalBase).getStructureResource());
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), 0);
		GlStateManager.scale(3, 3, 1);
		zLevel = gui.getRenderItem().zLevel;
		gui.getRenderItem().zLevel = 1;
		gui.getRenderItem().renderItemAndEffectIntoGUI(gui.getPlayer(), stack, 1, 1);
		gui.getRenderItem().zLevel = zLevel;
		GlStateManager.popMatrix();
	}

}
