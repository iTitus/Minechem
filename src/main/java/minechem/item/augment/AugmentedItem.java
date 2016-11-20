package minechem.item.augment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import minechem.item.IOverlay;
import minechem.item.augment.augments.IAugment;
import minechem.item.prefab.WrapperItem;
import minechem.registry.AugmentRegistry;

public class AugmentedItem extends WrapperItem implements IAugmentedItem, IOverlay {

	public static final String augmentList = "augments";
	public static final String level = "level";
	public static final UUID ATTACK_DAMAGE_MOIFIER_UUID = ATTACK_DAMAGE_MODIFIER;
//	private static int DEFAULTICON = 0;
//	private static int AXEICON = 1;
//	private static int HOEICON = 2;
//	private static int PICKICON = 3;
//	private static int SHOVELICON = 4;
//	private static int SWORDICON = 5;

//	@SideOnly(Side.CLIENT)
//	protected IIcon[] augmentIcon;

	public AugmentedItem() {
		super(Compendium.Naming.augmented);
		// augmented types are sword, shovel, pickaxe, hoe, axe
//		augmentIcon = new IIcon[6];
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister iconRegister) {
//		// there's got to be a better way to register these...maybe a map?
//
//		augmentIcon[DEFAULTICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedDefaultIcon");
//		augmentIcon[AXEICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedAxeIcon");
//		augmentIcon[HOEICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedHoeIcon");
//		augmentIcon[PICKICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedPickaxeIcon");
//		augmentIcon[SHOVELICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedShovelIcon");
//		augmentIcon[SWORDICON] = iconRegister.registerIcon(Compendium.Naming.id + ":augment/augmentedSwordIcon");
//	}

//	@SideOnly(Side.CLIENT)
//	@Override
//	public IIcon getIconFromDamage(int p_77617_1_) {
//		return augmentIcon[DEFAULTICON];
//	}

	@Override
	public void renderOverlay(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int left, int top, float z) {
//		RenderHelper.enableBlend();
//		RenderHelper.setOpacity(1.0F);
//		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//		ItemStack wrappedItemStack = getWrappedItemStack(itemStack);
//		if (wrappedItemStack != null) {
//			Item wrappedItem = wrappedItemStack.getItem();
//			if (wrappedItem instanceof ItemAxe) {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[AXEICON]);
//			} else if (wrappedItem instanceof ItemHoe) {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[HOEICON]);
//			} else if (wrappedItem instanceof ItemPickaxe) {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[PICKICON]);
//			} else if (wrappedItem instanceof ItemSpade) {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[SHOVELICON]);
//			} else if (wrappedItem instanceof ItemSword) {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[SWORDICON]);
//			} else {
//				RenderHelper.drawTexturedRectUV(left, top, z + 10, 16, 16, augmentIcon[DEFAULTICON]);
//			}
//		}
//
//		RenderHelper.resetOpacity();
//		RenderHelper.disableBlend();
	}

	@Override
	public boolean isWrappable(ItemStack stack) {
		return stack.getItem().isItemTool(stack) && !(stack.getItem() instanceof WrapperItem) && getWrappedItemStack(stack) == null;
	}

	@Override
	public ItemStack getWrappedItemStack(ItemStack wrapper) {
		if (wrapper.hasTagCompound()) {
			return ItemStack.loadItemStackFromNBT(wrapper.getTagCompound().getCompoundTag(Compendium.NBTTags.item));
		}
		return null;
	}

	@Override
	public void setWrappedItemStack(ItemStack wrapper, ItemStack stack) {
		if (!wrapper.hasTagCompound()) {
			wrapper.setTagCompound(new NBTTagCompound());
		}
		wrapper.getTagCompound().setTag(Compendium.NBTTags.item, stack.writeToNBT(new NBTTagCompound()));
	}

	//#############################Augmented Item Stuff##########################################################

	@Override
	public boolean hasAugment(ItemStack item, IAugment augment) {
		return item.hasTagCompound() && item.getTagCompound().hasKey(augment.getKey(), Constants.NBT.TAG_COMPOUND);
	}

	@Override
	public boolean canHaveAugment(ItemStack item, IAugment augment) {
		return this.getWrappedItemStack(item) != null && !this.hasAugment(item, augment);
	}

	@Override
	public Map<IAugment, Integer> getAugments(ItemStack stack) {
		Map<IAugment, Integer> result = new HashMap<IAugment, Integer>();
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(augmentList, Constants.NBT.TAG_LIST)) {
			NBTTagList augments = stack.getTagCompound().getTagList(augmentList, Constants.NBT.TAG_STRING);
			for (int i = 0; i < augments.tagCount(); i++) {
				String key = augments.getStringTagAt(i);
				result.put(AugmentRegistry.getAugment(key), (int) stack.getTagCompound().getCompoundTag(key).getByte(level));
			}
		}
		return result;
	}

	@Override
	public boolean removeAugment(ItemStack item, IAugment augment) {
		if (!item.hasTagCompound()) {
			return false;
		}
		NBTTagCompound tagCompound = item.getTagCompound();
		String augmentKey = augment.getKey();
		if (!tagCompound.hasKey(augmentKey)) {
			return false;
		}
		tagCompound.removeTag(augmentKey);
		NBTTagList augments = tagCompound.getTagList(augmentList, Constants.NBT.TAG_STRING);
		for (int i = 0; i < augments.tagCount(); i++) {
			if (augments.getStringTagAt(i).equals(augmentKey)) {
				augments.removeTag(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setAugment(ItemStack item, ItemStack augmentItem) {
		IAugment augment = AugmentRegistry.getAugment(augmentItem);
		if (augment == null) {
			return false;
		}
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tagCompound = item.getTagCompound();
		String augmentKey = augment.getKey();
		if (!tagCompound.hasKey(augmentKey, Constants.NBT.TAG_COMPOUND)) {
			NBTTagList augments = tagCompound.getTagList(augmentList, Constants.NBT.TAG_STRING);
			augments.appendTag(new NBTTagString(augmentKey));
			tagCompound.setTag(augmentList, augments);
			NBTTagCompound augmentTag = new NBTTagCompound();
			augmentTag.setTag(Compendium.NBTTags.item, augmentItem.writeToNBT(new NBTTagCompound()));
			augmentTag.setByte(level, (byte) 0);
			tagCompound.setTag(augmentKey, augmentTag);
			return true;
		}
		return false;
	}

	@Override
	public boolean setAugmentLevel(ItemStack item, IAugment augment, int level) {
		String augmentKey = augment.getKey();
		if (item.getTagCompound().hasKey(augmentKey, Constants.NBT.TAG_COMPOUND)) {
			item.getTagCompound().getCompoundTag(augmentKey).setByte(AugmentedItem.level, (byte) level);
			return true;
		}
		return false;
	}

	@Override
	public int getAugmentLevel(ItemStack item, IAugment augment) {
		return hasAugment(item, augment) ? item.getTagCompound().getCompoundTag(augment.getKey()).getByte(level) : -1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) // @TODO: Change this to display more useful data
		{
			tooltip.add(entry.getKey().getLocalizedName() + ": " + entry.getKey().getDisplayText(entry.getValue()));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return (getWrappedItemStack(stack) != null ? LocalizationHelper.getLocalString("augment.augmentedItem") + " " : "") + super.getItemStackDisplayName(stack);
	}

	//################################Augment Effect Stuff####################################

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		boolean result = super.onBlockDestroyed(stack, world, state, pos, entityLiving);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().onBlockDestroyed(stack, world, state, pos, entityLiving, entry.getValue());
		}
		return result;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
		boolean result = super.onDroppedByPlayer(stack, player);
		if (result) {
			return true;
		}
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().onDroppedByPlayer(stack, player, entry.getValue());
		}
		return result;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		boolean result = super.onEntityItemUpdate(entityItem);
		for (Map.Entry<IAugment, Integer> entry : getAugments(entityItem.getEntityItem()).entrySet()) {
			result |= entry.getKey().onEntityItemUpdate(entityItem, entry.getValue());
		}
		return result;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EnumActionResult result = super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
		if (result == EnumActionResult.SUCCESS) {
			return result;
		}
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			EnumActionResult newResult = entry.getKey().onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ, entry.getValue());
			if (result == EnumActionResult.PASS || newResult == EnumActionResult.SUCCESS) {
				result = newResult;
			}
		}
		return result;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		EnumActionResult result = super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
		if (result == EnumActionResult.SUCCESS) {
			return result;
		}
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			EnumActionResult newResult = entry.getKey().onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand, entry.getValue());
			if (result == EnumActionResult.PASS || newResult == EnumActionResult.SUCCESS) {
				result = newResult;
			}
		}
		return result;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		boolean result = super.onEntitySwing(entityLiving, stack);
		if (result) {
			return true;
		}
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().onEntitySwing(stack, entityLiving, entry.getValue());
		}
		return result;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		boolean result = super.itemInteractionForEntity(stack, player, target, hand);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().itemInteractionForEntity(stack, player, target, hand, entry.getValue());
		}
		return result;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		boolean result = super.onLeftClickEntity(stack, player, entity);
		if (result) {
			return true;
		}
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().onLeftClickEntity(stack, player, entity, entry.getValue());
		}
		return result;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		ActionResult<ItemStack> result = super.onItemRightClick(stack, world, player, hand);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result = entry.getKey().onItemRightClick(stack, world, player, hand, entry.getValue());
		}
		return result;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		ItemStack result = super.onItemUseFinish(stack, world, entityLiving);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result = entry.getKey().onItemUseFinish(stack, world, entityLiving, entry.getValue());
		}
		return result;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool) {
		super.onUpdate(stack, world, entity, slot, bool);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			entry.getKey().onUpdate(stack, world, entity, slot, bool, entry.getValue());
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		super.onUsingTick(stack, entityLiving, count);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			entry.getKey().onUsingTick(stack, entityLiving, count, entry.getValue());
		}
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		float result = super.getStrVsBlock(stack, state);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result = entry.getKey().getStrVsBlock(stack, state, result, entry.getValue());
		}
		return result;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state) {
		int result = super.getHarvestLevel(stack, toolClass, player, state);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result = entry.getKey().getHarvestLevel(stack, toolClass, player, state, result, entry.getValue());
		}
		return result;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		boolean result = super.hitEntity(stack, target, attacker);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result |= entry.getKey().hitEntity(stack, target, attacker, entry.getValue());
		}
		return result;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> result = getAttributeModifiers(slot, stack);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			result.putAll(entry.getKey().getAttributeModifiers(slot, stack, entry.getValue()));
		}
		return result;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			if (itemRand.nextFloat() < entry.getKey().getDamageChance(stack, entry.getValue())) {
				return;
			}
		}
		super.setDamage(stack, damage);
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		int lifespan = super.getEntityLifespan(stack, world);
		for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet()) {
			lifespan = entry.getKey().getEntityLifespan(stack, world, lifespan, entry.getValue());
		}
		return lifespan;
	}
}
