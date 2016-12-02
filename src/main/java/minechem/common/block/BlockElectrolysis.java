package minechem.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import minechem.common.Compendium;
import minechem.common.tile.TileElectrolysis;

public class BlockElectrolysis extends BlockBaseContainer {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.2, 0, 0.2, 0.8, 0.85, 0.8);

	public BlockElectrolysis() {
		super(Compendium.Naming.electrolysis, Material.GLASS);
	}

	@Override
	public int getGuiID() {
		return Compendium.Gui.ELECTROLYSIS_ID;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileElectrolysis();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

//	@Override
//	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
//		// @TODO: add "player.capabilities.isCreativeMode" checks before removing/adding items to inventory
//		TileEntity activatedTileEntity = world.getTileEntity(x, y, z);
//		if (activatedTileEntity instanceof ElectrolysisTileEntity) {
//			ElectrolysisTileEntity electrolysis = (ElectrolysisTileEntity) activatedTileEntity;
//			acquireResearch(player, world);
//			if (player.getCurrentEquippedItem() != null) {
//				ItemStack clickedItemStack = player.getCurrentEquippedItem();
//				if (clickedItemStack.getItem() instanceof ChemicalItem) {
//					ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(clickedItemStack);
//					if (chemicalBase != null) {
//						byte slot = electrolysis.addItem(clickedItemStack);
//						if (slot == 0 || slot == 1) {
//							electrolysis.fillWithChemicalBase(chemicalBase, slot);
//							player.inventory.decrStackSize(player.inventory.currentItem, 1);
//						}
//
//					}
//				}
//			} else {
//				ChemicalItem chemItem = null;
//				if (electrolysis.getRightTube() != null) {
//					chemItem = electrolysis.removeItem(1);
//				} else if (electrolysis.getLeftTube() != null) {
//					chemItem = electrolysis.removeItem(0);
//				}
//
//				if (chemItem != null) {
//					if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null) {
//						if (player.getCurrentEquippedItem().getItem() instanceof ChemicalItem) {
//							// @TODO: attempt to merge held items
//						}
//					} else {
//						player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), new ItemStack(chemItem));
//					}
//				}
//			}
//		}
//		return false;
//	}
}
