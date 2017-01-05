package minechem.common.chemical;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import minechem.common.item.ItemChemical;
import minechem.common.util.Jenkins;

/**
 * The base for chemicals This class will hold all functions that will shared by the {@link minechem.common.chemical.Element} and {@link minechem.common.chemical.Molecule} This way they can be handled as being the
 * same
 */
public abstract class ChemicalBase {

	public final Form form;// @TODO: this should become a temperature so that the state can be defined on that maybe?
	public final String fullName;
	public int colour;

	public ChemicalBase(String fullName, String form, int colour) {
		this.fullName = fullName;
		this.form = Form.valueOf(form);
		this.colour = colour;
	}

	public static ChemicalBase readFromNBT(NBTTagCompound tag) {
		if (tag != null && tag.hasKey("fullName")) {
			return Jenkins.get(tag.getString("fullName"));
		}
		return null;
	}

	/**
	 * Used for logging the {@link minechem.common.chemical.ChemicalBase} to the {@link net.minecraftforge.fml.common.FMLLog}
	 */
	public abstract void log();

	/**
	 * Get the short name for the {@link minechem.common.chemical.ChemicalBase}
	 *
	 * @return a short String representation
	 */
	public abstract String getFormula();

	/**
	 * Shorthand for checking if it is an element or not so that instanceof is not needed
	 *
	 * @return true if it is an element
	 */
	public abstract boolean isElement();

	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setString("fullName", this.fullName);
		tag.setBoolean("isElement", isElement());
		return tag;
	}

	public ItemStack getItemStack() {
		return ItemChemical.getItemStackForChemical(this);
	}

	public int getColour() {
		return colour;
	}

	public abstract List<String> getToolTip();

	public abstract String getResearchKey();

	@Override
	public String toString() {
		return getFormula();
	}

	public static enum Form {
		solid, liquid, gas, plasma
	}
}