package minechem.apparatus.tier1.electricCrucible;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;

/**
 * @author jakimfett
 */
public class ElectricCrucibleBlock extends BasicBlockContainer {

	public ElectricCrucibleBlock() {
		super(Compendium.Naming.electricCrucible, Material.ANVIL, SoundType.METAL);

		setBlockBounds(0.12F, 0F, 0.12F, 0.88F, 0.93F, 0.88F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new ElectricCrucibleTileEntity();
	}
}
