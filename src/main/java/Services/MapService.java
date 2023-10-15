package Services;

import java.util.Scanner;

import Models.GameMap;

/**
 * MapService for all command in map edit phase
 * 
 * @author Aarya
 */
public class MapService {
	/**
	 * get information of current map
	 */
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
	 * @param p_args main function parameter
	 */
	public void main(String[] p_args) {
		@SuppressWarnings("resource")
		Scanner l_sc = new Scanner(System.in);

		while (true) {
			String l_userInput;
			l_userInput = l_sc.nextLine();
			String l_commands[] = l_userInput.split(" ");

			// solve the command
			switch (l_commands[0]) {
			case "showmap":
				d_map.getD_mapView().showMap();
				break;
			case "savemap":
				if (l_commands.length < 2)
					System.out.println("Please enter file path to save map.");
				else
					d_map.d_mapEditor.write(l_commands[1]);
				break;
			case "validatemap":
				if (d_map.d_mapEditor.validateMap()) {
					System.out.println("Valid Map");
				}
				break;
			case "editmap":
				if (l_commands.length < 2)
					System.out.println("Please enter file path to load map.");
				else
					d_map.d_mapEditor.editMap(l_commands[1]);
				break;
			case "end":
				if (d_map.d_mapEditor.validateMap())
					return;
				else
					System.out.println("Since invalid map, can't move to play.");
				break;

			// edit continent with two kinds of options
			case "editcontinent":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing continent.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":

							// make sure -add with two parameters
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some continents but no more enough parameter for adding.");
							// make sure no -add or -remove being recognized as parameter
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter.");
								l_i += 1;
							} else {
								// make sure all parameter is integer otherwise an exception
								try {
									d_map.addContinent(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":

							// make sure -add with two parameters
							if (l_i + 1 >= l_commands.length)
								System.out.println("Edit some continents but no more enough parameter for removing.");
							// make sure no -add or -remove being recognized as parameter
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else {
								try {
									d_map.removeContinent(Integer.parseInt(l_commands[l_i + 1]));
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

			// edit country with two kind of options
			case "editcountry":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing country.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":

							// make sure -add with two parameters
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some countries but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									d_map.addCountry(Integer.parseInt(l_commands[l_i + 1]),
											Integer.parseInt(l_commands[l_i + 2]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":
							// make sure -add with two parameters
							if (l_i + 1 >= l_commands.length)
								System.out.println("Edit some countries but no more enough parameter for removing.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else {
								try {
									d_map.removeCountry(Integer.parseInt(l_commands[l_i + 1]));
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

			// edit neighbor with two kinds of options
			case "editneighbor":
				if (l_commands.length < 4)
					System.out.println("Please enter enough parameter for editing neighbor.");
				else
					for (int l_i = 1; l_i < l_commands.length;) {
						switch (l_commands[l_i]) {
						case "-add":
							// make sure -add with two parameters
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some neighbors but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									if (!l_commands[l_i + 1].equals(l_commands[l_i + 2]))
										d_map.addNeighbor(Integer.parseInt(l_commands[l_i + 1]),
												Integer.parseInt(l_commands[l_i + 2]));
									d_map.addNeighbor(Integer.parseInt(l_commands[l_i + 2]),
											Integer.parseInt(l_commands[l_i + 1]));
								} catch (Exception e) {
									System.out.println(e);
								}
								l_i += 2;
							}
							break;
						case "-remove":
							// make sure -add with two parameters
							if (l_i + 2 >= l_commands.length)
								System.out.println("Edit some neighbors but no more enough parameter for adding.");
							else if (l_commands[l_i + 1].equals("-add") || l_commands[l_i + 1].equals("-remove"))
								System.out.println("Please don't enter duplicated -add or -remove");
							else if (l_commands[l_i + 2].equals("-add") || l_commands[l_i + 2].equals("-remove")) {
								System.out.println("Please enter enough parameter for last command.");
								l_i += 1;
							} else {
								try {
									if (!l_commands[l_i + 1].equals(l_commands[l_i + 2]))
										d_map.removeNeighbor(Integer.parseInt(l_commands[l_i + 1]),
												Integer.parseInt(l_commands[l_i + 2]));
									d_map.removeNeighbor(Integer.parseInt(l_commands[l_i + 2]),
											Integer.parseInt(l_commands[l_i + 1]));
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
