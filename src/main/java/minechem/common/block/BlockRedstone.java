package minechem.common.block;

import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;

public class BlockRedstone extends BlockBase {

	public static final Material MATERIAL = new MaterialTransparent(MapColor.AIR);
	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);

	public BlockRedstone() {
		super(Compendium.Naming.redstone, MATERIAL);
		setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWER);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWER, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		for (int i = 0; i < state.getValue(POWER); i++) {
			double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
			double y = pos.getY() + 0.7 + (rand.nextDouble() - 0.5) * 0.2;
			double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;

			world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(POWER);
	}

	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(POWER);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, 20);
		secondOrderNotify(world, pos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (world.getBlockState(pos).getBlock() == this) {
			world.setBlockToAir(pos);
			secondOrderNotify(world, pos);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		secondOrderNotify(world, pos);
	}

	public void secondOrderNotify(World world, BlockPos pos) {
		world.notifyNeighborsOfStateChange(pos, this);
		for (EnumFacing facing : EnumFacing.VALUES) {
			world.notifyNeighborsOfStateChange(pos.offset(facing), this);
		}
	}
}
