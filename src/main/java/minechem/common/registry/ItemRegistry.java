package minechem.common.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.List;

import com.google.common.collect.Lists;

import minechem.common.Compendium;
import minechem.common.item.ItemChemical;
import minechem.common.item.ItemJournal;
import minechem.common.util.LogHelper;

@ObjectHolder(Compendium.Naming.id)
@Mod.EventBusSubscriber(modid = Compendium.Naming.id)
public class ItemRegistry {

	static final List<Item> registeredItems = Lists.newArrayList();
	
	@ObjectHolder(Compendium.Naming.journal)
	public static final ItemJournal journal = null;
	//@ObjectHolder(Compendium.Naming.augmented)
	//public static final ItemAugmented itemAugmented = null;
	@ObjectHolder(Compendium.Naming.chemical)
	public static final ItemChemical chemical = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event) {
		LogHelper.debug("Registering Items...");
		
		register(new ItemJournal());
		register(new ItemChemical());
		//register(new ItemAugmented());
		
		event.getRegistry().registerAll(registeredItems.toArray(new Item[registeredItems.size()]));
	}

	private static <T extends Item> T register(T item) {
		registeredItems.add(item);
		return item;
	}
}
