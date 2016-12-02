package minechem.client.render.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.model.ModelCrucible;
import minechem.common.Compendium;
import minechem.common.tile.TileCrucible;

@SideOnly(Side.CLIENT)
public class TESRCrucible extends TESRBase<TileCrucible, ModelCrucible> {

	public TESRCrucible() {
		super(0.25F);
		setOffset(0.5, 0.36D, 0.5);
		model = new ModelCrucible();
		texture = Compendium.Resource.Model.electricCrucible;
	}

}
