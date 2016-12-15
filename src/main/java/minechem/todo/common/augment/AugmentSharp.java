package minechem.todo.common.augment;

import com.google.common.collect.Multimap;

import minechem.todo.common.item.ItemAugmented;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AugmentSharp extends AugmentBase {

	public AugmentSharp() {
		super("sharp");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user, int level) {
		consumeAugment(stack, level);
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving, int level) {
		consumeAugment(stack, (int) (level / 0.4));
		return super.onBlockDestroyed(stack, world, state, pos, entityLiving, level);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state, int prevHarvestLevel, int level) {
		return prevHarvestLevel + (int) (level / 0.4) + 1;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, int level) {
		Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack, level);
		modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ItemAugmented.ATTACK_DAMAGE_MOIFIER_UUID, "Weapon modifier", (double) level, 0));
		return modifiers;
	}

}
