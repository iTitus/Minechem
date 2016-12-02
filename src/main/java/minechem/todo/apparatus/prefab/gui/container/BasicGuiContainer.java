package minechem.todo.apparatus.prefab.gui.container;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Optional;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import minechem.client.handler.IconHandler;
import minechem.client.util.GuiIntersectHelper;
import minechem.common.Compendium.Texture.IIcon;
import minechem.common.util.LinkHelper;
import minechem.todo.apparatus.prefab.gui.tab.BasicGuiTab;
import minechem.todo.apparatus.prefab.gui.tab.PatreonGuiTab;

/**
 * Basic GUI container class for extending
 */
@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = "NotEnoughItems")
public class BasicGuiContainer extends GuiBase implements INEIGuiHandler {
	protected ResourceLocation backgroundTexture;
	protected IIconRegister register;
	protected IIcon tabIcon;
	private BasicContainer container;

	public BasicGuiContainer(BasicContainer container) {
		super(container);
		this.container = container;
	}

	public BasicContainer getContainer() {
		return container;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	public World getWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}

	public List<BasicGuiTab> getTabs() {
		List<BasicGuiTab> basicGuiTabs = new ArrayList<BasicGuiTab>();
		for (TabBase tabBase : tabs) {
			if (tabBase instanceof BasicGuiTab) {
				basicGuiTabs.add((BasicGuiTab) tabBase);
			}
		}
		return basicGuiTabs;
	}

	@Override
	public IIcon getIcon(String paramString) {
		return IconHandler.getIcon(paramString);
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
		return null;
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item) {
		return null;
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public boolean handleDragNDrop(GuiContainer gui, int mouseX, int mouseY, ItemStack draggedStack, int button) {
		return false;
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
		if (gui instanceof BasicGuiContainer) {
			GuiIntersectHelper item = new GuiIntersectHelper(x, y, w, h);
			BasicGuiContainer container = (BasicGuiContainer) gui;
			for (BasicGuiTab tab : getTabs()) {
				GuiIntersectHelper tabRect = new GuiIntersectHelper(tab.getCurrentShiftX() + container.guiLeft, tab.getCurrentShiftY() + container.guiTop, tab.currentWidth, tab.currentHeight);
				if (item.intersectsWith(tabRect)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();
		addTab(new PatreonGuiTab(this));
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData visiblityData) {
		return visiblityData;
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {

		TabBase guiTab = getTabAtPosition(mouseX, mouseY);

		if (guiTab instanceof PatreonGuiTab) {
			PatreonGuiTab patreonTab = (PatreonGuiTab) guiTab;

			if (patreonTab.isFullyOpened()) {
				if (patreonTab.isLinkAtOffsetPosition(x - this.guiLeft, y - this.guiTop)) {
					LinkHelper.openLink(patreonTab.getLink(), this);
					// return here so the machine tab doesn't get closed
					return;
				}
			}
		}
		super.mouseClicked(x, y, mouseButton);
	}
}
