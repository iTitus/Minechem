package minechem;

import net.minecraft.util.ResourceLocation;

import minechem.helper.LogHelper;
import minechem.helper.StringHelper;
import repackage.net.afterlifelochie.fontbox.api.ITracer;

/*
 * A compendium of all constants for the mod, such as the modID and ResourceLocations, as well as more general things like color codes
 */
public class Compendium {

	public static final class Gui {
		public static final int JOURNAL_ID = 0;
		public static final int MICROSCOPE_ID = 1;
		public static final int CENTRIFUGE_ID = 2;
		public static final int ELECTRIC_CRUCIBLE_ID = 3;
		public static final int ELECTROLYSIS_ID = 4;
	}


	public static final class Color {

		public static final class TrueColor {
			public static final int black = -16777216;
			public static final int blue = -16776961;
			public static final int cyan = -16711681;
			public static final int darkGrey = -12303292;
			public static final int grey = -7829368;
			public static final int green = -16711936;
			public static final int lightGrey = -3355444;
			public static final int magenta = -65281;
			public static final int red = -65536;
			public static final int transparent = 0;
			public static final int white = -1;
			public static final int yellow = -256;
		}

	}

	public static final class Config {
		public static final String configPrefix = "config/minechem/";
		public static final String assetPrefix = "/assets/minechem/";
		public static final String dataJsonPrefix = "data/";
		public static final String elementsDataJson = "elementsData.json";
		public static final String moleculesDataJson = "moleculesData.json";
		public static final String researchPagesJson = "pages.json";
		public static final String playerResearchData = "minechem/researchData.json";
	}

	public static final class MetaData {
		public static final String patreon = "https://www.patreon.com/jakimfett";
	}

	public static final class Naming {
		public static final String id = "minechem";
		public static final String name = "Minechem";
		public static final String fontBox = "fontbox";
		public static final String opticalMicroscope = "optical_microscope";
		public static final String electrolysis = "electrolysis";
		public static final String electricCrucible = "electric_crucible";
		public static final String centrifuge = "centrifuge";
		public static final String journal = "journal";
		public static final String chemical = "chemical";
		public static final String augmented = "augmented";
		public static final String light = "light";
		public static final String redstone = "redstone";

		public static final class Mods {
			public static final String computerCraft = "ComputerCraft";
			public static final String openBlocks = "OpenBlocks";
			public static final String openComputers = "OpenComputers";
		}
	}

	public static final class Fontbox {
		public static ITracer tracer() {
			return new Tracer();
		}

		public static final class Tracer implements ITracer {
			@Override
			public void trace(Object... params) {
				LogHelper.debug("Fontbox trace: " + StringHelper.toString(", ", params));
			}

			@Override
			public void warn(Object... params) {
				LogHelper.warn("Fontbox warn: " + StringHelper.toString(", ", params));
			}

			@Override
			public boolean enableAssertion() {
				return false;
			}
		}
	}

	public static final class Resource {
		public static final class Icon {
			public static final ResourceLocation patreon = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Icon.patreon);
		}

		public static final class GUI {
			public static final ResourceLocation journal = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.journal);
			public static final ResourceLocation opticalMicroscope = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.opticalMicroscope);
			public static final ResourceLocation achievements = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.achievements);
			public static final ResourceLocation noContent = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.noContent);

			public static ResourceLocation getResourceForStructure(String name) {
				return new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.compounds + name.replaceAll("\\s", "_") + ".png");
			}

			public static final class Element {
				public static final ResourceLocation fluidTank = new ResourceLocation(Compendium.Naming.id, Texture.GUI.Element.fluidTank);
			}
		}

		public static final class Model {
			public static final ResourceLocation microscope = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Model.opticalMicroscope);
			public static final ResourceLocation electrolysis = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Model.electrolysis);
			public static final ResourceLocation electricCrucible = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Model.electricCrucible);
			public static final ResourceLocation centrifuge = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Model.centrifuge);
		}

		public static final class Tab {
			public static final ResourceLocation right = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.tab_right);
			public static final ResourceLocation left = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.tab_left);
		}

		public static final class Font {
			public static final ResourceLocation danielFont = new ResourceLocation(Compendium.Naming.fontBox, Compendium.Texture.Font.danielFont);
			public static final ResourceLocation danielMetrics = new ResourceLocation(Compendium.Naming.fontBox, Compendium.Texture.Font.danielMetrics);
			public static final ResourceLocation vanilla = new ResourceLocation("textures/font/ascii.png");
		}
	}

	public static final class Texture {
		public static final String prefix = Compendium.Naming.id + ":";

		public static final class IIcon {

		}

		public static final class Icon {

			private static final String prefix = "textures/icons/";
			public static final String patreon = Compendium.Texture.Icon.prefix + "patreon.png";
		}

		public static final class Item {

			private static final String prefix = "textures/item/";
			private static final String suffix = "Icon.png";
			public static final String augmentDefault = Compendium.Texture.Item.prefix + "augment" + Compendium.Texture.Item.suffix;
		}

		public static final class GUI {
			private static final String prefix = "textures/gui/";
			public static final String compounds = Compendium.Texture.GUI.prefix + "compounds/";

			public static final String blankMachine = Compendium.Texture.GUI.prefix + "blankMachine.png";
			public static final String journal = Compendium.Texture.GUI.prefix + "journal.png";
			public static final String opticalMicroscope = Compendium.Texture.GUI.prefix + "opticalMicroscope.png";
			public static final String tab_left = Compendium.Texture.GUI.prefix + "tabLeft.png";
			public static final String tab_right = Compendium.Texture.GUI.prefix + "tabRight.png";
			public static final String achievements = Compendium.Texture.GUI.prefix + "achievementPage.png";
			public static final String noContent = Compendium.Texture.GUI.prefix + "noContent.png";

			public static final class Element {
				public static final String fluidTank = Compendium.Texture.GUI.prefix + "fluidTank.png";
			}
		}

		public static final class Model {
			public static final String prefix = "textures/models/";
			public static final String opticalMicroscope = Compendium.Texture.Model.prefix + "optical_microscope.png";
			public static final String electrolysis = Compendium.Texture.Model.prefix + "electrolysis.png";
			public static final String electricCrucible = Compendium.Texture.Model.prefix + "electric_crucible.png";
			public static final String centrifuge = Compendium.Texture.Model.prefix + "centrifuge.png";
		}

		public static final class Font {
			public static final String prefix = "textures/fonts/";
			public static final String danielFont = Compendium.Texture.Font.prefix + "daniel.png";
			public static final String danielMetrics = Compendium.Texture.Font.prefix + "daniel.metrics.xml";
		}
	}

	public static final class Version {
		public static final String full = "@MODVERSION@";
	}

	public class NBTTags {
		public static final String slot = "slot";
		public static final String stack = "stack";
		public static final String inventory = "inventory";
		public static final String timer = "timer";
		public static final String count = "count";
		public static final String reset = "reset";
		public static final String fluid = "fluid";
		public static final String fluidNull = "fluid_null";
		public static final String amount = "amount";
		public static final String capacity = "capacity";
		public static final String name = "name";
		public static final String x = "xCoord";
		public static final String y = "xCoord";
		public static final String z = "zCoord";
		public static final String nbt = "nbt";
		public static final String item = "item";
		public static final String damage = "damage";
	}
}
