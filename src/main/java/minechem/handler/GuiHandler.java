package minechem.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import minechem.Compendium;
import minechem.item.journal.JournalGUI;
import minechem.item.journal.JournalItem;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		switch (ID) {
			case Compendium.Gui.JOURNAL_ID:
				ItemStack stack = player.getHeldItemMainhand();
				if (stack == null || !(stack.getItem() instanceof JournalItem)) {
					stack = player.getHeldItemOffhand();
				}
				if (stack != null && stack.getItem() instanceof JournalItem) {
					return new JournalGUI(player, JournalItem.getKnowledgeKeys(stack), JournalItem.getAuthors(stack));
				}
				break;
			case Compendium.Gui.MICROSCOPE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct GUI object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeGUI(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				// break;
			case Compendium.Gui.CENTRIFUGE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct GUI object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeGUI(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			case Compendium.Gui.ELECTRIC_CRUCIBLE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct GUI object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeGUI(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			case Compendium.Gui.ELECTROLYSIS_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct GUI object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeGUI(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		switch (ID) {
			case Compendium.Gui.MICROSCOPE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct container object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeContainer(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			case Compendium.Gui.CENTRIFUGE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct container object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeContainer(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			case Compendium.Gui.ELECTRIC_CRUCIBLE_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct container object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeContainer(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
			case Compendium.Gui.ELECTROLYSIS_ID:
				// tileEntity = world.getTileEntity(new BlockPos(x, y, z));
				// if (tileEntity != null) {
				// // use instanceof to return the correct container object
				// if (tileEntity instanceof OpticalMicroscopeTileEntity) {
				// return new OpticalMicroscopeContainer(player.inventory,
				// (OpticalMicroscopeTileEntity) tileEntity);
				// }
				// }
				break;
		}
		return null;
	}
}
