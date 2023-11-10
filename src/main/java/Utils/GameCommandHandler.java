package Utils;

import Controller.GameEngine;
import Models.GameMap;
import Models.Player;

/**
 * Handles game commands.
 * 
 * @author YURUI
 */
public class GameCommandHandler extends CommandHandler {

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public GameCommandHandler(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * Handles game setup commands.
	 *
	 * @param p_commands The player's command.
	 * 
	 * @return handle command result
	 */
	public String handleGameCommand(String[] p_commands) {
		String[] l_commands = p_commands;
		String l_response = null;

		switch (l_commands[0]) {
		case "showmap":
			d_gameEngine.getGameMap().getD_mapView().showGameMap();
			break;
		case "loadmap":
			if (l_commands.length < 2)
				System.out.println("Please enter valid map file path");
			else {
				GameMap l_map = d_gameEngine.getGameMap().d_mapEditor.loadMap(l_commands[1]);
				if (!d_gameEngine.getGameMap().equals(l_map)) {
					System.out.println("\"" + l_commands[1] + ".txt\" is loaded as the game map.");
					d_logEntryBuffer.setString("\"" + l_commands[1] + ".txt\" is loaded as the game map.");
					d_gameEngine.setGameMap(l_map);
				}
			}
			break;

		// game player order support multiple options
		case "gameplayer":
			try {
				for (int l_i = 1; l_i < l_commands.length; l_i += 2) {
					Player l_player = new Player(l_commands[l_i + 1], d_gameEngine);
					switch (l_commands[l_i]) {
					case "-add":

						// If number of player exceed number of country, stop adding
						if (d_gameEngine.getPlayers().size() >= d_gameEngine.getGameMap().getCountries().size()) {
							System.out.println("Can't add more gameplayer");
							break;
						}

						if (d_gameEngine.addPlayer(l_player)) {
							l_response = String.format("Player " + l_commands[l_i + 1] + " is added.");
							System.out.println(l_response);
							d_logEntryBuffer.setString(l_response);
						} else
							System.out.println("Player " + l_commands[l_i + 1] + " already exists. Can't add again.");
						break;
					case "-remove":
						if (d_gameEngine.removePlayer(l_player)) {
							l_response = String.format("Player " + l_commands[l_i + 1] + " is removed.");
							System.out.println(l_response);
							d_logEntryBuffer.setString(l_response);
						} else
							System.out.println("Player " + l_commands[l_i + 1] + " don't exist. Can't remove.");
						break;
					default:
						System.out.println("Please enter valid parameter for order gameplayer");
					}
				}
			} catch (Exception e) {
				System.out.println("Invalid command for gameplayer");
			}

			break;

		// before assign countries, ensure there are at least one player and players
		// less than countries.
		case "assigncountries":
			if (d_gameEngine.getPlayers().size() == 0)
				System.out.println("No players, can't assign countries");
			else if (d_gameEngine.getPlayers().size() > d_gameEngine.getGameMap().getCountries().size())
				System.out.println("Can't assign countries because too many players");
			else {
				d_gameEngine.assignCountriesRandomly();
				l_response = String.format("All countries are assigned to players");
				System.out.println(l_response);
				d_logEntryBuffer.setString(l_response);
			}
			break;
		default:
			System.out.println("Invalid Input");
		}
		return null;
	}
}
