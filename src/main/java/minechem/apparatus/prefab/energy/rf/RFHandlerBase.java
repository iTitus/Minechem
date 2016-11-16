package minechem.apparatus.prefab.energy.rf;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.common.Optional;

import cofh.api.energy.IEnergyProvider;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI|energy")
public class RFHandlerBase extends RFMachineBase implements IEnergyProvider {
	@Optional.Method(modid = "CoFHAPI|energy")
	@Override
	public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean doExtract) {
		return this.energy.extractEnergy(amount, doExtract);
	}
}
