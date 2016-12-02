package minechem.common.augment;

import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import minechem.api.augment.IAugment;
import minechem.api.augment.IAugmentItem;
import minechem.common.Compendium;
import minechem.common.util.LocalizationHelper;

public abstract class AugmentBase implements IAugment {

	public static final Random RAND = new Random(System.currentTimeMillis());

	private final String key;
	private final String localizationKey;

	public AugmentBase(String key) {
		this.key = key;
		this.localizationKey = "augment." + key;
	}

	@Override
	public final String getKey() {
		return key;
	}

	@Override
	public int getUsableLevel(ItemStack stack, int level) {
		NBTTagCompound augment = stack.getTagCompound().getCompoundTag(this.getKey());
		ItemStack augmentItem = ItemStack.loadItemStackFromNBT(augment.getCompoundTag(Compendium.NBTTags.item));
		if (augmentItem == null || augmentItem.getItem() == null) {
			return -1;
		}
		return dischargeAugment(augmentItem, level, false);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public String getLocalizedName() {
		return LocalizationHelper.getLocalString(localizationKey);
	}

	@Override
	public String getDisplayText(int level) {
		return String.valueOf(level);
	}

	@Override
	public int consumeAugment(ItemStack stack, int level) {
		NBTTagCompound augment = stack.getTagCompound().getCompoundTag(this.getKey());
		ItemStack augmentItem = ItemStack.loadItemStackFromNBT(augment.getCompoundTag(Compendium.NBTTags.item));
		if (augmentItem == null || augmentItem.getItem() == null) {
			return -1;
		}
		int discharged = dischargeAugment(augmentItem, level, true);
		augment.setTag(Compendium.NBTTags.item, augmentItem.writeToNBT(new NBTTagCompound()));
		return discharged;
	}

	/**
	 * Actually handles the draining/damaging of Augments from the stored container
	 *
	 * @param stack
	 * @param level
	 * @param discharge
	 * @return int: max level <= {@param level} that can be used
	 */
	public int dischargeAugment(ItemStack stack, int level, boolean discharge) {

		IFluidHandler handler = FluidUtil.getFluidHandler(stack);

		if (handler != null) {
			while (!drain(handler, stack, getVolumeConsumed(level), false) && level >= 0) {
				level--;
			}
			if (discharge && level >= 0) {
				drain(handler, stack, this.getVolumeConsumed(level), true);
			}
			return level;
		} else if (stack.getItem() instanceof IAugmentItem) {
			if (discharge) {
				return ((IAugmentItem) stack.getItem()).consumeLevel(stack, this, getVolumeConsumed(level));
			}
			return ((IAugmentItem) stack.getItem()).getMaxLevel(stack, this, getVolumeConsumed(level));
		} else if (stack.isItemStackDamageable()) {
			while (this.getDamageDone(level) > stack.getMaxDamage() - stack.getItemDamage() && level >= 0) {
				level--;
			}
			if (discharge && level >= 0) {
				stack.attemptDamageItem(this.getDamageDone(level), RAND);
			}
			return level;
		}
		return -1;
	}

	/**
	 * Attempts to drain passed ItemStack for FluidContainer augments
	 *
	 * @param handler
	 * @param stack
	 * @param volume
	 * @param doDrain
	 * @return true for volume is drainable
	 */
	public boolean drain(IFluidHandler handler, ItemStack stack, int volume, boolean doDrain) {
		return volume == handler.drain(volume, doDrain).amount;
	}

	/**
	 * @param level
	 * @return Fluid to drain from FluidContainer Augment for given Augment level
	 */
	public int getVolumeConsumed(int level) {
		return level * 10 + 5;
	}

	/**
	 * @param level
	 * @return Damage to do to an ItemStack augment for given Augment level
	 */
	public int getDamageDone(int level) {
		return level + 1;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving, int level) {
		return false;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player, int level) {
		return true;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem, int level) {
		return false;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, int level) {
		return EnumActionResult.PASS;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand, int level) {
		return EnumActionResult.PASS;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, EntityLivingBase entityLiving, int level) {
		return false;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand, int level) {
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level) {
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand, int level) {
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving, int level) {
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected, int level) {

	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count, int level) {

	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state, float prevStrVsBlock, int level) {
		return prevStrVsBlock;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state, int prevHarvestLevel, int level) {
		return prevHarvestLevel;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, int level) {
		return HashMultimap.create();
	}

	@Override
	public float getDamageChance(ItemStack stack, int level) {
		return 0;
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world, int prevEntityLifespan, int level) {
		return prevEntityLifespan;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, int level) {
		return false;
	}
}
