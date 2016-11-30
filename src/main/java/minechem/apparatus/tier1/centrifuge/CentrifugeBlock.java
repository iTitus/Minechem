package minechem.apparatus.tier1.centrifuge;

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

public class CentrifugeBlock extends BasicBlockContainer {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.18, 0, 0.18, 0.82, 0.46, 0.82);

	public CentrifugeBlock() {
		super(Compendium.Naming.centrifuge, Material.ANVIL);
	}

	@Override
	public int getGuiID() {
		//return Compendium.Gui.CENTRIFUGE_ID;
		return -1;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new CentrifugeTileEntity();
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
