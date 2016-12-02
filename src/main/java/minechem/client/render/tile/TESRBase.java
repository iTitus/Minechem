package minechem.client.render.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.model.ModelRenderable;
import minechem.common.block.BlockBaseContainer;
import minechem.common.tile.TileBase;

@SideOnly(Side.CLIENT)
public abstract class TESRBase<T extends TileBase, M extends ModelRenderable> extends TileEntitySpecialRenderer<T> {

	protected M model;
	protected float rotation;
	protected ResourceLocation texture;

	protected double xOffset;
	protected double yOffset;
	protected double zOffset;
	protected float xScale;
	protected float yScale;
	protected float zScale;

	public TESRBase() {
		setScale(1.0F);
	}

	public TESRBase(float scale) {
		setScale(scale);
		setRotation(0.0625F);
	}

	public TESRBase(float scale, float rotation) {
		setScale(scale);
		setRotation(rotation);
		setOffset(0.5, 1.5, 0.5);
	}

	@Override
	public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (destroyStage >= 0) {
			bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4, 4, 1);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		} else {
			bindTexture(texture);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		if (destroyStage < 0) {
			GlStateManager.color(1, 1, 1, 1);
		}
		if (te != null) {
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		GlStateManager.translate(x + xOffset, y + yOffset, z + zOffset);
		GlStateManager.scale(xScale, -yScale, -zScale);
		if (te != null && te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			state = state.getActualState(te.getWorld(), te.getPos());
			GlStateManager.rotate(state.getValue(BlockBaseContainer.FACING).getHorizontalAngle(), 0, 1, 0);
		}
		renderModel(te, x, y, z, partialTicks, destroyStage);
		if (te != null) {
			GlStateManager.disableBlend();
		}
		GlStateManager.disableRescaleNormal();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		}
	}

	protected void renderModel(T te, double x, double y, double z, float partialTicks, int destroyStage) {
		model.render(rotation);
	}

	public final void setOffset(double xOffset, double yOffset, double zOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	private void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public final void setScale(float scale) {
		this.xScale = scale;
		this.yScale = scale;
		this.zScale = scale;
	}

	public final void setScale(float xScale, float yScale, float zScale) {
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
	}

}
