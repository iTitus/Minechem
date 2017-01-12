package minechem.common.achievement;

import net.minecraft.stats.Achievement;

import net.minecraftforge.common.AchievementPage;

//TODO: Fancy Achievement Render
public class ElementAchievementPage extends AchievementPage /* implements IAchievementPageRenderer */ {

	public ElementAchievementPage(Achievement... achievements) {
		super("Periodic Table", achievements);
	}

	// @Override
	// public void drawBackground(Minecraft mc, float z, float scale, int
	// columnWidth, int rowHeight) {
	// int k = (columnWidth + 288);
	// int l = (rowHeight + 288);
	// RenderHelper.setScissor(254, 200, 0, 0, 250, 200);
	// RenderHelper.drawTexturedRectUV(-10 - k, 50 - l, z, 0, 0, 640, 480, 640,
	// 480, Compendium.Resource.GUI.achievements);
	// RenderHelper.stopScissor();
	// }
	//
	// @Override
	// public float setScaleOnLoad() {
	// return 2.0F;
	// }
	//
	// @Override
	// public float getMaxZoomOut() {
	// return 3.0F;
	// }
	//
	// @Override
	// public float getMaxZoomIn() {
	// return 1.0F;
	// }
}
