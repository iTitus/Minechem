package minechem.common.compatibility.lua.events.triggered;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import minechem.common.compatibility.lua.events.LuaEvent;

public abstract class TriggerEvent<T extends Event> extends LuaEvent {
	public TriggerEvent(String name, EventBus bus) {
		super(name);
		bus.register(this);
	}

	@SubscribeEvent
	public void eventListener(T event) {
		if (applies(event)) {
			announce(event);
		}
	}

	public abstract void announce(T event);

	public abstract boolean applies(T event);

	public abstract Object[] constructMessage(T event);
}
