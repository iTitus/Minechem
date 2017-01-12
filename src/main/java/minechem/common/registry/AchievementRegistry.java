package minechem.common.registry;

import java.util.Map;
import com.google.common.collect.Maps;

import minechem.common.Compendium;
import minechem.common.achievement.ElementAchievement;
import minechem.common.achievement.ElementAchievementPage;
import minechem.common.block.BlockBase;
import minechem.common.chemical.Element;
import minechem.common.item.ItemBase;
import minechem.common.util.PeriodicTableHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementRegistry {

	private static Map<Object, Achievement> achievements = Maps.newHashMap();

	public static Achievement journal, microscope, centrifuge, crucible, electrolysis;

	public static void init() {
		journal = register(0, -2, ItemRegistry.journal, null, false);
		microscope = register(0, 0, BlockRegistry.opticalMicroscope, journal, false);
		centrifuge = register(-2, 0, BlockRegistry.centrifuge, microscope, false);
		crucible = register(0, 2, BlockRegistry.electricCrucible, microscope, false);
		electrolysis = register(2, 0, BlockRegistry.electrolysis, microscope, false);
		
		for (Element element : ElementRegistry.getInstance().getElements()) {
			int[] pos = PeriodicTableHelper.getPosition(element);
			register(pos[0] - 3, pos[1] - 2, element);
		}
		
		AchievementPage.registerAchievementPage(new AchievementPage(Compendium.Naming.name, achievements.entrySet().stream().filter(entry -> !(entry.getKey() instanceof Element)).map(Map.Entry::getValue).toArray(Achievement[]::new)));
		AchievementPage.registerAchievementPage(new ElementAchievementPage(achievements.entrySet().stream().filter(entry -> entry.getKey() instanceof Element).map(Map.Entry::getValue).toArray(Achievement[]::new)));
	}

	private static Achievement register(int x, int y, ItemBase item, Achievement parent, boolean isSpecial) {
		Achievement achievement = register(Compendium.Naming.id + ":" + item.getUnlocalizedName(), x, y, new ItemStack(item), parent, isSpecial);
		achievements.put(item, achievement);
		return achievement;
	}

	private static Achievement register(int x, int y, BlockBase block, Achievement parent, boolean isSpecial) {
		Achievement achievement = register(Compendium.Naming.id + ":" + block.getUnlocalizedName(), x, y, new ItemStack(block), parent, isSpecial);
		achievements.put(block, achievement);
		return achievement;
	}

	private static Achievement register(int x, int y, Element element) {
		Achievement achievement = new ElementAchievement(element, x, y);
		achievements.put(element, achievement);
		return achievement.initIndependentStat().registerStat();
	}

	private static Achievement register(String name, int x, int y, ItemStack stack, Achievement parent, boolean isSpecial) {
		Achievement achievement = new Achievement("achievement." + name, name, x, y, stack, parent);
		if (isSpecial) {
			achievement.setSpecial();
		}
		if (parent == null) {
			achievement.initIndependentStat();
		}
		return achievement.registerStat();
	}
	
	public static Achievement getAchievement(Object object) {
		return achievements.get(object);
	}

}
