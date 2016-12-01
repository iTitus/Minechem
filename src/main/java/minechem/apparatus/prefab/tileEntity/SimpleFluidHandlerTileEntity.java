package minechem.apparatus.prefab.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class SimpleFluidHandlerTileEntity extends InventoryTileEntity {

	protected final FluidTank fluidHandler;

	public SimpleFluidHandlerTileEntity(String name, int size, int capacity) {
		super(name, size);
		this.fluidHandler = new FluidTank(capacity) {

			@Override
			protected void onContentsChanged() {
				super.onContentsChanged();
				SimpleFluidHandlerTileEntity.this.markDirty();
			}
		};
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		if (compound.hasKey("fluid", Constants.NBT.TAG_COMPOUND)) {
			fluidHandler.readFromNBT(compound.getCompoundTag("fluid"));
		}
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		compound.setTag("fluid", fluidHandler.writeToNBT(new NBTTagCompound()));
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) fluidHandler;
		}
		return super.getCapability(capability, facing);
	}

}
