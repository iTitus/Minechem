package minechem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	protected final String name;

	public BlockBase(String name, Material material) {
		super(material);
		this.name = name;
		setRegistryName(name);
	}

	public BlockBase(String name, Material material, MapColor mapColor) {
		super(material, mapColor);
		this.name = name;
		setRegistryName(name);
	}

	public String getName() {
		return name;
	}

}
