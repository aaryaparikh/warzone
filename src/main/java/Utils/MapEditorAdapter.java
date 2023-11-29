package Utils;

import java.io.FileWriter;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Utils.ConquestMapEditor.ConquestMap;

/**
 * Represents a map editor adapter which adapts to any of the map format.
 *
 * @author Dev
 */
public class MapEditorAdapter extends MapEditor {
	/**
	 * The tool for editing Conquest maps.
	 */
	ConquestMapEditor d_conquestMapEditor;

	/**
	 * Constructor for map editor adapter
	 *
	 * @param p_gameMap Current map in game engine
	 */
	public MapEditorAdapter(GameMap p_gameMap) {
		super(p_gameMap);
		this.d_conquestMapEditor = new ConquestMapEditor();
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	@Override
	public GameMap editMap(String p_fileName) {
		ConquestMap l_conquestMap = d_conquestMapEditor.loadConquestMap(p_fileName);

		if (l_conquestMap != null) {

			String l_mapInfo = translateMapInfo(l_conquestMap);
			GameMap l_map = translateMap(l_conquestMap);

			if (l_map != null) {
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
		}
		return d_gameMap;
	}

	/**
	 * Loads a map from a file with the given filename.
	 *
	 * @param p_fileName The name of the file to load the map from.
	 * @return The loaded map.
	 */
	@Override
	public GameMap loadMap(String p_fileName) {
		ConquestMap l_conquestMap = d_conquestMapEditor.loadConquestMap(p_fileName);

		if (l_conquestMap != null) {

			String l_mapInfo = translateMapInfo(l_conquestMap);
			GameMap l_map = translateMap(l_conquestMap);

			if (l_map != null) {
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
		}
		return d_gameMap;
	}

	/**
	 * Writes the map information to a file with the given filename.
	 *
	 * @param p_fileName The name of the file to write to.
	 */
	@Override
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

	/**
	 * Translates the map information from a {@code ConquestMap} object.
	 *
	 * @param p_conquestMap The ConquestMap object containing map information.
	 * @return The translated map information as a String.
	 */
	private String translateMapInfo(ConquestMap p_conquestMap) {
		return p_conquestMap.d_mapInfo;
	}

	/**
	 * Translates the game map from a {@code ConquestMap} object.
	 *
	 * @param p_conquestMap The ConquestMap object containing the game map.
	 * @return The translated game map as a {@code GameMap} object.
	 */
	private GameMap translateMap(ConquestMap p_conquestMap) {
		return p_conquestMap.d_gameMap;
	}

}
