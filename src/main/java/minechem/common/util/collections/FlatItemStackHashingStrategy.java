package minechem.common.util.collections;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

import gnu.trove.strategy.HashingStrategy;

public class FlatItemStackHashingStrategy implements HashingStrategy<ItemStack> {

	private static final long serialVersionUID = 1L;

	@Override
	public int computeHashCode(ItemStack stack) {
		return Objects.hash(stack.getItem(), stack.getItemDamage(), stack.getTagCompound());
	}

	@Override
	public boolean equals(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
}
