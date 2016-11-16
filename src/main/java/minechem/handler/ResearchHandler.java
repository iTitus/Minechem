package minechem.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;

import minechem.Compendium;
import minechem.Minechem;
import minechem.helper.FileHelper;
import minechem.helper.LogHelper;
import minechem.registry.ResearchRegistry;

public class ResearchHandler {
	/**
	 * Save the current research map
	 */
	public static void saveResearch() {
		try {
			OutputStream outputStream = FileUtils.openOutputStream(new File(Minechem.proxy.getCurrentSaveDir() + "/data/" + Compendium.Config.playerResearchData));
			JsonWriter jWriter = new JsonWriter(new OutputStreamWriter(outputStream));
			jWriter.setIndent("    ");
			jWriter.beginObject();
			for (Map.Entry<UUID, Set<String>> entry : ResearchRegistry.getInstance().getPlayerResearchMap().entrySet()) {
				jWriter.name(entry.getKey().toString()).beginObject();
				// @TODO: maybe find a way to add this for readability
				//jWriter.name("displayName").value();
				jWriter.name("research").beginArray();
				for (String research : entry.getValue()) {
					jWriter.value(research);
				}
				jWriter.endArray();
				jWriter.endObject();
			}
			jWriter.endObject();
			jWriter.close();
			try {
				outputStream.close();
			} catch (IOException e) {
				LogHelper.exception("Cannot close stream!", e, Level.WARN);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Read existing research
	 */
	public static void readPlayerResearch() {
		String fileName = Minechem.proxy.getCurrentSaveDir() + "/data/" + Compendium.Config.playerResearchData;
		if (FileHelper.doesFileExist(fileName)) {
			InputStream inputStream = FileHelper.getFile(fileName);
			readPlayerResearch(inputStream);
		}
	}

	private static void readPlayerResearch(InputStream inputStream) {
		JsonReader jReader = new JsonReader(new InputStreamReader(inputStream));
		JsonParser parser = new JsonParser();

		Set<Map.Entry<String, JsonElement>> playerSet = parser.parse(jReader).getAsJsonObject().entrySet();
		int count = 0;
		for (Map.Entry<String, JsonElement> playerEntry : playerSet) {
			if (!playerEntry.getValue().isJsonObject()) {
				continue;
			}
			JsonObject playerObject = playerEntry.getValue().getAsJsonObject();
			for (JsonElement research : playerObject.getAsJsonArray("research")) {
				ResearchRegistry.getInstance().addResearch(
						UUID.fromString(playerEntry.getKey()),
						research.getAsString()
				);
			}
			count++;
		}
		LogHelper.info("Total of " + count + " researchers registered");
	}
}
