package minechem.apparatus.tier1.opticalMicroscope;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;

public class OpticalMicroscopeBlock extends BasicBlockContainer {

	public static AxisAlignedBB AABB = new AxisAlignedBB(0.2, 0, 0.2, 0.8, 1, 0.8);

	public OpticalMicroscopeBlock() {
		super(Compendium.Naming.opticalMicroscope, Material.IRON);
	}

	@Override
	public int getGuiID() {
		return Compendium.Gui.MICROSCOPE_ID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new OpticalMicroscopeTileEntity();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
