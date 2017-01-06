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
import minechem.common.registry.AugmentRegistry;
import minechem.todo.api.augment.IAugmentedItem;

public class AugmentRecipe implements IRecipe {

	public AugmentRecipe() {
		RecipeSorter.register(Compendium.Naming.id + ":augment", getClass(), RecipeSorter.Category.SHAPELESS, "after:" + Compendium.Naming.id + ":wrapper");
	}

	@Override
	public boolean matches(InventoryCrafting crafting, World world) {
		ItemStack augmented = getAugmentable(crafting);
		if (augmented.isEmpty()) {
			return false;
		}
		ItemStack augmentStack = getItem(crafting);
		if (augmentStack.isEmpty()) {
			return false;
		}
		return ((IAugmentedItem) augmented.getItem()).canHaveAugment(augmented, AugmentRegistry.getAugment(augmentStack));
	}

	private ItemStack getAugmentable(IInventory crafting) {
		ItemStack wrapper = ItemStack.EMPTY;
		for (int i = 0; i < crafting.getSizeInventory(); i++) {
			ItemStack stack = crafting.getStackInSlot(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.getItem() instanceof IAugmentedItem) {
				if (wrapper.isEmpty()) {
					wrapper = stack;
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
			ItemStack stack = crafting.getStackInSlot(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (AugmentRegistry.getAugment(stack) != null) {
				if (item.isEmpty()) {
					item = stack;
				} else {
					return ItemStack.EMPTY;
				}
			}
		}
		return item;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		ItemStack augment = getAugmentable(crafting);
		if (augment.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack item = getItem(crafting);
		if (item.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack result = augment.copy();
		((IAugmentedItem) result.getItem()).setAugment(result, item);
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
