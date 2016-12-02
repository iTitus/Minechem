package minechem.client.model;

import net.minecraft.client.model.ModelBase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelRenderable extends ModelBase {
	public abstract void render(float rotation);
}
