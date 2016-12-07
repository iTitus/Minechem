package minechem.todo.apparatus.prefab.peripheral;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Optional;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.ManagedPeripheral;
import minechem.common.Compendium;
import minechem.common.tile.TileBase;
import minechem.todo.common.compatibility.ModList;
import minechem.todo.common.compatibility.lua.events.checked.CheckEvent;
import minechem.todo.common.compatibility.lua.methods.LuaMethod;

@Optional.InterfaceList(
		{
				@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = Compendium.Naming.Mods.computerCraft),
				@Optional.Interface(iface = "li.cil.oc.api.network.Environment", modid = Compendium.Naming.Mods.openComputers),
				@Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = Compendium.Naming.Mods.openComputers)
		})
public abstract class TilePeripheralBase extends TileBase implements ManagedPeripheral, Environment, IPeripheral {
	protected final String name;
	protected final Map<Integer, String> methodIDs = new LinkedHashMap<Integer, String>();
	protected final Map<String, LuaMethod> methodNames = new LinkedHashMap<String, LuaMethod>();
	protected final List<CheckEvent> events = new ArrayList<CheckEvent>();
	private final Object node = ModList.opencomputers.isLoaded() ? this.createNode() : null;
	private boolean initialize = true;
	private Set<Object> computers = new LinkedHashSet<Object>();
	private Set<Object> context = new LinkedHashSet<Object>();

	public TilePeripheralBase(String name) {
		this.name = name;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			serverUpdate();
		}
		if (initialize) {
			init();
		}
	}

	/**
	 * Called once when the TileEntity first updates
	 */
	protected void init() {
		if (ModList.opencomputers.isLoaded()) {
			if (node instanceof Component && ((Component) node).network() == null) {
				Network.joinOrCreateNetwork(this);
			}
		}
		initialize = false;
	}

	/**
	 * Only runs on server Side
	 */
	public void serverUpdate() {
		for (CheckEvent event : events) {
			if (event.checkEvent(this)) {
				event.triggerEvent(this);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (ModList.opencomputers.isLoaded()) {
			if (node instanceof Component) {
				((Component) node).load(compound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (ModList.opencomputers.isLoaded()) {
			if (node instanceof Component) {
				((Component) node).save(compound);
			}
		}
	}

	//####################Peripheral Stuff################
	public String[] getMethods() {
		return methodNames.keySet().toArray(new String[methodNames.size()]);
	}

	public void addMethod(LuaMethod method) {
		if (ModList.computercraft.isLoaded() || ModList.opencomputers.isLoaded()) {
			int num = methodIDs.size();
			if (!methodNames.containsKey(method.getMethodName())) {
				methodIDs.put(num, method.getMethodName());
				methodNames.put(method.getMethodName(), method);
			}
		}
	}

	public void addEvent(CheckEvent event) {
		if (ModList.computercraft.isLoaded() || ModList.opencomputers.isLoaded()) {
			events.add(event);
		}
	}

	public String getType() {
		return name;
	}

	//####################ComputerCraft####################
	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public String[] getMethodNames() {
		return this.getMethods();
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public Object[] callMethod(IComputerAccess iComputerAccess, ILuaContext iLuaContext, int i, Object[] objects) throws LuaException, InterruptedException {
		try {
			return methodIDs.containsKey(i) ? methodNames.get(methodIDs.get(i)).call(this, objects) : null;
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public void attach(IComputerAccess iComputerAccess) {
		computers.add(iComputerAccess);
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public void detach(IComputerAccess iComputerAccess) {
		computers.remove(iComputerAccess);
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public boolean equals(IPeripheral iPeripheral) {
		return false;
	}

	@Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
	public Set<Object> getComputers() {
		return computers;
	}

	//####################OpenComputers####################
	public String getComponentName() {
		return this.getType();
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public String[] methods() {
		return this.getMethods();
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		Object[] objs = new Object[args.count()];
		for (int i = 0; i < objs.length; i++) {
			objs[i] = args.checkAny(i);
		}
		return methodNames.containsKey(method) ? methodNames.get(method).call(this, objs) : null;
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public final void onChunkUnload() {
		super.onChunkUnload();
		if (ModList.opencomputers.isLoaded()) {
			if (node instanceof Component) {
				((Component) node).remove();
			}
		}
		this.onInvalidateOrUnload(worldObj, xCoord, yCoord, zCoord, false);
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public final void invalidate() {
		super.invalidate();
		if (node instanceof Component) {
			((Component) node).remove();
		}
		this.onInvalidateOrUnload(worldObj, xCoord, yCoord, zCoord, true);
	}

	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
	}

	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	private Node createNode() {
		return Network.newNode(this, Visibility.Network).withComponent(this.getType(), this.getOCNetworkVisibility()).create();
	}

	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	protected Visibility getOCNetworkVisibility() {
		return Visibility.Network;
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public Node node() {
		return (Node) node;
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public void onConnect(Node node) {
		if (node.host() instanceof Context) {
			context.add(node.host());
		}
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public void onDisconnect(Node node) {
		if (node.host() instanceof Context) {
			context.remove(node.host());
		}
	}

	@Override
	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public void onMessage(Message message) {
	}

	@Optional.Method(modid = Compendium.Naming.Mods.openComputers)
	public Set<Object> getContext() {
		return context;
	}
}
