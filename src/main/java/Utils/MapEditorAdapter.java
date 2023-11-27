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
 * Map Editor Adapter in Adapter Pattern
 * 
 * @author YURUI
 */
public class MapEditorAdapter extends MapEditor {
	/**
	 * Constructor for map editor adapter
	 *
	 * @param p_gameMap Current map in game engine
	 */
	public MapEditorAdapter(GameMap p_gameMap) {
		super(p_gameMap);
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	public GameMap editMap(String p_fileName) {
		String l_text = "";
		GameMap l_map = new GameMap();

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

				// validate the loaded map
				d_gameMap.d_mapInfo = l_mapInfo;
				d_countries.clear();
				d_countries.addAll(l_map.getCountries());
				d_continents.clear();
				d_continents.addAll(l_map.getContinents());
				d_gameMap.setD_continentCount(l_map.getContinents().size());
				d_gameMap.setD_countryCount(l_map.getCountries().size());
				validateMap();
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Edit map fail.");
		}
		return d_gameMap;
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	public GameMap loadMap(String p_fileName) {
		String l_text = "";
		GameMap l_map = new GameMap();

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

				// validate the loaded map
				GameMap l_mapBuffer = d_gameMap;
				d_gameMap = l_map;
				d_gameMap.d_mapInfo = l_mapInfo;

				if (!validateMap())
					return l_mapBuffer;
				else {
					d_gameMap.setD_continentCount(l_map.getContinents().size());
					d_gameMap.setD_countryCount(l_map.getCountries().size());
					return d_gameMap;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Edit map fail.");
		}
		return d_gameMap;
	}

	/**
	 * Writes the map information to a file with the given filename.
	 *
	 * @param p_fileName The name of the file to write to.
	 */
	public void write(String p_fileName) {
		if (!validateMap()) {
			System.out.println("Since invalid Map, it can't be saved as Conquest Map.");
			return;
		}
		try {
			String l_text = "";
			if (d_gameMap.d_mapInfo != null) {
				l_text += d_gameMap.d_mapInfo + "\n";
			}

			l_text += "\n[Continents]\n";
			for (Continent l_continent : d_continents) {
				if (l_continent.getContinentName() != null)
					l_text = l_text + l_continent.getContinentName() + "=" + l_continent.getContinentValue() + "\n";
			}

			l_text = l_text + "\n[Territories]\n";
			for (Country l_country : d_countries) {
				if (l_country.d_countryName != null)
					l_text += l_country.d_countryName + ",";
				else
					l_text += l_country.getCountryId() + ",";

				if (l_country.d_xAxis != null)
					l_text += l_country.d_xAxis + ",";
				if (l_country.d_yAxis != null)
					l_text += l_country.d_yAxis;

				for (Continent l_continent : d_continents)
					if (l_continent.getContinentId() == l_country.getContinentId()) {
						if (l_continent.getContinentName() != null)
							l_text += "," + l_continent.getContinentName();
						else
							l_text += "," + l_country.getContinentId();
					}

				for (int l_neighbor : l_country.getNeighborCountries())
					for (Country l_country2 : d_countries)
						if (l_country2.getCountryId() == l_neighbor) {
							if (l_country2.d_countryName != null)
								l_text += "," + l_country2.d_countryName;
							else
								l_text += "," + l_country2.getCountryId();
						}
				l_text += "\n";
			}

			FileWriter l_writer = new FileWriter("src/main/resources/ConquestMaps/" + p_fileName);
			l_writer.write(l_text);
			l_writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
