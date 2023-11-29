package Utils;

import org.junit.jupiter.api.Test;

import Models.GameMap;

class MapEditorTest {

	/**
	 * Default Game map maker
	 *
	 * @param p_gameMap game map
	 */
	private void defaultGameMap(GameMap p_gameMap) {
		p_gameMap.addContinent(1, 3);
		p_gameMap.addContinent(2, 5);

		p_gameMap.addCountry(1, 1);
		p_gameMap.addCountry(2, 1);
		p_gameMap.addCountry(7, 2);

		p_gameMap.addNeighbor(1, 2);
		p_gameMap.addNeighbor(2, 1);
		p_gameMap.addNeighbor(7, 1);
		p_gameMap.addNeighbor(1, 7);
	}

	@Test
	void test() {
		GameMap d_map = new GameMap();
		defaultGameMap(d_map);
		MapEditor d_mapEditor = new MapEditor(d_map);
		GameMap l_map = d_mapEditor.loadMap("thirdmap.txt");

		System.out.println(l_map.equals(d_map));

	}

}
