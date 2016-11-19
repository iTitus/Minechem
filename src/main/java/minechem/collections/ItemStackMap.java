package minechem.collections;

import net.minecraft.item.ItemStack;

import gnu.trove.map.hash.TCustomHashMap;
import minechem.collections.strategy.FlatItemStackHashingStrategy;
import minechem.collections.strategy.ItemStackHashingStrategy;

/**
 * A HashMap&lt;ItemStack, V&gt; that implements a proper hashing function on ItemStacks, allowing them to be stored and queried in a HashMap Example usage: ItemStackMap&lt;ItemStack>&gt; stackMap =
 * new ItemStackMap&lt;ItemStack&gt; stackMap.put(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone)); stackMap.contains(new ItemStack(Blocks.cobblestone)); (returns true)
 *
 * @param <V>
 */
public class ItemStackMap<V> extends TCustomHashMap<ItemStack, V> {

	private static final ItemStackHashingStrategy HASHING_STRATEGY = new ItemStackHashingStrategy();
	private static final FlatItemStackHashingStrategy FLAT_HASHING_STRATEGY = new FlatItemStackHashingStrategy();

	public ItemStackMap() {
		this(false);
	}

	public ItemStackMap(boolean flat) {
		super(flat ? FLAT_HASHING_STRATEGY : HASHING_STRATEGY);
	}

	public ItemStackMap<V> copy() {
		ItemStackMap<V> copy = new ItemStackMap<>();
		copy.strategy = this.strategy;
		copy.putAll(this);
		return copy;
	}
}
