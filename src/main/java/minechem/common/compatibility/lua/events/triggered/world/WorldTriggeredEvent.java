package minechem.common.compatibility.lua.events.triggered.world;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import minechem.common.compatibility.lua.events.triggered.TriggerEvent;

public abstract class WorldTriggeredEvent<T extends Event> extends TriggerEvent<T> {
	public WorldTriggeredEvent(String name, EventBus bus) {
		super(name, bus);
	}
}
