package minechem.item.chemical;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
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
		setCreativeTab(CreativeTabRegistry.TAB_CHEMICALS);
	}

	public static ChemicalBase getChemicalBase(ItemStack itemStack) {
		return ChemicalBase.readFromNBT(itemStack.getTagCompound());
	}

	public static ItemStack getItemStackForChemical(ChemicalBase chemicalBase) {
		ItemStack itemStack = new ItemStack(ItemRegistry.chemicalItem);
		NBTTagCompound tag = new NBTTagCompound();
		chemicalBase.writeToNBT(tag);
		itemStack.setTagCompound(tag);
		return itemStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("fullName", Constants.NBT.TAG_STRING)) {
			return itemStack.getTagCompound().getString("fullName");
		} else {
			return "Chemical";
		}
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
		ItemStack itemStack;
		NBTTagCompound tagCompound;
		for (ChemicalBase element : Jenkins.getAll()) {
			itemStack = new ItemStack(this);
			tagCompound = new NBTTagCompound();
			element.writeToNBT(tagCompound);
			itemStack.setTagCompound(tagCompound);
			subItems.add(itemStack);
		}
	}

}
