package minechem.item.chemical;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import minechem.Compendium;
import minechem.chemical.ChemicalBase;
import minechem.helper.Jenkins;
import minechem.item.prefab.BasicItem;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;

public class ChemicalItem extends BasicItem {

	public ChemicalItem() {
		super(Compendium.Naming.chemical);
		setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { CreativeTabRegistry.TAB_CHEMICALS, getCreativeTab() };
	}

	public static ChemicalBase getChemicalBase(ItemStack itemStack) {
		return ChemicalBase.readFromNBT(itemStack.getTagCompound());
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
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
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
