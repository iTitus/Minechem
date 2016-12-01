package minechem.item.augment.augments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class AugmentUnstable extends AugmentBase {

	private final float multiplier;

	public AugmentUnstable(float multiplier) {
		super("unstable");
		this.multiplier = multiplier;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem, int level) {
		if (!entityItem.world.isRemote && RAND.nextFloat() < multiplier * 0.001) {
			consumeAugment(entityItem.getEntityItem(), level);
			if (level < 0) {
				return false;
			}
			entityItem.world.createExplosion(null, entityItem.posX, entityItem.posY, entityItem.posZ, (level * multiplier) + 1, true);
		}
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected, int level) {
		if (!entity.world.isRemote && RAND.nextFloat() < multiplier * 0.001) {
			consumeAugment(stack, level);
			if (level < 0) {
				return;
			}
			entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity, null), multiplier * (level + 1));
		}
	}
}
