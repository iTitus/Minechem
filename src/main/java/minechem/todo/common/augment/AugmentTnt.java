package minechem.todo.common.augment;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AugmentTnt extends AugmentBase {

	public AugmentTnt() {
		super("tnt");
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving, int level) {
		createExplosion(world, null, pos.getX() + RAND.nextDouble(), pos.getY() + RAND.nextDouble(), pos.getZ() + RAND.nextDouble(), level + 3, stack, level);
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level) {
		createExplosion(player.getEntityWorld(), null, entity.posX, entity.posY + entity.height / 2, entity.posZ, (level + 1), stack, level);
		return false;
	}

	public void createExplosion(World world, Entity entity, double x, double y, double z, float radius, ItemStack stack, int level) {
		if (!world.isRemote) {
			consumeAugment(stack, level);
			world.newExplosion(entity, x, y, z, radius, false, false);
		}
	}
}
