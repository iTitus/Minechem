package minechem.client.render.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.model.ModelCentrifuge;
import minechem.common.Compendium;
import minechem.common.tile.TileCentrifuge;

@SideOnly(Side.CLIENT)
public class TESRCentrifuge extends TESRBase<TileCentrifuge, ModelCentrifuge> {

	public TESRCentrifuge() {
		super(0.25F);
		setOffset(0.5, 0.625, 0.5);
		model = new ModelCentrifuge();
		texture = Compendium.Resource.Model.centrifuge;
	}

}
