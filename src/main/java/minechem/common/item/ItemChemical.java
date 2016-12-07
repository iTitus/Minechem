package minechem.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.common.Compendium;
import minechem.common.chemical.ChemicalBase;
import minechem.common.registry.CreativeTabRegistry;
import minechem.common.registry.ItemRegistry;
import minechem.common.util.Jenkins;

public class ItemChemical extends ItemBase {

	public ItemChemical() {
		super(Compendium.Naming.chemical);
		setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
	}

	public static ChemicalBase getChemicalBase(ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemChemical && stack.hasTagCompound() ? ChemicalBase.readFromNBT(stack.getTagCompound()) : null;
	}

	public static ItemStack getItemStackForChemical(ChemicalBase chemicalBase) {
		ItemStack stack = new ItemStack(ItemRegistry.chemical);
		if (chemicalBase != null) {
			NBTTagCompound tag = new NBTTagCompound();
			chemicalBase.writeToNBT(tag);
			stack.setTagCompound(tag);
		}
		return stack;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[]{CreativeTabRegistry.TAB_CHEMICALS, getCreativeTab()};
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		ChemicalBase chemicalBase = getChemicalBase(stack);
		if (chemicalBase != null) {
			return chemicalBase.fullName;
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		ChemicalBase chemicalBase = getChemicalBase(stack);
		if (chemicalBase != null) {
			tooltip.addAll(chemicalBase.getToolTip());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack stack = new ItemStack(this);
		if (tab != CreativeTabRegistry.TAB_CHEMICALS) {
			subItems.add(stack);
		}
		if (tab != CreativeTabRegistry.TAB_PRIMARY) {
			for (ChemicalBase chemicalBase : Jenkins.getAll()) {
				stack = new ItemStack(this);
				stack.setTagCompound(chemicalBase.writeToNBT(new NBTTagCompound()));
				subItems.add(stack);
			}
		}
	}

}
