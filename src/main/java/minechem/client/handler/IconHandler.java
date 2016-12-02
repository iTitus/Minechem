package minechem.client.handler;

import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.renderer.texture.IIconRegister;

import net.minecraftforge.client.event.TextureStitchEvent;

import minechem.common.Compendium;
import minechem.common.Compendium.Texture.IIcon;

public class IconHandler {

	private static Map<String, IIcon> icons = new TreeMap<String, IIcon>();

	public static void addIcon(String iconName, String iconPath, IIconRegister iconRegistry) {
		icons.put(iconName, iconRegistry.registerIcon(iconPath));
	}

	public static IIcon getIcon(String iconName) {
		if (icons.containsKey(iconName)) {
			return icons.get(iconName);
		}
		return icons.get("default");
	}

	public static void registerIcons(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureType() != 0) {
			if (event.getMap().getTextureType() == 1) {
				IconHandler.addIcon("default", Compendium.Naming.id + ":guitab/default", event.map);
				IconHandler.addIcon("patreon", Compendium.Naming.id + ":guitab/patreon", event.map);
			}
		}
	}
}
