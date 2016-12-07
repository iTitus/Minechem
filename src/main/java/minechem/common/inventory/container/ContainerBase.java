package minechem.common.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import minechem.common.tile.TileBase;

public class ContainerBase<T extends TileBase> extends Container {

	protected final EntityPlayer player;
	protected final T tile;

	public ContainerBase(EntityPlayer player, T tile) {
		this.player = player;
		this.tile = tile;
	}

	protected void addPlayerInventory(int x, int y) {
		if (player != null) {
			for (int hotbarIndex = 0; hotbarIndex < 9; hotbarIndex++) {
				addSlotToContainer(new Slot(player.inventory, hotbarIndex, 8 + hotbarIndex * 18, y + 58));
			}
			for (int inventoryRowIndex = 0; inventoryRowIndex < 3; inventoryRowIndex++) {
				for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; inventoryColumnIndex++) {
					addSlotToContainer(new Slot(player.inventory, 9 + inventoryColumnIndex + inventoryRowIndex * 9, x + inventoryColumnIndex * 18, y + inventoryRowIndex * 18));
				}
			}

		}
	}

	public T getTile() {
		return tile;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public InventoryPlayer getPlayerInventory() {
		return player.inventory;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile == null || tile.canInteractWith(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		Slot slot = (Slot) inventorySlots.get(slotNumber);
		ItemStack stack = slot.getStack();

		if (stack != null && slot.getHasStack()) {
			if (slotNumber < player.inventory.mainInventory.size()) {
				if (!mergeItemStack(stack, player.inventory.mainInventory.size(), inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if (!mergeItemStack(stack, 0, player.inventory.mainInventory.size(), true)) {
					return null;
				}
			}

			if (stack.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}
}
