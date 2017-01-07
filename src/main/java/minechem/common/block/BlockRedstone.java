package minechem.common.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;

public class BlockRedstone extends BlockParticle {
	
	public static final int STAY_TICKS = 20;
	
	public BlockRedstone() {
		super(Compendium.Naming.redstone);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		for (int i = 0; i < state.getValue(VALUE); i++) {
			double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
			double y = pos.getY() + 0.7 + (rand.nextDouble() - 0.5) * 0.2;
			double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;

			world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0);
		}
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(VALUE);
	}

	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(VALUE);
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
		world.scheduleUpdate(pos, this, STAY_TICKS);
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
		world.notifyNeighborsOfStateChange(pos, this, false);
		for (EnumFacing facing : EnumFacing.VALUES) {
			world.notifyNeighborsOfStateChange(pos.offset(facing), this, false);
		}
	}
}
