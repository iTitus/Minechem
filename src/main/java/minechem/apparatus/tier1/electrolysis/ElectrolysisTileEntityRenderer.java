package minechem.apparatus.tier1.electrolysis;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;

@SideOnly(Side.CLIENT)
public class ElectrolysisTileEntityRenderer extends BasicTileEntityRenderer<ElectrolysisTileEntity, ElectrolysisModel> {

	public ElectrolysisTileEntityRenderer() {
		super(0.4F);
		setOffset(0.5, 0.625, 0.5);
		model = new ElectrolysisModel();
		texture = Compendium.Resource.Model.electrolysis;
	}

	@Override
	protected void renderModel(ElectrolysisTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te != null) {
			model.setLeftTube(te.getLeftTube() != null);
			model.setRightTube(te.getRightTube() != null);
		}
		super.renderModel(te, x, y, z, partialTicks, destroyStage);
	}

}
