package minechem.common.util.collections;

import net.minecraft.item.ItemStack;

import gnu.trove.strategy.HashingStrategy;

public class ItemStackHashingStrategy implements HashingStrategy<ItemStack> {

	private static final long serialVersionUID = 1L;

	@Override
	public int computeHashCode(ItemStack stack) {
		return stack.getItem().hashCode() ^ stack.getItemDamage() ^ (stack.getCount() << 16) ^ (stack.hasTagCompound() ? stack.getTagCompound().hashCode() : 0);
	}

	@Override
	public boolean equals(ItemStack stack1, ItemStack stack2) {
		return ItemStack.areItemStacksEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
}
