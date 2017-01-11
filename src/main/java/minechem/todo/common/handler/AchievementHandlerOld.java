package minechem.todo.common.handler;

import net.minecraft.stats.Achievement;
import minechem.common.chemical.Element;
import minechem.common.registry.BlockRegistry;
import minechem.common.registry.ElementRegistry;
import minechem.common.registry.ItemRegistry;
import minechem.todo.common.registry.AchievementRegistryOld;

public class AchievementHandlerOld {

	public static void init() {
		initMinechem();
		initElements();
	}

	private static void initElements() {
		for (Element element : ElementRegistry.getInstance().getElements()) {
			AchievementRegistryOld.getInstance().addAchievement(element);
		}
		AchievementRegistryOld.getInstance().registerElementAchievements();
	}

	private static void initMinechem() {
		Achievement journal = AchievementRegistryOld.getInstance().addAchievement(ItemRegistry.journal.getUnlocalizedName(), 0, 0, ItemRegistry.journal);
		Achievement microscope = AchievementRegistryOld.getInstance().addAchievement(BlockRegistry.opticalMicroscope.getUnlocalizedName(), 1, 3, BlockRegistry.opticalMicroscope, journal);
		Achievement centrifuge = AchievementRegistryOld.getInstance().addAchievement(BlockRegistry.centrifuge.getUnlocalizedName(), -2, 3, BlockRegistry.centrifuge, microscope);
		Achievement crucible = AchievementRegistryOld.getInstance().addAchievement(BlockRegistry.electricCrucible.getUnlocalizedName(), -2, 3, BlockRegistry.electricCrucible, microscope);
		Achievement electrolysis = AchievementRegistryOld.getInstance().addAchievement(BlockRegistry.electrolysis.getUnlocalizedName(), 3, 3, BlockRegistry.electrolysis, microscope);
		AchievementRegistryOld.getInstance().registerMinechemAchievements();
	}
}
