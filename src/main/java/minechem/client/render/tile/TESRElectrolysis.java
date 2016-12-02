package minechem.client.render.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.model.ModelElectrolysis;
import minechem.common.Compendium;
import minechem.common.tile.TileElectrolysis;

@SideOnly(Side.CLIENT)
public class TESRElectrolysis extends TESRBase<TileElectrolysis, ModelElectrolysis> {

	public TESRElectrolysis() {
		super(0.4F);
		setOffset(0.5, 0.625, 0.5);
		model = new ModelElectrolysis();
		texture = Compendium.Resource.Model.electrolysis;
	}

	@Override
	protected void renderModel(TileElectrolysis te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te != null) {
			model.setLeftTube(te.getLeftTube() != null);
			model.setRightTube(te.getRightTube() != null);
		}
		super.renderModel(te, x, y, z, partialTicks, destroyStage);
	}

}
