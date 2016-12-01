package minechem.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import minechem.helper.ColourHelper;

/**
 * Holds short hands for common used {@link org.lwjgl.opengl.GL11} methods
 */
public class RenderHelper extends net.minecraft.client.renderer.RenderHelper {

	/**
	 * Executes GL11.glColor4f() for given int colour
	 *
	 * @param colour in int form
	 */
	public static void setOpenGLColour(int colour) {
		GL11.glColor4f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour), ColourHelper.getAlpha(colour));
	}

	/**
	 * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
	 */
	public static void resetOpenGLColour() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Executes GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F)
	 *
	 * @param greyscale the greyscale in float form where 0.0F is black and 1.0F is white
	 */
	public static void setGreyscaleOpenGLColour(float greyscale) {
		GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F);
	}

	/**
	 * Enables GL11.GL_BLEND
	 */
	public static void enableBlend() {
		GL11.glEnable(GL11.GL_BLEND);
	}

	/**
	 * Disables GL11.GL_BLEND
	 */
	public static void disableBlend() {
		GL11.glDisable(GL11.GL_BLEND);
	}

	/**
	 * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity) Used in combination with blend
	 *
	 * @param opacity the opacity in float form where 1.0F is opaque and 0.0F is transparent
	 */
	public static void setOpacity(float opacity) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);
	}

	/**
	 * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F) back to full opaque
	 */
	public static void resetOpacity() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Draws a {@link net.minecraft.util.IIcon} on given x, y, z
	 *
	 * @param x    xPos
	 * @param y    yPos
	 * @param z    zPos
	 * @param w    icon width
	 * @param h    icon height
	 * @param icon the {@link net.minecraft.util.IIcon}
	 */
	public static void drawTexturedRectUV(double x, double y, double z, double w, double h, TextureAtlasSprite textureSprite) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x + 0, y + h, z).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
		vertexbuffer.pos(x + w, y + h, z).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
		vertexbuffer.pos(x + w, y + 0, z).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
		vertexbuffer.pos(x + 0, y + 0, z).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
		tessellator.draw();
	}

	/**
	 * Draw a {@link net.minecraft.util.ResourceLocation} on given coords
	 *
	 * @param x        xPos
	 * @param y        yPos
	 * @param z        zPos
	 * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param w        width
	 * @param h        height
	 * @param resource the {@link net.minecraft.util.ResourceLocation}
	 */
	public static void drawTexturedRectUV(double x, double y, double z, double u, double v, double w, double h, ResourceLocation resource) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		float textScale = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x, y + h, z).tex(u * textScale, (v + h) * textScale).endVertex();
		vertexbuffer.pos(x + w, y + h, z).tex((u + w) * textScale, (v + h) * textScale).endVertex();
		vertexbuffer.pos(x + w, y, z).tex((u + w) * textScale, y * textScale).endVertex();
		vertexbuffer.pos(x, y, z).tex(u * textScale, v * textScale).endVertex();
		tessellator.draw();
	}

	/**
	 * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given scaling
	 *
	 * @param x        xPos
	 * @param y        yPos
	 * @param z        zPos
	 * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param w        width
	 * @param h        height
	 * @param scale    the scale to draw 1.0F is exact, less is smaller and bigger is larger
	 * @param resource the {@link net.minecraft.util.ResourceLocation}
	 */
	public static void drawScaledTexturedRectUV(double x, double y, double z, double u, double v, double w, double h, double scale, ResourceLocation resource) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		double drawH = h * scale;
		double drawW = w * scale;
		double xScale = 1 / w;
		double yScale = 1 / h;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x, y + drawH, z).tex(u * xScale, (v + h) * yScale).endVertex();
		vertexbuffer.pos(x + drawW, y + drawH, z).tex((u + w) * xScale, (v + h) * yScale).endVertex();
		vertexbuffer.pos(x + drawW, y, z).tex((u + w) * xScale, v * yScale).endVertex();
		vertexbuffer.pos(x, y, z).tex(u * xScale, v * yScale).endVertex();
		tessellator.draw();
	}

	/**
	 * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given draw width and draw height
	 *
	 * @param x        xPos
	 * @param y        yPos
	 * @param z        zPos
	 * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
	 * @param w        actual width
	 * @param h        actual height
	 * @param drawW    the draw width
	 * @param drawH    the draw height
	 * @param resource the {@link net.minecraft.util.ResourceLocation}
	 */
	public static void drawTexturedRectUV(double x, double y, double z, double u, double v, double w, double h, double drawW, double drawH, ResourceLocation resource) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		double xScale = 1 / w;
		double yScale = 1 / h;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x, y + drawH, z).tex(u * xScale, (v + h) * yScale).endVertex();
		vertexbuffer.pos(x + drawW, y + drawH, z).tex((u + w) * xScale, (v + h) * yScale).endVertex();
		vertexbuffer.pos(x + drawW, y, z).tex((u + w) * xScale, v * yScale).endVertex();
		vertexbuffer.pos(x, y, z).tex(u * xScale, v * yScale).endVertex();
		tessellator.draw();
	}

	/**
	 * Draw a texture in 2D using {@link net.minecraft.client.renderer.ItemRenderer#renderItemIn2D(net.minecraft.client.renderer.Tessellator, float, float, float, float, int, int, float)}
	 *
	 * @param texture the {@link net.minecraft.util.IIcon}
	 */
	public static void drawTextureIn2D(TextureAtlasSprite texture) {
		Tessellator tessellator = Tessellator.getInstance();
		//TODO: Fix
		//ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), 0.0625F);
	}

	/**
	 * Draws a texture in 3D using {@link net.minecraft.client.renderer.ItemRenderer#renderItemIn2D(net.minecraft.client.renderer.Tessellator, float, float, float, float, int, int, float)}
	 *
	 * @param texture the {@link net.minecraft.util.IIcon}
	 */
	public static void drawTextureIn3D(TextureAtlasSprite texture) {
		Tessellator tessellator = Tessellator.getInstance();
		float scale = 0.7F;
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(-scale / 2, -scale / 2, 0.0F);
		//TODO: Fix
		//ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .0625F);
		GL11.glPopMatrix();
	}

	/**
	 * Start a GL_SCISSOR_TEST
	 *
	 * @param guiWidth  {@link net.minecraft.client.gui.GuiScreen#width}
	 * @param guiHeight {@link net.minecraft.client.gui.GuiScreen#height}
	 * @param x         xPos to start scissor
	 * @param y         yPos to start scissor
	 * @param w         width of the scissor
	 * @param h         height of the scissor
	 */
	public static void setScissor(int guiWidth, int guiHeight, float x, float y, int w, int h) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledRes = new ScaledResolution(mc);
		int scale = scaledRes.getScaleFactor();
		x *= scale;
		y *= scale;
		w *= scale;
		h *= scale;
		float guiScaledWidth = (guiWidth * scale);
		float guiScaledHeight = (guiHeight * scale);
		float guiLeft = ((mc.displayWidth / 2) - guiScaledWidth / 2);
		float guiTop = ((mc.displayHeight / 2) + guiScaledHeight / 2);
		int scissorX = Math.round((guiLeft + x));
		int scissorY = Math.round(((guiTop - h) - y));
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(scissorX, scissorY, w, h);
	}

	/**
	 * Stop a GL_SCISSOR_TEST
	 */
	public static void stopScissor() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	/**
	 * Bind given texture
	 *
	 * @param texture
	 */
	public static void bindTexture(ResourceLocation texture) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
}
