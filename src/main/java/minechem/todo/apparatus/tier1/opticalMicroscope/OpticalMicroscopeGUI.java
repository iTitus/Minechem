package minechem.todo.apparatus.tier1.opticalMicroscope;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import minechem.common.Compendium;
import minechem.common.chemical.ChemicalBase;
import minechem.common.chemical.Element;
import minechem.common.chemical.Molecule;
import minechem.common.item.ItemChemical;
import minechem.common.tile.TileMicroscope;
import minechem.common.util.AchievementHelper;
import minechem.common.util.LocalizationHelper;
import minechem.common.util.ResearchHelper;
import minechem.todo.apparatus.prefab.gui.container.BasicGuiContainer;

/**
 *
 *
 */
public class OpticalMicroscopeGUI extends BasicGuiContainer {

	protected static final int eyePieceX = 13;
	protected static final int eyePieceY = 16;
	protected static final int eyePieceW = 54;
	protected static final int eyePieceH = 54;
	protected TileMicroscope opticalMicroscope;
	private ItemStack prevStack;
	private RenderItem renderItem;

	public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, TileMicroscope opticalMicroscope) {
		super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
		this.opticalMicroscope = opticalMicroscope;
		texture = Compendium.Resource.GUI.opticalMicroscope;
		name = LocalizationHelper.getLocalString("tile.opticalMicroscope.name");
		renderItem = new MicroscopeRenderItem(this);
	}

	public boolean isMouseInMicroscope() {
		return mouseX >= eyePieceX && mouseX <= eyePieceX + eyePieceW && mouseY >= eyePieceY && mouseY <= eyePieceY + eyePieceH;
	}

	private void drawMicroscopeOverlay() {
		RenderHelper.resetOpenGLColour();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		float z = this.zLevel;
		this.zLevel = 600.0F;
		drawTexturedModalRect(eyePieceX, eyePieceY, 176, 0, eyePieceH, eyePieceW);
		this.zLevel -= 10.0F;
		drawTexturedModalRect(eyePieceX, eyePieceY, 176, eyePieceH, eyePieceH, eyePieceW);
		this.zLevel = z;
	}

	private void drawInfo() {
		Slot slot = inventorySlots.getSlotFromInventory(opticalMicroscope, 0);
		if (slot.getHasStack()) {
			ItemStack itemStack = slot.getStack();
			if (itemStack.getItem() instanceof ItemChemical) {
				ChemicalBase chemicalBase = ChemicalBase.readFromNBT(itemStack.getTagCompound());
				fontRendererObj.drawString(chemicalBase.fullName, eyePieceX + eyePieceH + 5, eyePieceY, 0);
				fontRendererObj.drawString("Formula:", eyePieceX + eyePieceH + 5, eyePieceY + 10, 0);
				fontRendererObj.drawString(chemicalBase.getFormula(), eyePieceX + eyePieceH + 5, eyePieceY + 20, 0);

				if (!chemicalBase.isElement()) {
					RenderHelper.drawScaledTexturedRectUV(eyePieceX + eyePieceW + 50, eyePieceY + 5, 0, 0, 0, 200, 200, 0.3F, ((Molecule) chemicalBase).getStructureResource());
				}

				if (prevStack != itemStack) {
					prevStack = itemStack;
					if (chemicalBase.isElement()) {
						AchievementHelper.giveAchievement(getPlayer(), (Element) chemicalBase, getWorld().isRemote);
					}
					ResearchHelper.addResearch(getPlayer(), chemicalBase.getResearchKey(), getWorld().isRemote);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int i1) {
		super.drawGuiContainerForegroundLayer(i, i1);
		drawMicroscopeOverlay();
		drawInfo();
	}

	@Override
	public void drawScreen(int x, int y, float z) {
		super.drawScreen(x, y, z);
		renderItem.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), opticalMicroscope.getStackInSlot(0), x, y);
		renderItem.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), getContainer().getInventoryPlayer().getItemStack(), x, y);
	}
}
