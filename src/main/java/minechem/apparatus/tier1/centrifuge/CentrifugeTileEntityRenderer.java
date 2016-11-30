package minechem.apparatus.tier1.centrifuge;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;

@SideOnly(Side.CLIENT)
public class CentrifugeTileEntityRenderer extends BasicTileEntityRenderer<CentrifugeTileEntity, CentrifugeModel> {

	public CentrifugeTileEntityRenderer() {
		super(0.25F);
		setOffset(0.5, 0.625, 0.5);
		model = new CentrifugeModel();
		texture = Compendium.Resource.Model.centrifuge;
	}

}
