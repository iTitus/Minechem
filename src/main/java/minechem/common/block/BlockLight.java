package minechem.common.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;

public class BlockLight extends BlockParticle {

	public BlockLight() {
		super(Compendium.Naming.light);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//TODO: player hits with an augmented Item to boost light
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(VALUE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		for (int i = 0; i < state.getValue(VALUE); i++) {
			double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
			double y = pos.getY() + 0.7 + (rand.nextDouble() - 0.5) * 0.2;
			double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;

			world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
		}
	}
}
