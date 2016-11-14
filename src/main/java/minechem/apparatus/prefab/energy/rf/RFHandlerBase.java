package minechem.apparatus.prefab.energy.rf;

import cofh.api.energy.IEnergyProvider;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI|energy")
public class RFHandlerBase extends RFMachineBase implements IEnergyProvider
{
    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean doExtract)
    {
        return this.energy.extractEnergy(amount, doExtract);
    }
}
