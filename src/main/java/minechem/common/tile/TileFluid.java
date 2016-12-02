package minechem.common.tile;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileFluid extends TileInventory {

	public static final int DEFAULT_FLUID_CAPACITY = 16 * FluidContainerRegistry.BUCKET_VOLUME;

	protected final FluidTank fluidHandler;

	public TileFluid(String name, int itemSlots) {
		this(name, itemSlots, DEFAULT_FLUID_CAPACITY);
	}

	public TileFluid(String name, int itemSlots, int fluidCapacity) {
		this(name, DEFAULT_ENERGY_CAPACITY, itemSlots, fluidCapacity);
	}

	public TileFluid(String name, int energyCapacity, int itemSlots, int fluidCapacity) {
		super(name, energyCapacity, itemSlots);
		this.fluidHandler = new FluidTank(fluidCapacity) {

			@Override
			protected void onContentsChanged() {
				super.onContentsChanged();
				TileFluid.this.markDirty();
			}

			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return TileFluid.this.canFill(fluid);
			}

			@Override
			public boolean canDrainFluidType(FluidStack fluid) {
				return TileFluid.this.canDrain(fluid);
			}
		};
		this.fluidHandler.setTileEntity(this);
	}

	protected boolean canDrain(FluidStack fluidStack) {
		return true;
	}

	protected boolean canFill(FluidStack fluidStack) {
		return true;
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
