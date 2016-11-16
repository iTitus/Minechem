package minechem.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.item.chemical.ChemicalItem;
import minechem.item.journal.JournalItem;

public class ItemRegistry {

	public static JournalItem journal;
	// public static AugmentedItem augmentedItem;
	public static ChemicalItem chemicalItem;

	public static void init() {
		GameRegistry.register(journal = new JournalItem());

		// augmentedItem = new AugmentedItem();
		// GameRegistry.registerItem(augmentedItem,
		// augmentedItem.getUnlocalizedName());
		// GameRegistry.addRecipe(new AugmentRecipe());
		// GameRegistry.addRecipe(new WrapperRecipe());

		GameRegistry.register(chemicalItem = new ChemicalItem());
	}

}
