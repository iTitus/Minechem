package minechem.todo.common.item;

import java.util.Set;

import com.google.common.collect.Multimap;

import minechem.common.item.ItemBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;

public abstract class ItemWrapped extends ItemBase {

	public ItemWrapped(String name) {
		super(name);
		setMaxDamage(Short.MAX_VALUE - 1);
	}

	/**
	 * @param wrapper Wrapped ItemStack
	 * @return ItemStack contained
	 */
	public abstract ItemStack getWrappedItemStack(ItemStack wrapper);

	/**
	 * @param stack ItemStack to be wrapped
	 * @return true if ItemStack is valid
	 */
	public abstract boolean isWrappable(ItemStack stack);

	/**
	 * @param wrapper ItemStack
	 * @param stack   ItemStack to be wrapped
	 */
	public abstract void setWrappedItemStack(ItemStack wrapper, ItemStack stack);

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped != null) {
			return wrapped.getItem().getItemStackDisplayName(wrapped);
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getAttributeModifiers(slot, stack);
		}
		return wrapped.getItem().getAttributeModifiers(slot, wrapped);
	}

//	@Override
//	public int getColorFromItemStack(ItemStack stack, int colour) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.getColorFromItemStack(stack, colour);
//		}
//		return wrapped.getItem().getColorFromItemStack(wrapped, colour);
//	}

	@Override
	public int getDamage(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getDamage(stack);
		}
		return wrapped.getItem().getDamage(wrapped);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getStrVsBlock(stack, state);
		}
		return wrapped.getItem().getStrVsBlock(wrapped, state);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getDurabilityForDisplay(stack);
		}
		return wrapped.getItem().getDurabilityForDisplay(wrapped);
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getEntityLifespan(stack, world);
		}
		return wrapped.getItem().getEntityLifespan(wrapped, world);
	}

	@Override
	public FontRenderer getFontRenderer(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getFontRenderer(stack);
		}
		return wrapped.getItem().getFontRenderer(wrapped);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getHarvestLevel(stack, toolClass, player, blockState);
		}
		return wrapped.getItem().getHarvestLevel(stack, toolClass, player, blockState);
	}

//	@Override
//	public IIcon getIcon(ItemStack stack, int pass) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.getIcon(stack, pass);
//		}
//		return wrapped.getItem().getIcon(wrapped, pass);
//	}

//	@Override
//	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
//		}
//		return wrapped.getItem().getIcon(wrapped, renderPass, player, usingItem, useRemaining);
//	}

//	@Override
//	public IIcon getIconIndex(ItemStack stack) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.getIconIndex(stack);
//		}
//		return wrapped.getItem().getIconIndex(wrapped);
//	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getIsRepairable(stack, stack2);
		}
		return wrapped.getItem().getIsRepairable(wrapped, stack2);
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getItemEnchantability(stack);
		}
		return wrapped.getItem().getItemEnchantability(wrapped);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getItemStackLimit(stack);
		}
		return wrapped.getItem().getItemStackLimit(wrapped);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getItemUseAction(stack);
		}
		return wrapped.getItem().getItemUseAction(wrapped);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getMaxDamage(stack);
		}
		return wrapped.getItem().getMaxDamage(wrapped);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getMaxItemUseDuration(stack);
		}
		return wrapped.getItem().getMaxItemUseDuration(wrapped);
	}

//	@Override
//	public String getPotionEffect(ItemStack stack) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.getPotionEffect(stack);
//		}
//		return wrapped.getItem().getPotionEffect(wrapped);
//	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getRarity(stack);
		}
		return wrapped.getRarity();
	}

	@Override
	public float getSmeltingExperience(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getSmeltingExperience(stack);
		}
		return wrapped.getItem().getSmeltingExperience(wrapped);
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getToolClasses(stack);
		}
		return getToolClasses(wrapped);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.getUnlocalizedName(stack);
		}
		return wrapped.getItem().getUnlocalizedName(wrapped);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.hitEntity(stack, target, attacker);
		}
		boolean result = wrapped.getItem().hitEntity(wrapped, target, attacker);
		setDamage(stack, wrapped.getItemDamage());
		if (stack.getCount() <= 0) {
			if (attacker instanceof EntityPlayer) {
				ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) attacker, stack, attacker.getActiveHand());
			}
			attacker.setHeldItem(attacker.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.isBeaconPayment(stack);
		}
		return wrapped.getItem().isBeaconPayment(wrapped);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.isBookEnchantable(stack, book);
		}
		return wrapped.getItem().isBookEnchantable(wrapped, book);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.isDamaged(stack);
		}
		return wrapped.getItem().isDamaged(wrapped);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.isEnchantable(stack);
		}
		return wrapped.getItem().isEnchantable(wrapped);
	}

//	@Override
//	public boolean isPotionIngredient(ItemStack stack) {
//		ItemStack wrapped = getWrappedItemStack(stack);
//		if (wrapped == null) {
//			return super.isPotionIngredient(stack);
//		}
//		return wrapped.getItem().isPotionIngredient(wrapped);
//	}

	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.isValidArmor(stack, armorType, entity);
		}
		return wrapped.getItem().isValidArmor(wrapped, armorType, entity);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.itemInteractionForEntity(stack, player, target, hand);
		}
		boolean result = wrapped.getItem().itemInteractionForEntity(wrapped, player, target, hand);
		if (stack.getCount() <= 0) {
			ForgeEventFactory.onPlayerDestroyItem(player, stack, player.getActiveHand());
			player.setHeldItem(player.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			onArmorTick(world, player, stack);
		} else {
			wrapped.getItem().onArmorTick(world, player, wrapped);
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
		}
		boolean result = wrapped.getItem().onBlockDestroyed(wrapped, world, state, pos, entityLiving);
		setDamage(stack, wrapped.getItemDamage());
		if (stack.getCount() <= 0) {
			if (entityLiving instanceof EntityPlayer) {
				ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) entityLiving, stack, entityLiving.getActiveHand());
			}
			entityLiving.setHeldItem(entityLiving.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onBlockStartBreak(stack, pos, player);
		}
		boolean result = wrapped.getItem().onBlockStartBreak(wrapped, pos, player);
		setDamage(stack, wrapped.getItemDamage());
		if (stack.getCount() <= 0) {
			ForgeEventFactory.onPlayerDestroyItem(player, stack, player.getActiveHand());
			player.setHeldItem(player.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			super.onCreated(stack, world, player);
		} else {
			wrapped.getItem().onCreated(wrapped, world, player);
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onDroppedByPlayer(stack, player);
		}
		return wrapped.getItem().onDroppedByPlayer(wrapped, player);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onItemUseFinish(stack, world, entityLiving);
		}
		return wrapped.getItem().onItemUseFinish(wrapped, world, entityLiving);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onEntitySwing(entityLiving, stack);
		}
		return wrapped.getItem().onEntitySwing(entityLiving, wrapped);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onItemRightClick(stack, world, player, hand);
		}
		ActionResult<ItemStack> result = wrapped.getItem().onItemRightClick(wrapped, world, player, hand);
		wrapped = result.getResult();
		if (wrapped == null || wrapped.getCount() <= 0) {
			return ActionResult.newResult(result.getType(), null);
		}
		setWrappedItemStack(stack, wrapped);
		return ActionResult.newResult(result.getType(), stack);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		EnumActionResult result = wrapped.getItem().onItemUse(wrapped, player, world, pos, hand, facing, hitX, hitY, hitZ);
		setDamage(stack, wrapped.getItemDamage());
		if (stack.getCount() <= 0) {
			ForgeEventFactory.onPlayerDestroyItem(player, stack, player.getActiveHand());
			player.setHeldItem(player.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
		}
		EnumActionResult result = wrapped.getItem().onItemUseFirst(wrapped, player, world, pos, side, hitX, hitY, hitZ, hand);
		setDamage(stack, wrapped.getItemDamage());
		if (stack.getCount() <= 0) {
			ForgeEventFactory.onPlayerDestroyItem(player, stack, player.getActiveHand());
			player.setHeldItem(player.getActiveHand(), null);
		}
		return result;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			return super.onLeftClickEntity(stack, player, entity);
		}
		return wrapped.getItem().onLeftClickEntity(wrapped, player, entity);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			super.onPlayerStoppedUsing(stack, world, entityLiving, timeLeft);
		} else {
			wrapped.getItem().onPlayerStoppedUsing(wrapped, world, entityLiving, timeLeft);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			super.onUpdate(stack, world, entity, slot, isSelected);
		} else {
			wrapped.getItem().onUpdate(wrapped, world, entity, slot, isSelected);
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			super.onUsingTick(stack, entityLiving, count);
		} else {
			wrapped.getItem().onUsingTick(wrapped, entityLiving, count);
		}
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		ItemStack wrapped = getWrappedItemStack(stack);
		if (wrapped == null) {
			super.setDamage(stack, damage);
		} else {
			wrapped.getItem().setDamage(wrapped, damage);
			if (wrapped.getMaxDamage() <= damage) {
				stack.setCount(stack.getCount() - 1);
			}
			setWrappedItemStack(stack, wrapped);
		}
	}
}
