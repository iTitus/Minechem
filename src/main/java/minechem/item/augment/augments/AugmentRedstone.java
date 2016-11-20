package minechem.item.augment.augments;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import minechem.blocks.BlockRedstone;
import minechem.registry.BlockRegistry;

public class AugmentRedstone extends AugmentBase {

	private static final int[] levels = new int[]{5, 10, 15};

	public AugmentRedstone() {
		super("redstone");
	}

	@Override
	public int getUsableLevel(ItemStack stack, int level) {
		return level;
	}

	@Override
	public int getMaxLevel() {
		return levels.length;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, int level) {
		pos = pos.offset(facing);
		if (!world.isRemote && player.canPlayerEdit(pos, facing, stack) && world.isAirBlock(pos)) {
			world.setBlockState(pos, BlockRegistry.blockRedstone.getDefaultState().withProperty(BlockRedstone.POWER, levels[level]), 7);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

}
