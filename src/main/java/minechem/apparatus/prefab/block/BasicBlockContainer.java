package minechem.apparatus.prefab.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import minechem.Minechem;
import minechem.helper.ItemHelper;
import minechem.helper.MathHelper;
import minechem.helper.ResearchHelper;

/**
 * Extendable class for simple container blocks
 */
public abstract class BasicBlockContainer extends BasicBlock implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BasicBlockContainer(String name) {
		super(name);
		this.isBlockContainer = true;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BasicBlockContainer(String name, Material material) {
		super(name, material);
		this.isBlockContainer = true;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BasicBlockContainer(String name, Material material, SoundType soundType) {
		super(name, material, soundType);
		this.isBlockContainer = true;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, MathHelper.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		acquireResearch(player, world);
		int id = getGuiID();
		if (id >= 0) {
			if (!world.isRemote) {
				player.openGui(Minechem.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}

	public int getGuiID() {
		return -1;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null) {
			List<ItemStack> droppedStacks = Lists.newArrayList();

			if (dropInventory()) {
				if (tileEntity instanceof IInventory) {
					IInventory inventory = (IInventory) tileEntity;
					for (int i = 0; i < inventory.getSizeInventory(); i++) {
						ItemStack stack = inventory.getStackInSlot(i);
						if (stack != null) {
							droppedStacks.add(stack);
						}
					}
				}
			}

			addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
			for (ItemStack stack : droppedStacks) {
				ItemHelper.throwItemStack(world, stack, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	/**
	 * Define what stacks get dropped when the block is broken, defaults to
	 * nothing
	 *
	 * @param tileEntity
	 * @param itemStacks
	 */
	public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, List<ItemStack> itemStacks) {
	}

	/**
	 * Override to allow inventory dropping to be toggled
	 *
	 * @return boolean true
	 */
	public boolean dropInventory() {
		return true;
	}

	/**
	 * Acquire the research for the block
	 *
	 * @param player
	 * @param world
	 */
	public void acquireResearch(EntityPlayer player, World world) {
		ResearchHelper.addResearch(player, getResearchKey(), world.isRemote);
	}

	/**
	 * Get the research key bound to the block
	 *
	 * @return
	 */
	public String getResearchKey() {
		return "apparatus." + getRawUnlocalizedName();
	}

	/**
	 * Get the unlocalized name without the "tile." prefix
	 *
	 * @return
	 */
	public String getRawUnlocalizedName() {
		return getUnlocalizedName().substring(5);
	}

}
