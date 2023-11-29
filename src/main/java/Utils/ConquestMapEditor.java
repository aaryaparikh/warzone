package Utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Models.Continent;
import Models.Country;
import Models.GameMap;

/**
 * The class represents a tool for editing Conquest maps.
 */
public class ConquestMapEditor {
	/**
	 * The class represents a Conquest map consisting of map information
	 */
	public static class ConquestMap {

		/**
		 * Holds the information about the Conquest map.
		 */
		public String d_mapInfo;

		/**
		 * Represents the actual game map associated with this Conquest map.
		 */
		public GameMap d_gameMap;

		/**
		 * Constructs a {@code ConquestMap} object with the specified map information
		 * and game map.
		 *
		 * @param p_mapInfo The information about the Conquest map.
		 * @param p_gameMap The associated game map.
		 */
		public ConquestMap(String p_mapInfo, GameMap p_gameMap) {
			this.d_mapInfo = p_mapInfo;
			this.d_gameMap = p_gameMap;
		}
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded conquest map.
	 */
	public ConquestMap loadConquestMap(String p_fileName) {
		String l_text = "";
		GameMap l_map = new GameMap();
		ConquestMap l_conquestMap = new ConquestMap(null, null);

		try {
			File l_mapFile = new File("src/main/resources/ConquestMaps/" + p_fileName);

			// Check whether the file exist in the path
			if (!l_mapFile.exists()) {
				System.out.println("Conquest Map dosen't exist at \"" + "src/main/resources/ConquestMaps/" + p_fileName
						+ "\", a new map is created.");
				l_mapFile.createNewFile();
				l_text = "[Map]\n";
				l_text = l_text + "\n[Countries]\n";
				l_text = l_text + "\n[Territories]\n";
				FileWriter l_writer = new FileWriter("src/main/resources/ConquestMaps/" + p_fileName);
				l_writer.write(l_text);
				l_writer.close();
			} else {
				@SuppressWarnings("resource")
				Scanner l_reader = new Scanner(l_mapFile);
				while (l_reader.hasNextLine()) {
					String l_textLine = l_reader.nextLine();
					if (l_textLine.equals("[Continents]") || l_textLine.equals("[Territories]"))
						l_text += "\n";
					l_text += l_textLine;
					if (l_textLine.length() != 0)
						l_text += "\n";
				}

				// Read map information form the file
				String l_mapInfo = l_text.split("\n\n")[0];
				String l_continents[] = l_text.split("\n\n")[1].split("\n");
				String l_territories[] = l_text.split("\n\n")[2].split("\n");

				for (int l_i = 1; l_i < l_continents.length; l_i++) {
					String l_continentName = l_continents[l_i].split("=")[0];
					int l_continentId = l_i;
					String l_continentValueString = l_continents[l_i].split("=")[1];
					int l_continentValue = Integer.parseInt(l_continentValueString.split(" ")[0]);
					l_map.addContinent(l_continentId, l_continentValue, l_continentName);
				}

				// To save neighbor country name and update to id later
				List<List<String>> l_neighborsOfCountryList = new ArrayList<>();

				for (int l_i = 1; l_i < l_territories.length; l_i++) {
					String l_countryName = l_territories[l_i].split(",")[0];
					int l_countryId = l_i;
					String l_continentName = l_territories[l_i].split(",")[3];
					int l_continentId = 0;

					for (Continent l_continent : l_map.getContinents()) {
						if (l_continent.getContinentName() != null)
							if (l_continent.getContinentName().equals(l_continentName))
								l_continentId = l_continent.getContinentId();
					}

					String l_xAxis = l_territories[l_i].split(",")[1];
					String l_yAxis = l_territories[l_i].split(",")[2];

					l_map.addCountry(l_countryId, l_continentId, l_countryName, l_xAxis, l_yAxis);

					List<String> l_neighborsOfCountry = new ArrayList<>();
					l_neighborsOfCountry.add(l_countryName);

					for (int l_n = 4; l_n < l_territories[l_i].split(",").length; l_n++) {
						l_neighborsOfCountry.add(l_territories[l_i].split(",")[l_n]);
					}

					l_neighborsOfCountryList.add(l_neighborsOfCountry);
				}

				// Update neighbors
				for (List<String> l_neighborList : l_neighborsOfCountryList) {
					String l_countryName = l_neighborList.get(0);
					int l_countryId = -1;
					for (Country l_country : l_map.getCountries())
						if (l_countryName.equals(l_country.d_countryName))
							l_countryId = l_country.getCountryId();

					l_neighborList.remove(0);
					for (String neighbor : l_neighborList)
						for (Country l_country : l_map.getCountries())
							if (neighbor.equals(l_country.d_countryName))
								l_map.addNeighbor(l_countryId, l_country.getCountryId());
				}

				l_conquestMap.d_mapInfo = l_mapInfo;
				l_conquestMap.d_gameMap = l_map;
				return l_conquestMap;
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error when loading conquest map.");
		}
		return l_conquestMap;
	}
}
