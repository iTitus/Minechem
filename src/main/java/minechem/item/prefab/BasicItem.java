package minechem.item.prefab;

import net.minecraft.item.Item;

import minechem.Compendium;
import minechem.registry.CreativeTabRegistry;

/**
 * Defines properties of a basic item
 */
public abstract class BasicItem extends Item {

	protected final String name;

	public BasicItem(String name) {
		super();
		this.name = name;
		setRegistryName(Compendium.Naming.id, name);
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
	}

	public String getName() {
		return name;
	}
}
