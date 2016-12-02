package minechem.common.registry;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.common.item.ItemAugmented;
import minechem.common.item.ItemChemical;
import minechem.common.item.ItemJournal;

public class ItemRegistry {

	public static ItemJournal journal;
	public static ItemAugmented itemAugmented;
	public static ItemChemical chemical;

	public static void init() {
		chemical = register(new ItemChemical());
		journal = register(new ItemJournal());
		itemAugmented = register(new ItemAugmented());
	}

	private static <T extends Item> T register(T item) {
		return GameRegistry.register(item);
	}
}
