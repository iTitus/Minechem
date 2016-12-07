package minechem.todo.common.compatibility.lua.events.checked;

import minechem.todo.apparatus.prefab.peripheral.TilePeripheralBase;
import minechem.todo.common.compatibility.lua.events.LuaEvent;

public abstract class CheckEvent extends LuaEvent {
	public CheckEvent(String name) {
		super(name);
	}

	public abstract boolean triggerEvent(TilePeripheralBase te);

	public boolean checkEvent(TilePeripheralBase te) {
		return true;
	}
}
