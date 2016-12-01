package minechem.registry;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.item.chemical.ChemicalItem;
import minechem.item.journal.JournalItem;

public class ItemRegistry {

	public static JournalItem journal;
	// public static AugmentedItem augmentedItem;
	public static ChemicalItem chemical;

	public static void init() {
		chemical = register(new ChemicalItem());
		journal = register(new JournalItem());
		//augmentedItem = register(new AugmentedItem());
	}

	private static <T extends Item> T register(T item) {
		return GameRegistry.register(item);
	}
}
