package minechem.item.augment.augments;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AugmentLightning extends AugmentBase {

	public AugmentLightning() {
		super("lightning");
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving, int level) {
		if (RAND.nextInt(75) <= level) {
			spawnLightning(world, pos.getX() + RAND.nextDouble(), pos.getY() + RAND.nextDouble(), pos.getZ() + RAND.nextDouble(), stack, level);
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user, int level) {
		if (RAND.nextInt(15) <= level) {
			spawnLightning(user.worldObj, target.posX, target.posY, target.posZ, stack, level);
		}
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool, int level) {
		if (RAND.nextInt(24000) <= level && entity instanceof EntityLivingBase) {
			spawnLightning(world, entity.posX, entity.posY, entity.posZ, stack, level);
		}
	}

	public void spawnLightning(World world, double x, double y, double z, ItemStack stack, int level) {
		if (!world.isRemote) {
			level = getUsableLevel(stack, level);
			if (level >= 0) {
				consumeAugment(stack, level);
				world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
			}
		}
	}
}
