package minechem.common.compatibility.lua.events.checked;

import minechem.common.compatibility.lua.events.LuaEvent;
import minechem.todo.apparatus.prefab.peripheral.TilePeripheralBase;

public abstract class CheckEvent extends LuaEvent {
	public CheckEvent(String name) {
		super(name);
	}

	public abstract boolean triggerEvent(TilePeripheralBase te);

	public boolean checkEvent(TilePeripheralBase te) {
		return true;
	}
}
