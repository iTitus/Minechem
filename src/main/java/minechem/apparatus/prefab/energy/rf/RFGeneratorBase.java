package minechem.apparatus.prefab.energy.rf;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.common.Optional;

import cofh.api.energy.IEnergyProvider;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI|energy")
public class RFGeneratorBase extends RFBase implements IEnergyProvider {
	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public boolean canConnectEnergy(ForgeDirection forgeDirection) {
		return true;
	}

	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean doExtract) {
		return this.energy.extractEnergy(amount, doExtract);
	}

	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public int getEnergyStored(ForgeDirection forgeDirection) {
		return this.energy.getStored();
	}

	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public int getMaxEnergyStored(ForgeDirection forgeDirection) {
		return this.energy.getCapacity();
	}
}
