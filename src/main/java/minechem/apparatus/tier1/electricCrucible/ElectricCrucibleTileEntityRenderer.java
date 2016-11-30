package minechem.apparatus.tier1.electricCrucible;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;

@SideOnly(Side.CLIENT)
public class ElectricCrucibleTileEntityRenderer extends BasicTileEntityRenderer<ElectricCrucibleTileEntity, ElectricCrucibleModel> {

	public ElectricCrucibleTileEntityRenderer() {
		super(0.25F);
		setOffset(0.5, 0.36D, 0.5);
		model = new ElectricCrucibleModel();
		texture = Compendium.Resource.Model.electricCrucible;
	}

}
