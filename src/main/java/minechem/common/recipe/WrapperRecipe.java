package minechem.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.RecipeSorter;

import minechem.common.Compendium;
import minechem.todo.common.item.ItemWrapped;

public class WrapperRecipe implements IRecipe {

	public WrapperRecipe() {
		RecipeSorter.register(Compendium.Naming.id + ":wrapper", getClass(), RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
	}

	@Override
	public boolean matches(InventoryCrafting crafting, World world) {
		ItemStack wrapper = getWrapper(crafting);
		if (wrapper.isEmpty()) {
			return false;
		}
		ItemStack item = getItem(crafting);
		if (item.isEmpty()) {
			return false;
		}
		return ((ItemWrapped) wrapper.getItem()).isWrappable(item);
	}

	private ItemStack getWrapper(IInventory crafting) {
		ItemStack wrapper = ItemStack.EMPTY;
		for (int i = 0; i < crafting.getSizeInventory(); i++) {
			ItemStack itemStack = crafting.getStackInSlot(i);
			if (itemStack.isEmpty()) {
				continue;
			}
			if (itemStack.getItem() instanceof ItemWrapped) {
				if (wrapper.isEmpty()) {
					wrapper = itemStack;
				} else {
					return ItemStack.EMPTY;
				}
			}
		}
		return wrapper;
	}

	private ItemStack getItem(IInventory crafting) {
		ItemStack item = ItemStack.EMPTY;
		for (int i = 0; i < crafting.getSizeInventory(); i++) {
			ItemStack itemStack = crafting.getStackInSlot(i);
			if (itemStack.isEmpty()) {
				continue;
			}
			if (!(itemStack.getItem() instanceof ItemWrapped)) {
				if (item.isEmpty()) {
					item = itemStack;
				} else {
					return ItemStack.EMPTY;
				}
			}
		}
		return item;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		ItemStack wrapper = getWrapper(crafting);
		if (wrapper.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack item = getItem(crafting);
		if (item.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack result = wrapper.copy();
		((ItemWrapped) result.getItem()).setWrappedItemStack(result, item);
		return result;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
