package minechem.apparatus.tier1.opticalMicroscope;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;

@SideOnly(Side.CLIENT)
public class OpticalMicroscopeTileEntityRenderer extends BasicTileEntityRenderer<OpticalMicroscopeTileEntity, OpticalMicroscopeModel> {

	public OpticalMicroscopeTileEntityRenderer() {
		super(0.4F);
		setOffset(0.5, 0.625, 0.5);
		model = new OpticalMicroscopeModel();
		texture = Compendium.Resource.Model.microscope;
	}

}
