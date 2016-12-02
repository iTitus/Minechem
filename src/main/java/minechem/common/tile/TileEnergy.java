package minechem.common.tile;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileEnergy extends TileBase {

	public static final int DEFAULT_ENERGY_CAPACITY = 32000;

	protected final EnergyStorage energyHandler;

	public TileEnergy(String name) {
		this(name, DEFAULT_ENERGY_CAPACITY);
	}

	public TileEnergy(String name, int capacity) {
		this(name, capacity, capacity);
	}

	public TileEnergy(String name, int capacity, int maxTransfer) {
		this(name, capacity, maxTransfer, maxTransfer);
	}

	public TileEnergy(String name, int capacity, int maxReceive, int maxExtract) {
		super(name);
		this.energyHandler = new EnergyStorage(capacity, maxReceive, maxExtract);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energyHandler;
		}
		return super.getCapability(capability, facing);
	}

}
