package minechem.todo.common.compatibility.lua.events.triggered.world;

import minechem.todo.common.compatibility.lua.events.triggered.TriggerEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public abstract class WorldTriggeredEvent<T extends Event> extends TriggerEvent<T> {
	public WorldTriggeredEvent(String name, EventBus bus) {
		super(name, bus);
	}
}
