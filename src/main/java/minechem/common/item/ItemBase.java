package minechem.common.item;

import net.minecraft.item.Item;

import minechem.common.Compendium;
import minechem.common.registry.CreativeTabRegistry;

/**
 * Defines properties of a basic item
 */
public abstract class ItemBase extends Item {

	protected final String name;

	public ItemBase(String name) {
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
