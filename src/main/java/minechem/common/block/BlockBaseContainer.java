package minechem.common.block;

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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Strings;

import minechem.common.Minechem;
import minechem.common.registry.AchievementRegistry;
import minechem.common.util.MathHelper;
import minechem.common.util.ResearchHelper;

/**
 * Extendable class for simple container blocks
 */
public abstract class BlockBaseContainer extends BlockBase implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockBaseContainer(String name) {
		super(name);
		this.isBlockContainer = true;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockBaseContainer(String name, Material material) {
		super(name, material);
		this.isBlockContainer = true;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockBaseContainer(String name, Material material, SoundType soundType) {
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		acquireResearchAndAchievement(player);
		int id = getGuiID();
		if (id >= 0 && !player.isSneaking()) {
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
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null) {
			if (dropInventory()) {
				if (tile instanceof IInventory) {
					InventoryHelper.dropInventoryItems(world, pos, (IInventory) tile);
				}
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tile = world.getTileEntity(pos);
		return tile != null && tile.receiveClientEvent(id, param);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation) {
		return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
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
	 * Acquire the research and the achievement for the block
	 *
	 * @param player
	 * @param world
	 */
	public void acquireResearchAndAchievement(EntityPlayer player) {
		if (player != null && !player.world.isRemote) {
			String researchKey = getResearchKey();
			if (!Strings.isNullOrEmpty(researchKey)) {
				ResearchHelper.addResearch(player, researchKey);
			}
			Achievement achievement = getAchievement();
			if (achievement != null) {
				player.addStat(achievement);
			}
		}
	}

	protected Achievement getAchievement() {
		return AchievementRegistry.getAchievement(this);
	}

	/**
	 * Get the research key bound to the block
	 *
	 * @return
	 */
	protected String getResearchKey() {
		return "apparatus." + name;
	}

}
