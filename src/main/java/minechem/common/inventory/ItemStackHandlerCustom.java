package minechem.common.inventory;

import minechem.common.tile.TileInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerCustom extends ItemStackHandler {

	protected final TileInventory tile;
	
	public ItemStackHandlerCustom(TileInventory tile) {
		this(1, tile);
	}

	public ItemStackHandlerCustom(int size, TileInventory tile) {
		super(size);
		this.tile = tile;
	}

	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		tile.markDirty();
	}
	
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return tile.canInsertItem(slot, stack, null) ? super.insertItem(slot, stack, simulate) : stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return tile.canExtractItem(slot, getStackInSlot(slot), null) ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }
    
    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
    	return tile.getStackLimit(slot, stack);
    }

}
