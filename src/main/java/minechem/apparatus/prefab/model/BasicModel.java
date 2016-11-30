package minechem.apparatus.prefab.model;

import net.minecraft.client.model.ModelBase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BasicModel extends ModelBase {
	public abstract void render(float rotation);
}
