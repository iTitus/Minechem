package minechem.apparatus.prefab.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryTileEntity extends BaseTileEntity {

	protected final ItemStackHandler itemStackHandler;

	public InventoryTileEntity(String name, int size) {
		super(name);
		this.itemStackHandler = new ItemStackHandler(size) {
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				InventoryTileEntity.this.markDirty();
			}
		};
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		if (compound.hasKey("items", Constants.NBT.TAG_COMPOUND)) {
			itemStackHandler.deserializeNBT(compound.getCompoundTag("items"));
		}
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		return compound;
	}

	public boolean canInteractWith(EntityPlayer player) {
		return !isInvalid() && player.getDistanceSqToCenter(pos) <= 64;
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
