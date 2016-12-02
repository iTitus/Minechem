package minechem.apparatus.tier1.electricCrucible;

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

public class ElectricCrucibleBlock extends BasicBlockContainer {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.12, 0, 0.12, 0.88, 0.93, 0.88);

	public ElectricCrucibleBlock() {
		super(Compendium.Naming.electricCrucible, Material.ANVIL);
	}

	@Override
	public int getGuiID() {
		return Compendium.Gui.ELECTRIC_CRUCIBLE_ID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new ElectricCrucibleTileEntity();
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
