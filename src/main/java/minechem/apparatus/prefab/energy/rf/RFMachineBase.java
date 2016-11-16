package minechem.apparatus.prefab.energy.rf;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.common.Optional;

import cofh.api.energy.IEnergyReceiver;

@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHAPI|energy")
public class RFMachineBase extends RFBase implements IEnergyReceiver {
	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public boolean canConnectEnergy(ForgeDirection forgeDirection) {
		return true;
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

	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public int receiveEnergy(ForgeDirection forgeDirection, int amount, boolean doInsert) {
		return this.energy.inputEnergy(amount, doInsert);
	}
}
