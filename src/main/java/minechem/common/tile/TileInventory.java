package minechem.common.tile;

import java.util.stream.IntStream;

import minechem.common.inventory.ItemStackHandlerCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileInventory extends TileEnergy implements ISidedInventory {

	protected final ItemStackHandlerCustom stackHandler;

	public TileInventory(String name, int itemSlots) {
		this(name, DEFAULT_ENERGY_CAPACITY, itemSlots);
	}

	public TileInventory(String name, int energyCapacity, int itemSlots) {
		super(name, energyCapacity);
		this.stackHandler = new ItemStackHandlerCustom(itemSlots, this);
	}

	public boolean canExtract(int slot, ItemStack stack){
        return true;
    }

	public ItemStackHandlerCustom getStackHandler() {
		return stackHandler;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) stackHandler: super.getCapability(capability, facing);
	}

	@Override
	public int getSizeInventory() {
		return stackHandler.getSlots();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++) {
			if (!stackHandler.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stackHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return stackHandler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		stackHandler.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return stackHandler.getSlotLimit(0);
	}

	@Override
	public void openInventory(EntityPlayer player) {	
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < getSizeInventory(); i++) {
			setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return IntStream.range(0, getSizeInventory()).toArray();
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return canExtract(index, stack);
	}

	public int getStackLimit(int slot, ItemStack stack) {
		return Math.min(getInventoryStackLimit(), stack.getMaxStackSize());
	}
}
