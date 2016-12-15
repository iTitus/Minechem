package minechem.todo.common.augment;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AugmentReinforced extends AugmentBase {

	public AugmentReinforced() {
		super("reinforced");
	}

	@Override
	public int getVolumeConsumed(int level) {
		return level * 4 + 1;
	}

	@Override
	public float getDamageChance(ItemStack stack, int level) {
		consumeAugment(stack, level);
		return (level + 1) * 0.08F;
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world, int prevEntityLifespan, int level) {
		return prevEntityLifespan + level * 1000;
	}

}
