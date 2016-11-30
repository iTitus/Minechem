package minechem.apparatus.prefab.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.apparatus.prefab.model.BasicModel;
import minechem.apparatus.prefab.tileEntity.BaseTileEntity;

@SideOnly(Side.CLIENT)
public abstract class BasicTileEntityRenderer<T extends BaseTileEntity, M extends BasicModel> extends TileEntitySpecialRenderer<T> {

	protected M model;
	protected float rotation;
	protected ResourceLocation texture;

	protected double xOffset;
	protected double yOffset;
	protected double zOffset;
	protected float xScale;
	protected float yScale;
	protected float zScale;

	public BasicTileEntityRenderer() {
		setScale(1.0F);
	}

	public BasicTileEntityRenderer(float scale) {
		setScale(scale);
		setRotation(0.0625F);
	}

	public BasicTileEntityRenderer(float scale, float rotation) {
		setScale(scale);
		setRotation(rotation);
		setOffset(0.5, 1.5, 0.5);
	}

	@Override
	public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(GL11.GL_LEQUAL);
		GlStateManager.depthMask(true);

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
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(x + xOffset, y + yOffset, z + zOffset);
		GlStateManager.scale(xScale, -yScale, -zScale);
		if (te != null && te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			state = state.getActualState(te.getWorld(), te.getPos());
			GlStateManager.rotate(state.getValue(BasicBlockContainer.FACING).getHorizontalAngle(), 0, 1, 0);
		}
		renderModel(te, x, y, z, partialTicks, destroyStage);
		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1, 1, 1, 1);

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
