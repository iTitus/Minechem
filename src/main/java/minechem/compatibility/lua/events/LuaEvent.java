package minechem.compatibility.lua.events;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fml.common.Optional;

import minechem.Compendium;
import minechem.apparatus.prefab.peripheral.TilePeripheralBase;
import minechem.compatibility.ModList;

//import dan200.computercraft.api.peripheral.IComputerAccess;
//import li.cil.oc.api.machine.Context;

public abstract class LuaEvent {

	private String name;

	public LuaEvent(String name) {
		this.name = name;
	}

	public void announce(TileEntity te, Object... message) {
		if (!(te instanceof TilePeripheralBase)) {
			return;
		}
		TilePeripheralBase cTE = (TilePeripheralBase) te;
		if (ModList.computercraft.isLoaded()) {
			computerCraftAnnounce(cTE, message);
		}
		if (ModList.opencomputers.isLoaded()) {
			openComputersAnnounce(cTE, message);
		}
	}

	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public void computerCraftAnnounce(TilePeripheralBase te, Object... message) {
		for (Object computer : te.getComputers()) {
			//((IComputerAccess) computer).queueEvent(name, message);
		}
	}

	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public void openComputersAnnounce(TilePeripheralBase te, Object... message) {
		for (Object context : te.getContext()) {
			//((Context) context).signal(name, message);
		}
	}
}
