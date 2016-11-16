package repackage.net.afterlifelochie.demo;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDemoBook extends Item {

	public ItemDemoBook() {
		super();
		setTextureName("book_normal");
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote)
			Minecraft.getMinecraft().displayGuiScreen(new GuiDemoBook());
		return par1ItemStack;
	}
}
