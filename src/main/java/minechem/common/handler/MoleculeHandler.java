package minechem.common.handler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

import org.apache.logging.log4j.Level;

import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.registry.MoleculeRegistry;
import minechem.common.util.ColourHelper;
import minechem.common.util.FileHelper;
import minechem.common.util.LogHelper;

public class MoleculeHandler {

	public static void init() {
		String[] fileDestSource = new String[2];
		fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.moleculesDataJson;
		fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.moleculesDataJson;
		InputStream inputStream = FileHelper.getJsonFile(MoleculeHandler.class, fileDestSource, Config.useDefaultMolecules);
		readFromStream(inputStream);
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				LogHelper.exception("Cannot close stream!", e, Level.WARN);
			}
		}
	}

	private static void readFromStream(InputStream stream) {
		JsonReader jReader = new JsonReader(new InputStreamReader(stream));
		JsonParser parser = new JsonParser();

		JsonObject object = parser.parse(jReader).getAsJsonObject();

		readFromObject(object.entrySet(), 0);

		//saveJson(object);
		LogHelper.info("Total of " + MoleculeRegistry.getInstance().getMolecules().size() + " molecules registered");
	}

	public static void saveJson(JsonObject object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		json = json.replaceAll("\\{", "{\n\t\t").replaceAll(",", ",\n\t\t").replaceAll("}", "\n\t}");
		try {
			FileWriter writer = new FileWriter("D:\\output.json");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFromObject(Set<Map.Entry<String, JsonElement>> elementSet, int run) {
		if (elementSet.isEmpty()) {
			return;
		} else if (run > 4) {
			for (Map.Entry<String, JsonElement> moleculeEntry : elementSet) {
				LogHelper.warn("Molecule Parsing Error: " + moleculeEntry.getKey() + " cannot be parsed.");
			}
		} else {
			Map<String, JsonElement> unparsed = new HashMap<String, JsonElement>();
			for (Map.Entry<String, JsonElement> moleculeEntry : elementSet) {
				if (!moleculeEntry.getValue().isJsonObject()) {
					continue;
				}
				JsonObject elementObject = moleculeEntry.getValue().getAsJsonObject();
				String form = "liquid";
				if (elementObject.has("MeltingPt") && elementObject.get("MeltingPt").getAsDouble() > 25) {
					form = "solid";
				} else if (elementObject.has("BoilingPt") && elementObject.get("BoilingPt").getAsDouble() < 25) {
					form = "gas";
				}
				int colour = 0;
				if (elementObject.has("Colour") && elementObject.get("Colour").isJsonPrimitive()) {
					JsonPrimitive cInput = elementObject.getAsJsonPrimitive("Colour");
					if (cInput.isString()) {
						colour = ColourHelper.RGB(cInput.getAsString());
					} else if (cInput.isNumber()) {
						colour = cInput.getAsInt();
					}
				}
				if (MoleculeRegistry.getInstance().registerMolecule(
						moleculeEntry.getKey(),
						form,
						colour,
						elementObject.get("Formula").getAsString())) {
					unparsed.put(moleculeEntry.getKey(), moleculeEntry.getValue());
				} else {
//                    if (elementObject.has("SMILES") && !elementObject.has("Height"))
//                    {
//                        int[] result = MoleculeImageParser.parser(moleculeEntry.getKey(), elementObject.get("SMILES").getAsString());
//                        if (result!=null)
//                        {
//                            elementObject.add("Height",new JsonPrimitive(result[0]));
//                            elementObject.add("Width",new JsonPrimitive(result[1]));
//                        }
//                    }
				}
			}
			readFromObject(unparsed.entrySet(), run + 1);
		}
	}
}
