package minechem.apparatus.prefab.tileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryTileEntity extends EnergyTileEntity {

	protected final ItemStackHandler itemStackHandler;

	public InventoryTileEntity(String name, int itemSlots) {
		this(name, DEFAULT_ENERGY_CAPACITY, itemSlots);
	}
	
	public InventoryTileEntity(String name, int energyCapacity, int itemSlots) {
		super(name, energyCapacity);
		this.itemStackHandler = new ItemStackHandler(itemSlots) {
			
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				InventoryTileEntity.this.markDirty();
			}
			
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				return !InventoryTileEntity.this.canInsert(slot, stack) ? stack : super.insertItem(slot, stack, simulate);
			}
			
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				return !InventoryTileEntity.this.canExtract(slot, amount) ? null : super.extractItem(slot, amount, simulate);
			}
		};
	}

	protected boolean canInsert(int slot, ItemStack stack) {
		return true;
	}

	protected boolean canExtract(int slot, int amount) {
		return true;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) itemStackHandler;
		}
		return super.getCapability(capability, facing);
	}

}
