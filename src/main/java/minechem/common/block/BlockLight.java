package minechem.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;

public class BlockLight extends BlockBase {

	public static final PropertyInteger LIGHT = PropertyInteger.create("light", 0, 15);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);

	public BlockLight() {
		super(Compendium.Naming.light, Material.CIRCUITS);
		setDefaultState(blockState.getBaseState().withProperty(LIGHT, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIGHT);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(LIGHT, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LIGHT);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//TODO: player hits with an augmented Item to boost light
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(LIGHT);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		for (int i = 0; i < state.getValue(LIGHT); i++) {
			double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
			double y = pos.getY() + 0.7 + (rand.nextDouble() - 0.5) * 0.2;
			double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;

			world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
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
}
