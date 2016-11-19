package minechem.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;

public class CreativeTabRegistry {

	public static final CreativeTab TAB_PRIMARY = new CreativeTab(Compendium.Naming.id + ".name");
	public static final CreativeTab TAB_CHEMICALS = new CreativeTab(Compendium.Naming.id + "_chemicals.name").setSearchable();

	/**
	 * Must be inited after the Blocks and Items If you want to used modded
	 * Items or Blocks
	 */
	public static void init() {
		TAB_PRIMARY.setStack(new ItemStack(BlockRegistry.opticalMicroscope));
		TAB_CHEMICALS.setStack(new ItemStack(BlockRegistry.electrolysisBlock));
	}

	private static class CreativeTab extends CreativeTabs {

		private ItemStack stack;
		private boolean searchable = false;

		public CreativeTab(String label) {
			super(label);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return null;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			return getStack();
		}

		public ItemStack getStack() {
			return stack != null && stack.getItem() != null ? stack : null;
		}

		public void setStack(ItemStack stack) {
			this.stack = stack;
		}

		public CreativeTab setSearchable() {
			return setSearchable("item_search.png");
		}

		public CreativeTab setSearchable(String background) {
			this.searchable = true;
			setBackgroundImageName(background);
			return this;
		}

		@Override
		public boolean hasSearchBar() {
			return searchable;
		}
	}
}
