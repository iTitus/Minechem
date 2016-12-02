package minechem.client.render.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.client.model.ModelMicroscope;
import minechem.common.Compendium;
import minechem.common.tile.TileMicroscope;

@SideOnly(Side.CLIENT)
public class TESRMicroscope extends TESRBase<TileMicroscope, ModelMicroscope> {

	public TESRMicroscope() {
		super(0.4F);
		setOffset(0.5, 0.625, 0.5);
		model = new ModelMicroscope();
		texture = Compendium.Resource.Model.microscope;
	}

}
