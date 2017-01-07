package minechem.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import minechem.common.item.block.ItemBlockBase;
import minechem.common.registry.CreativeTabRegistry;

/**
 * Extendable class for simple non-container blocks
 */
public class BlockBase extends Block {

	protected final String name;

	/**
	 * Create a basic block with a given name
	 *
	 * @param name unlocalized name of the block
	 */
	public BlockBase(String name) {
		this(name, Material.IRON);
	}

	public BlockBase(String name, Material material) {
		this(name, material, material == Material.CLOTH ? SoundType.CLOTH : material == Material.WOOD ? SoundType.WOOD : material == Material.GLASS ? SoundType.GLASS : SoundType.METAL);
	}

	public BlockBase(String name, Material material, SoundType soundType) {
		super(material);
		this.name = name;
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setSoundType(soundType);
		setHardness(5);
		setResistance(7);
		setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
	}

	public String getName() {
		return name;
	}
	
	public Item getItemBlock() {
		return new ItemBlockBase(this);
	}

}
