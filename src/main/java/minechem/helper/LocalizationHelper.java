package minechem.helper;

import java.util.IllegalFormatException;

import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 *
 */
public class LocalizationHelper {
	public static String getLocalString(String key) {
		return getLocalString(key, false);
	}

	public static String getLocalString(String key, boolean capitalize) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			String localString;
			if (I18n.canTranslate(key)) {
				localString = I18n.translateToLocal(key);
			} else {
				localString = I18n.translateToFallback(key);
			}
			if (capitalize) {
				localString = localString.toUpperCase();
			}
			return localString;
		}
		return key;
	}

	public static String getFormattedString(String key, Object... objects) {
		return getFormattedString(key, false, objects);
	}

	public static String getFormattedString(String key, boolean capitalize, Object... objects) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			String localString;
			if (I18n.canTranslate(key)) {
				localString = I18n.translateToLocalFormatted(key, objects);
			} else {
				localString = I18n.translateToFallback(key);
				try {
					localString = String.format(localString, objects);
				} catch (IllegalFormatException illegalformatexception) {
					return "Format error: " + key;
				}
			}
			if (capitalize) {
				localString = localString.toUpperCase();
			}
			return localString;
		}
		return key;
	}
}
