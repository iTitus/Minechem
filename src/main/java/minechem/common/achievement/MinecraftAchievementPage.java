package minechem.common.achievement;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;

import minechem.client.util.RenderHelper;
import minechem.common.asm.MinechemHooks;

public class MinecraftAchievementPage {

	public static void drawBackground(Minecraft mc, float z, float scale, int columnWidth, int rowHeight) {

		int i = columnWidth + 288 >> 4;
		int j = rowHeight + 288 >> 4;
		int k = (columnWidth + 288) % 16;
		int l = (rowHeight + 288) % 16;
		Random random = new Random();
		float scaled = 16.0F / scale;
		int row, column, icon;

		for (row = 0; row * scaled - l < 155.0F; ++row) {
			float grayScale = 0.6F - (j + row) / 25.0F * 0.3F;
			MinechemHooks.resetGreyscale(grayScale);

			for (column = 0; column * scaled - k < 224.0F; ++column) {
				random.setSeed(mc.getSession().getPlayerID().hashCode() + i + column + (j + row) * 16);
				icon = random.nextInt(1 + j + row) + (j + row) / 2;
				TextureAtlasSprite textureatlassprite = getTexture(Blocks.SAND);

				if (icon <= 37 && j + row != 35) {
					if (icon == 22) {
						if (random.nextInt(2) == 0) {
							textureatlassprite = getTexture(Blocks.DIAMOND_ORE);
						} else {
							textureatlassprite = getTexture(Blocks.REDSTONE_ORE);
						}
					} else if (icon == 10) {
						textureatlassprite = getTexture(Blocks.IRON_ORE);
					} else if (icon == 8) {
						textureatlassprite = getTexture(Blocks.COAL_ORE);
					} else if (icon > 4) {
						textureatlassprite = getTexture(Blocks.STONE);
					} else if (icon > 0) {
						textureatlassprite = getTexture(Blocks.DIRT);
					}
				} else {
					Block block = Blocks.BEDROCK;
					textureatlassprite = getTexture(block);
				}

				mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				RenderHelper.drawTexturedRectUV(column * 16 - k, row * 16 - l, z, 16, 16, textureatlassprite);
			}
		}
	}

	private static TextureAtlasSprite getTexture(Block block) {
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState());
	}
}
