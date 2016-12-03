package minechem.client.gui.widget;

import org.lwjgl.util.Rectangle;

import net.minecraft.util.ResourceLocation;

import minechem.client.gui.GuiBase;
import minechem.common.inventory.container.ContainerBase;
import minechem.common.tile.TileBase;

public class WidgetBase {

	protected final Rectangle bounds;
	protected final GuiBase<? extends TileBase, ? extends ContainerBase<? extends TileBase>> gui;
	protected final int u, v;
	protected final ResourceLocation texture;

	public WidgetBase(int x, int y, int w, int h, ResourceLocation texture, GuiBase<? extends TileBase, ? extends ContainerBase<? extends TileBase>> gui) {
		this(x, y, w, h, 0, 0, texture, gui);
	}

	public WidgetBase(int x, int y, int w, int h, int u, int v, ResourceLocation texture, GuiBase<? extends TileBase, ? extends ContainerBase<? extends TileBase>> gui) {
		this.bounds = new Rectangle(x, y, w, h);
		this.u = u;
		this.v = v;
		this.gui = gui;
		this.texture = texture;
	}

	public void drawForeground(int mouseX, int mouseY) {
	}

	public void drawBackground(float partialTicks, int mouseX, int mouseY) {
		if (texture == null) {
			return;
		}
		gui.bindTexture(texture);
		gui.drawTexturedModalRect(gui.getGuiLeft() + bounds.getX(), gui.getGuiTop() + bounds.getY(), u, v, bounds.getWidth(), bounds.getHeight());
	}

	public void onClick(int mouseButton) {
	}

	public Rectangle getBounds() {
		return bounds;
	}
}