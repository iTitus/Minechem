package minechem.item.augment.augments;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAugment {

	String getKey();

	/**
	 * @param stack
	 * @param level
	 * @return max Level of damage can be applied
	 */
	int getUsableLevel(ItemStack stack, int level);

	/**
	 * @param stack
	 * @param level
	 * @return int level of augment applied
	 */
	int consumeAugment(ItemStack stack, int level);

	int getMaxLevel();

	/**
	 * @return Localized String name of the augment;
	 */
	String getLocalizedName();

	/**
	 * @param level
	 * @return flavour text to display for level
	 */
	String getDisplayText(int level);

	/**
	 * @return true to make the block drop
	 */
	boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving, int level);

	/**
	 * @return false to cancel drop
	 */
	boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player, int level);

	/**
	 * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner control over the update of the item without having to write a subclass.
	 *
	 * @param entityItem The entity Item
	 * @return Return true to skip any further update code.
	 */
	boolean onEntityItemUpdate(EntityItem entityItem, int level);

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those.
	 */
	EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, int level);

	/**
	 *
	 */
	EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand, int level);

	/**
	 * Called when a entity tries to play the 'swing' animation.
	 *
	 * @param entityLiving The entity swinging the item.
	 * @param stack        The Item stack
	 * @return True to cancel any further processing by EntityLiving
	 */
	boolean onEntitySwing(ItemStack stack, EntityLivingBase entityLiving, int level);

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 */
	boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand, int level);

	/**
	 * Called when the player Left Clicks (attacks) an entity. Processed before damage is done, if return value is true further processing is canceled and the entity is not attacked.
	 *
	 * @param stack  The Item being used
	 * @param player The player that is attacking
	 * @param entity The entity being attacked
	 * @return True to cancel the rest of the interaction.
	 */
	boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level);

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed.
	 */
	ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand, int level);

	ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving, int level);

	/**
	 * Called each tick as long the item is on a player inventory.
	 */
	void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected, int level);

	/**
	 * Called each tick while using an item.
	 *
	 * @param stack  The Item being used
	 * @param player The Player using the item
	 * @param count  The amount of time in tick the item has been used for continuously
	 */
	void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count, int level);

	/**
	 * @param prevDigSpeed unmodified dig speed
	 * @return
	 */
	float getStrVsBlock(ItemStack stack, IBlockState state, float prevStrVsBlock, int level);

	/**
	 * @param toolClass
	 * @return new tool level
	 */
	int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state, int prevHarvestLevel, int level);

	/**
	 * @return Attribute Modifiers to the base tools attributes.
	 */
	Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, int level);

	/**
	 * @return float value between 0 and 1 indicating probability of damage not being applied to the tool
	 */
	float getDamageChance(ItemStack stack, int level);

	/**
	 * @return new EntityItem lifespan (base 6000)
	 */
	int getEntityLifespan(ItemStack stack, World world, int prevEntityLifespan, int level);

	boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, int level);
}
