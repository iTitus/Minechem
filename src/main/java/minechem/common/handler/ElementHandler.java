package minechem.common.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.apache.logging.log4j.Level;

import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.registry.ElementRegistry;
import minechem.common.util.FileHelper;
import minechem.common.util.LogHelper;

public class ElementHandler {

	public static void init() {

		String[] fileDestSource = new String[2];
		fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.elementsDataJson;
		fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.elementsDataJson;
		InputStream inputStream = FileHelper.getJsonFile(MoleculeHandler.class, fileDestSource, Config.useDefaultElements);
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

		Set<Map.Entry<String, JsonElement>> elementsSet = parser.parse(jReader).getAsJsonObject().entrySet();
		int count = 0;
		for (Map.Entry<String, JsonElement> elementEntry : elementsSet) {
			if (!elementEntry.getValue().isJsonObject()) {
				continue;
			}
			JsonObject elementObject = elementEntry.getValue().getAsJsonObject();
			ElementRegistry.getInstance().registerElement(
					Integer.parseInt(elementEntry.getKey()),
					elementObject.get("longName").getAsString(),
					elementObject.get("shortName").getAsString(),
					elementObject.get("form").getAsString(),
					elementObject.get("type").getAsString(),
					Integer.parseInt(elementObject.get("neutrons").getAsString())
			);
			count++;
		}
		LogHelper.info("Total of " + count + " elements registered");
	}
}
