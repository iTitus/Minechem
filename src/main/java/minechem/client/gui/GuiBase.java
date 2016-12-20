package minechem.client.gui;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import minechem.client.gui.widget.WidgetBase;
import minechem.common.inventory.container.ContainerBase;
import minechem.common.tile.TileBase;

public class GuiBase<T extends TileBase, C extends ContainerBase<T>> extends GuiContainer {

	protected final Minecraft mc;
	protected final C container;
	protected final ResourceLocation texture;
	protected final int xSize, ySize;
	protected final List<WidgetBase> widgets = Lists.newArrayList();

	public GuiBase(C container, int xSize, int ySize, ResourceLocation texture) {
		super(container);
		this.mc = Minecraft.getMinecraft();
		this.container = container;
		this.texture = texture;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	protected void addWidget(WidgetBase widget) {
		widgets.add(widget);
	}

	public void bindTexture(ResourceLocation texture) {
		mc.getTextureManager().bindTexture(texture);
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = getTile().getDisplayName().getUnformattedText();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 0x404040);
		fontRendererObj.drawString(getPlayerInventory().getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 0x404040);

		for (WidgetBase widget : widgets) {
			GlStateManager.color(1f, 1f, 1f);
			widget.drawForeground(mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for (WidgetBase widget : widgets) {
			GlStateManager.color(1f, 1f, 1f);
			widget.drawBackground(partialTicks, mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (WidgetBase widget : widgets) {
			if (widget.getBounds().contains(mouseX - guiLeft, mouseY - guiTop)) {
				widget.onClick(mouseButton);
			}
		}
	}

	public float getZLevel() {
		return zLevel;
	}

	public void setZLevel(float zLevel) {
		this.zLevel = zLevel;
	}

	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}

	public T getTile() {
		return container.getTile();
	}

	public C getContainer() {
		return container;
	}

	public InventoryPlayer getPlayerInventory() {
		return container.getPlayerInventory();
	}

	public RenderItem getRenderItem() {
		return itemRender;
	}

	public EntityPlayer getPlayer() {
		return container.getPlayer();
	}
}
