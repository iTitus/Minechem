package minechem.apparatus.prefab.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import minechem.registry.CreativeTabRegistry;

/*
 * Extendable class for simple non-container blocks
 */
public class BasicBlock extends Block {

	protected final String name;

	/**
	 * Create a basic block with a given name
	 *
	 * @param name unlocalized name of the block
	 */
	public BasicBlock(String name) {
		this(name, Material.IRON);
	}

	public BasicBlock(String name, Material material) {
		this(name, material, material == Material.CLOTH ? SoundType.CLOTH : material == Material.WOOD ? SoundType.WOOD : material == Material.GLASS ? SoundType.GLASS : SoundType.METAL);
	}

	public BasicBlock(String name, Material material, SoundType soundType) {
		super(material);
		this.name = name;
		setRegistryName(name);
		setUnlocalizedName(name);
		setSoundType(soundType);
		setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
	}

	public String getName() {
		return name;
	}

}
