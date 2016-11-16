package minechem.item.augment;

import net.minecraft.item.ItemStack;

import minechem.item.augment.augments.IAugment;

public interface IAugmentItem {
	String getAugmentKey(ItemStack stack);

	int getMaxLevel(ItemStack stack, IAugment augment, int level);

	int consumeLevel(ItemStack stack, IAugment augment, int level);

	int replenishLevel(ItemStack stack, IAugment augment, int level);
}
