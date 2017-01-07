package minechem.common.item.block;

import minechem.common.block.BlockBase;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(BlockBase block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}
	
	@Override
	public BlockBase getBlock() {
		return (BlockBase) super.getBlock();
	}

}
