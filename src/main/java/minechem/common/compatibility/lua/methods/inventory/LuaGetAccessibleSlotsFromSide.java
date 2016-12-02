package minechem.common.compatibility.lua.methods.inventory;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;

import minechem.common.compatibility.lua.LuaParser;
import minechem.common.compatibility.lua.methods.LuaMethod;

public class LuaGetAccessibleSlotsFromSide extends LuaMethod {
	public LuaGetAccessibleSlotsFromSide() {
		super("getAccessibleSlotsFromSide", "(int side)", Number.class);
	}

	@Override
	public Object[] action(TileEntity te, Object[] args) throws Exception {
		return new Object[]
				{
						LuaParser.toLua(((ISidedInventory) te).getAccessibleSlotsFromSide(((Number) args[0]).intValue()))
				};
	}

	@Override
	public boolean applies(TileEntity te) {
		return te instanceof ISidedInventory;
	}
}
