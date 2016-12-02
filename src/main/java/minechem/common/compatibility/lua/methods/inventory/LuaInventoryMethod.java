package minechem.common.compatibility.lua.methods.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import minechem.common.compatibility.lua.methods.LuaMethod;

public abstract class LuaInventoryMethod extends LuaMethod {
	public LuaInventoryMethod(String methodName) {
		super(methodName);
	}

	public LuaInventoryMethod(String methodName, String args, Class... classes) {
		super(methodName, args, classes);
	}

	public LuaInventoryMethod(String methodName, String args, int minArgs, int maxArgs, Class... classes) {
		super(methodName, args, minArgs, maxArgs, classes);
	}

	@Override
	public boolean applies(TileEntity te) {
		return te instanceof IInventory;
	}
}
