package Utils;

import Controller.GameEngine;
import Models.GameMap;

/**
 * Handles player commands.
 *
 * @author YURUI
 */
public class MapCommandHandler extends CommandHandler {
	private GameMap d_map;

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public MapCommandHandler(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_map = p_gameEngine.getGameMap();
	}

	/**
	 * Handles map command.
	 *
	 * @param p_command       The player's command.
	 * @param p_currentPlayer The current player.
	 * @return A response string indicating the result of the command.
	 */
	public String handleMapCommand(String[] p_commands) {
		String l_response = null;

		switch (p_commands[0]) {
		case "showmap":
			d_map.getD_mapView().showMap();
			break;
		case "savemap":
			if (p_commands.length < 2) {
				l_response = String.format("Please enter file path to save map.");
				System.out.println(l_response);
			} else {
				d_map.d_mapEditor.write(p_commands[1]);
				l_response = String.format("Map is saved in \"%s.txt\"", p_commands[1]);
				System.out.println(l_response);
				d_logEntryBuffer.setString(l_response);
			}
			break;
		case "validatemap":
			if (d_map.d_mapEditor.validateMap()) {
				l_response = String.format("This Map is valid");
				System.out.println(l_response);
				d_logEntryBuffer.setString(l_response);
			} else {
				l_response = String.format("This Map is invalid");
				d_logEntryBuffer.setString(l_response);
			}
			break;
		case "editmap":
			if (p_commands.length < 2)
				System.out.println("Please enter file path to load map.");
			else {
				d_map.d_mapEditor.editMap(p_commands[1]);
				l_response = String.format("Edit map \"%s.txt\"", p_commands[1]);
				d_logEntryBuffer.setString(l_response);
			}
			break;
		case "next":
			if (d_map.d_mapEditor.validateMap())
				return "Valid";
			else
				System.out.println("Since invalid map, can't move to play.");
			break;

		// edit continent with two kinds of options
		case "editcontinent":
			if (p_commands.length < 3)
				System.out.println("Please enter enough parameter for editing continent.");
			else
				for (int l_i = 1; l_i < p_commands.length;) {
					switch (p_commands[l_i]) {
					case "-add":

						// make sure -add with two parameters
						if (l_i + 2 >= p_commands.length)
							System.out.println("EditMapPhase some continents but no more enough parameter for adding.");
						// make sure no -add or -remove being recognized as parameter
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else if (p_commands[l_i + 2].equals("-add") || p_commands[l_i + 2].equals("-remove")) {
							System.out.println("Please enter enough parameter.");
							l_i += 1;
						} else {
							// make sure all parameter is integer otherwise an exception
							try {
								d_map.addContinent(Integer.parseInt(p_commands[l_i + 1]),
										Integer.parseInt(p_commands[l_i + 2]));
								l_response = String.format("Add continent \"%s\" with value \"%s\". ",
										p_commands[l_i + 1], p_commands[l_i + 2]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 2;
						}
						break;
					case "-remove":

						// make sure -add with two parameters
						if (l_i + 1 >= p_commands.length)
							System.out
									.println("EditMapPhase some continents but no more enough parameter for removing.");
						// make sure no -add or -remove being recognized as parameter
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else {
							try {
								d_map.removeContinent(Integer.parseInt(p_commands[l_i + 1]));
								l_response = String.format("Remove continent \"%s\". ", p_commands[l_i + 1]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 1;
						}
						break;
					default:
						System.out.println("Edit some continents, but stop when invalid");
						l_i = p_commands.length;
						break;
					}
					l_i += 1;
				}
			break;

		// edit country with two kind of options
		case "editcountry":
			if (p_commands.length < 3)
				System.out.println("Please enter enough parameter for editing country.");
			else
				for (int l_i = 1; l_i < p_commands.length;) {
					switch (p_commands[l_i]) {
					case "-add":

						// make sure -add with two parameters
						if (l_i + 2 >= p_commands.length)
							System.out.println("EditMapPhase some countries but no more enough parameter for adding.");
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else if (p_commands[l_i + 2].equals("-add") || p_commands[l_i + 2].equals("-remove")) {
							System.out.println("Please enter enough parameter for last command.");
							l_i += 1;
						} else {
							try {
								d_map.addCountry(Integer.parseInt(p_commands[l_i + 1]),
										Integer.parseInt(p_commands[l_i + 2]));
								l_response = String.format("Add country \"%s\" to continent \"%s\". ",
										p_commands[l_i + 1], p_commands[l_i + 2]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 2;
						}
						break;
					case "-remove":
						// make sure -add with two parameters
						if (l_i + 1 >= p_commands.length)
							System.out
									.println("EditMapPhase some countries but no more enough parameter for removing.");
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else {
							try {
								d_map.removeCountry(Integer.parseInt(p_commands[l_i + 1]));
								l_response = String.format("Remove country \"%s\". ", p_commands[l_i + 1]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 1;
						}
						break;
					default:
						System.out.println("Edit some countries, but stop when invalid");
						l_i = p_commands.length;
						break;
					}
					l_i += 1;
				}
			break;

		// edit neighbor with two kinds of options
		case "editneighbor":
			if (p_commands.length < 4)
				System.out.println("Please enter enough parameter for editing neighbor.");
			else
				for (int l_i = 1; l_i < p_commands.length;) {
					switch (p_commands[l_i]) {
					case "-add":
						// make sure -add with two parameters
						if (l_i + 2 >= p_commands.length)
							System.out.println("EditMapPhase some neighbors but no more enough parameter for adding.");
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else if (p_commands[l_i + 2].equals("-add") || p_commands[l_i + 2].equals("-remove")) {
							System.out.println("Please enter enough parameter for last command.");
							l_i += 1;
						} else {
							try {
								if (!p_commands[l_i + 1].equals(p_commands[l_i + 2]))
									d_map.addNeighbor(Integer.parseInt(p_commands[l_i + 1]),
											Integer.parseInt(p_commands[l_i + 2]));
								d_map.addNeighbor(Integer.parseInt(p_commands[l_i + 2]),
										Integer.parseInt(p_commands[l_i + 1]));
								l_response = String.format("Add neighbor between \"%s\" and \"%s\". ",
										p_commands[l_i + 1], p_commands[l_i + 2]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 2;
						}
						break;
					case "-remove":
						// make sure -add with two parameters
						if (l_i + 2 >= p_commands.length)
							System.out.println("EditMapPhase some neighbors but no more enough parameter for adding.");
						else if (p_commands[l_i + 1].equals("-add") || p_commands[l_i + 1].equals("-remove"))
							System.out.println("Please don't enter duplicated -add or -remove");
						else if (p_commands[l_i + 2].equals("-add") || p_commands[l_i + 2].equals("-remove")) {
							System.out.println("Please enter enough parameter for last command.");
							l_i += 1;
						} else {
							try {
								if (!p_commands[l_i + 1].equals(p_commands[l_i + 2]))
									d_map.removeNeighbor(Integer.parseInt(p_commands[l_i + 1]),
											Integer.parseInt(p_commands[l_i + 2]));
								d_map.removeNeighbor(Integer.parseInt(p_commands[l_i + 2]),
										Integer.parseInt(p_commands[l_i + 1]));
								l_response = String.format("Remove neighbor between \"%s\" and \"%s\". ",
										p_commands[l_i + 1], p_commands[l_i + 2]);
								d_logEntryBuffer.setString(l_response);
							} catch (Exception e) {
								System.out.println(e);
							}
							l_i += 2;
						}
						break;
					default:
						System.out.println("Edit some neighbors, but stop when invalid");
						l_i = p_commands.length;
						break;
					}
					l_i += 1;
				}
			break;
		default:
			System.out.println("Invalid Input");
		}
		return l_response;
	}
}
