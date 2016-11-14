package minechem.item.chemical;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import minechem.Compendium;
import minechem.chemical.ChemicalBase;
import minechem.helper.Jenkins;
import minechem.item.prefab.BasicItem;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ChemicalItem extends BasicItem
{
    // @TODO maybe replace the textures since I just used the old ones
    @SideOnly(Side.CLIENT)
    public IIcon dust;
    @SideOnly(Side.CLIENT)
    public IIcon tube;
    @SideOnly(Side.CLIENT)
    public IIcon moleculeSymbol;
    @SideOnly(Side.CLIENT)
    public IIcon[] liquid;
    @SideOnly(Side.CLIENT)
    public IIcon[] gas;
    @SideOnly(Side.CLIENT)
    public IIcon[] plasma;

    public ChemicalItem()
    {
        super("chemical");
        setCreativeTab(CreativeTabRegistry.TAB_CHEMICALS);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        super.registerIcons(iconRegister);
        liquid = new IIcon[7];
        gas = new IIcon[7];
        plasma = new IIcon[1];
        tube = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString);
        dust = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString + "_dust");
        moleculeSymbol = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString + "_molecule");
        for (int i = 0; i < liquid.length; i++)
        {
            liquid[i] = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString + "_liquid" + (i + 1));
        }
        for (int i = 0; i < gas.length; i++)
        {
            gas[i] = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString + "_gas" + (i + 1));
        }
        for (int i = 0; i < plasma.length; i++)
        {
            plasma[i] = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString + "_plasma" + (i + 1));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("fullName"))
        {
            return itemStack.getTagCompound().getString("fullName");
        } else
        {
            return "Generic ChemicalItem";
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean bool)
    {
        super.addInformation(itemStack, player, tooltip, bool);
        ChemicalBase chemicalBase = getChemicalBase(itemStack);
        if (chemicalBase != null)
        {
            tooltip.addAll(chemicalBase.getToolTip());
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List subItems)
    {
        ItemStack itemStack;
        NBTTagCompound tagCompound;
        for (ChemicalBase element : Jenkins.getAll())
        {
            itemStack = new ItemStack(this);
            tagCompound = new NBTTagCompound();
            element.writeToNBT(tagCompound);
            itemStack.setTagCompound(tagCompound);
            subItems.add(itemStack);
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
        ChemicalBase chemicalBase = getChemicalBase(itemStack);
        if (chemicalBase != null)
        {
            return chemicalBase.getColour();
        }
        return super.getColorFromItemStack(itemStack, renderPass);
    }

    public static ChemicalBase getChemicalBase(ItemStack itemStack)
    {
        return ChemicalBase.readFromNBT(itemStack.getTagCompound());
    }

    public static ItemStack getItemStackForChemical(ChemicalBase chemicalBase)
    {
        ItemStack itemStack = new ItemStack(ItemRegistry.chemicalItem);
        NBTTagCompound tag = new NBTTagCompound();
        chemicalBase.writeToNBT(tag);
        itemStack.setTagCompound(tag);
        return itemStack;
    }
}
