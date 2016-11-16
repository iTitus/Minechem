package minechem.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;

import minechem.Compendium;
import minechem.Config;

/**
 * Various functions to help with files
 */
public class FileHelper {
	/**
	 * Thanks to @tterrag1098 for letting me have this bit of code
	 *
	 * @param classFromJar    A class from the jar in question
	 * @param fileSource      File to copy, prepended with "/assets/minechem/"
	 * @param fileDestination File to copy to
	 */
	public static void copyFromJar(Class<?> classFromJar, String fileSource, String fileDestination) {
		LogHelper.debug("Copying file " + fileSource + " from jar");

		URL source = classFromJar.getResource(Compendium.Config.assetPrefix + fileSource);
		File destination = new File(fileDestination);

		try {
			FileUtils.copyURLToFile(source, destination);
		} catch (IOException e) {
			LogHelper.exception("Couldn't load file from jar!", e, Level.WARN);
			LogHelper.info("This is a bug, please report it to the mod author!");

			if (Config.debugMode) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Read a file from jar
	 *
	 * @param classFromJar A class from the jar in question
	 * @param file         File to read, prepended with "/assets/minechem/"
	 * @return the InputStream
	 */
	public static InputStream getInputStreamFromJar(Class<?> classFromJar, String file) {
		LogHelper.debug("Reading file " + file + " from jar");
		URL url = classFromJar.getResource(Compendium.Config.assetPrefix + file);
		try {
			return url.openStream();
		} catch (IOException e) {
			LogHelper.exception("Couldn't read file from jar!", e, Level.WARN);
			LogHelper.info("This is a bug, please report it to the mod author!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get a JSON file from the default data location
	 *
	 * @param classFromJar the class that makes the call
	 * @param file         String[2] array, 0 is source, 1 is destination
	 * @param alwaysCopy   set ot true to always make a fresh copy
	 * @return the requested JSON file in an InputStream. Throws IOException if something goes wrong
	 */
	public static InputStream getJsonFile(Class<?> classFromJar, String[] file, boolean alwaysCopy) {
		File dataFile = new File(file[1]);

		if (!dataFile.isFile() || alwaysCopy) {
			FileHelper.copyFromJar(classFromJar, file[0], file[1]);

			// If the file was copied, get the file again
			dataFile = new File(file[1]);
		}

		if (dataFile.isFile()) {
			LogHelper.debug("JSON file exists");
			InputStream stream = null;
			try {
				stream = new FileInputStream(dataFile);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			return stream;
		}
		throw new RuntimeException(); // this should never be reached
	}

	/**
	 * Check if file exits in the jar
	 *
	 * @param classFromJar
	 * @param fileSource
	 * @return
	 */
	public static boolean doesFileExistInJar(Class<?> classFromJar, String fileSource) {
		try {
			URL source = classFromJar.getResource(Compendium.Config.assetPrefix + fileSource);
			return source != null;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Check if file exits in the config folder
	 *
	 * @param file
	 * @return
	 */
	public static boolean doesFileExist(String file) {
		try {
			new FileInputStream(FileUtils.getFile(file));
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	/**
	 * Get a file from the config folder
	 *
	 * @param file the fileName
	 * @return
	 */
	public static FileInputStream getFile(String file) {
		try {
			return new FileInputStream(FileUtils.getFile(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
