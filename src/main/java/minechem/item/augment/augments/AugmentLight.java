package minechem.item.augment.augments;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import minechem.blocks.BlockLight;
import minechem.item.augment.IAugmentedItem;
import minechem.registry.BlockRegistry;

public class AugmentLight extends AugmentBase {

	public AugmentLight() {
		super("light");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() != null) {
			ItemStack stack = event.getHarvester().getActiveItemStack();
			if (stack != null && stack.getItem() instanceof IAugmentedItem) {
				IAugmentedItem augmentedItem = (IAugmentedItem) stack.getItem();
				int level = augmentedItem.getAugmentLevel(stack, this);
				int light = event.getWorld().getLightFor(EnumSkyBlock.BLOCK, event.getPos());
				if (level > -1 && light < 8) {
					consumeAugment(stack, level);
					event.getWorld().setBlockState(event.getPos(), BlockRegistry.blockLight.getDefaultState().withProperty(BlockLight.LIGHT, level));
				}
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, int level) {
		pos = pos.offset(facing);
		if (!world.isRemote && world.isAirBlock(pos)) {
			consumeAugment(stack, level * 2);
			world.setBlockState(pos, BlockRegistry.blockLight.getDefaultState().withProperty(BlockLight.LIGHT, (int) (level * 1.5F)));
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}
}
