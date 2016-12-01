package minechem.apparatus.prefab.tileEntity;

import com.google.common.base.Strings;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;

import net.minecraftforge.common.util.Constants;

import minechem.Compendium;

public class BaseTileEntity extends TileEntity implements IWorldNameable {

	protected final String name;
	protected String customName;

	public BaseTileEntity(String name) {
		super();
		this.name = "container." + name + ".name";
	}

	public void sync() {
		if (world != null) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		sync();
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		if (compound != null) {
			readFromCustomNBT(compound);
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, -1, writeToCustomNBT(new NBTTagCompound()));
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readFromCustomNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeToCustomNBT(compound);
		return compound;
	}

	public void readFromCustomNBT(NBTTagCompound compound) {
		if (compound.hasKey(Compendium.NBTTags.customName, Constants.NBT.TAG_STRING)) {
			customName = compound.getString(Compendium.NBTTags.customName);
		} else {
			customName = null;
		}
	}

	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		if (hasCustomName()) {
			compound.setString(Compendium.NBTTags.customName, customName);
		}
		return compound;
	}

	@Override
	public boolean canRenderBreaking() {
		return true;
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : name;
	}

	public String getDefaultName() {
		return name;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public boolean hasCustomName() {
		return !Strings.isNullOrEmpty(customName);
	}

	@Override
	public ITextComponent getDisplayName() {
		return hasCustomName() ? new TextComponentString(getCustomName()) : new TextComponentTranslation(getDefaultName());
	}

}
