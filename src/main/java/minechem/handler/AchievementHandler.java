package minechem.handler;

import net.minecraft.stats.Achievement;

import minechem.chemical.Element;
import minechem.registry.AchievementRegistry;
import minechem.registry.BlockRegistry;
import minechem.registry.ElementRegistry;
import minechem.registry.ItemRegistry;

public class AchievementHandler {

	public static void init() {
		initMinechem();
		initElements();
	}

	private static void initElements() {
		for (Element element : ElementRegistry.getInstance().getElements()) {
			AchievementRegistry.getInstance().addAchievement(element);
		}
		AchievementRegistry.getInstance().registerElementAchievements();
	}

	private static void initMinechem() {
		Achievement journal = AchievementRegistry.getInstance().addAchievement(ItemRegistry.journal.getUnlocalizedName(), 0, 0, ItemRegistry.journal);
		Achievement microscope = AchievementRegistry.getInstance().addAchievement(BlockRegistry.opticalMicroscope.getUnlocalizedName(), 1, 3, BlockRegistry.opticalMicroscope, journal);
		Achievement centrifuge = AchievementRegistry.getInstance().addAchievement(BlockRegistry.centrifuge.getUnlocalizedName(), -2, 3, BlockRegistry.centrifuge, microscope);
		Achievement crucible = AchievementRegistry.getInstance().addAchievement(BlockRegistry.electricCrucible.getUnlocalizedName(), -2, 3, BlockRegistry.electricCrucible, microscope);
		Achievement electrolysis = AchievementRegistry.getInstance().addAchievement(BlockRegistry.electrolysis.getUnlocalizedName(), 3, 3, BlockRegistry.electrolysis, microscope);
		AchievementRegistry.getInstance().registerMinechemAchievements();
	}
}
