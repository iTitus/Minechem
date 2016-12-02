package minechem.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import minechem.common.Compendium;
import minechem.common.tile.TileCentrifuge;

public class BlockCentrifuge extends BlockBaseContainer {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.18, 0, 0.18, 0.82, 0.46, 0.82);

	public BlockCentrifuge() {
		super(Compendium.Naming.centrifuge, Material.ANVIL);
	}

	@Override
	public int getGuiID() {
		return Compendium.Gui.CENTRIFUGE_ID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCentrifuge();
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
