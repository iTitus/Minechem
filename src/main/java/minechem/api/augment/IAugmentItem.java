package minechem.api.augment;

import net.minecraft.item.ItemStack;

public interface IAugmentItem {

	String getAugmentKey(ItemStack stack);

	int getMaxLevel(ItemStack stack, IAugment augment, int level);

	int consumeLevel(ItemStack stack, IAugment augment, int level);

	int replenishLevel(ItemStack stack, IAugment augment, int level);
}
