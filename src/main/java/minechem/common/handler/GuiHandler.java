package minechem.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;
import minechem.client.gui.GuiCentrifuge;
import minechem.client.gui.GuiCrucible;
import minechem.client.gui.GuiElectrolysis;
import minechem.client.gui.GuiJournal;
import minechem.client.gui.GuiMicroscope;
import minechem.common.Compendium;
import minechem.common.inventory.container.ContainerCentrifuge;
import minechem.common.inventory.container.ContainerCrucible;
import minechem.common.inventory.container.ContainerElectrolysis;
import minechem.common.inventory.container.ContainerMicroscope;
import minechem.common.item.ItemJournal;
import minechem.common.tile.TileCentrifuge;
import minechem.common.tile.TileCrucible;
import minechem.common.tile.TileElectrolysis;
import minechem.common.tile.TileMicroscope;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case Compendium.Gui.JOURNAL_ID:
				ItemStack stack = player.getHeldItemMainhand();
				if (!(stack.getItem() instanceof ItemJournal)) {
					stack = player.getHeldItemOffhand();
				}
				if (stack.getItem() instanceof ItemJournal) {
					return new GuiJournal(player, ItemJournal.getKnowledgeKeys(stack), ItemJournal.getAuthors(stack));
				}
				break;
			case Compendium.Gui.MICROSCOPE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileMicroscope) {
						return new GuiMicroscope(new ContainerMicroscope(player, (TileMicroscope) tileEntity));
					}
				}
				break;
			case Compendium.Gui.CENTRIFUGE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileCentrifuge) {
						return new GuiCentrifuge(new ContainerCentrifuge(player, (TileCentrifuge) tileEntity));
					}
				}
				break;
			case Compendium.Gui.ELECTRIC_CRUCIBLE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileCrucible) {
						return new GuiCrucible(new ContainerCrucible(player, (TileCrucible) tileEntity));
					}
				}
				break;
			case Compendium.Gui.ELECTROLYSIS_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileElectrolysis) {
						return new GuiElectrolysis(new ContainerElectrolysis(player, (TileElectrolysis) tileEntity));
					}
				}
				break;

		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID) {
			case Compendium.Gui.MICROSCOPE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileMicroscope) {
						return new ContainerMicroscope(player, (TileMicroscope) tileEntity);
					}
				}
				break;
			case Compendium.Gui.CENTRIFUGE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileCentrifuge) {
						return new ContainerCentrifuge(player, (TileCentrifuge) tileEntity);
					}
				}
				break;
			case Compendium.Gui.ELECTRIC_CRUCIBLE_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileCrucible) {
						return new ContainerCrucible(player, (TileCrucible) tileEntity);
					}
				}
				break;
			case Compendium.Gui.ELECTROLYSIS_ID:
				tileEntity = world.getTileEntity(pos);
				if (tileEntity != null) {
					if (tileEntity instanceof TileElectrolysis) {
						return new ContainerElectrolysis(player, (TileElectrolysis) tileEntity);
					}
				}
				break;
		}
		return null;
	}
}
