package Services;

import java.util.Scanner;

import Models.GameMap;

/**
 * MapService for all command in map edit phase
 */
public class MapService {
	public GameMap d_map;

	/**
	 * Constructor for Map Service
	 *
	 * @param p_gameMap the game map object
	 */
	public MapService(GameMap p_gameMap) {
		d_map = p_gameMap;
	}

	/**
	 * Update the Map in Map Service
	 *
	 * @param p_gameMap the game map object
	 */
	public void setD_map(GameMap p_gameMap) {
		d_map = p_gameMap;
	}

	/**
	 * Main function for map service
	 *
	 * @param args
	 */
	public void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner l_sc = new Scanner(System.in);

		GameMap l_map = d_map;

		while (true) {
			String l_userInput;
			l_userInput = l_sc.nextLine();
			String l_commands[] = l_userInput.split(" ");
			switch (l_commands[0]) {
			case "showmap":
				l_map.getD_mapView().showMap();
				break;
			case "savemap":
				if (l_commands.length < 2)
					System.out.println("Please enter file path to save map.");
				else
					l_map.d_mapEditor.write(l_commands[1]);
				break;
			case "validatemap":
				if (l_map.d_mapEditor.validateMap()) {
					System.out.println("Valid Map");
				}
				break;
			case "editmap":
				if (l_commands.length < 2)
					System.out.println("Please enter file path to load map.");
				else
					l_map = l_map.d_mapEditor.loadMap(l_commands[1]);
				break;
			case "end":
				if (l_map.d_mapEditor.validateMap())
					return;
				else
					System.out.println("Since invalid map, can't move to play.");
				break;
			case "editcontinent":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing continent.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some continents but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter.");
								l_i += 1;
							} else {
								try {
									l_map.addContinent(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":
							if (l_i + 1 >= l_commands.length)
								System.out.println("Edit some continents but no more enough parameter for removing.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else {
								try {
									l_map.removeContinent(Integer.parseInt(l_commands[l_i + 1]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 1;
							}
							break;
						default:
							System.out.println("Edit some continents, but stop when invalid");
							l_i = l_commands.length;
							break;
						}
						l_i += 1;
					}
				break;
			case "editcountry":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing country.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some countries but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									l_map.addCountry(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":
							if (l_i + 1 >= l_commands.length)
								System.out.println("Edit some countries but no more enough parameter for removing.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else {
								try {
									l_map.removeCountry(Integer.parseInt(l_commands[l_i + 1]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 1;
							}
							break;
						default:
							System.out.println("Edit some continents, but stop when invalid");
							l_i = l_commands.length;
							break;
						}
						l_i += 1;
					}
				break;
			case "editneighbor":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing neighbor.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some neighbors but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									l_map.addNeighbor(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some neighbors but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									l_map.removeNeighbor(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						default:
							System.out.println("Edit some neighbors, but stop when invalid");
							l_i = l_commands.length;
							break;
						}
						l_i += 1;
					}
				break;
			default:
				System.out.println("Invalid Input");
			}
		}

	}
}
