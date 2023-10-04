package Utils;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.Orders.DeployOrder;

/**
 * Handles player commands.
 */
public class MapCommandHandler {
	private GameEngine d_gameEngine;

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public MapCommandHandler(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Handles player commands.
	 *
	 * @param p_command       The player's command.
	 * @param p_currentPlayer The current player.
	 * @return A response string indicating the result of the command.
	 */
	public String handlePlayerCommand(String p_command, Player p_currentPlayer) {
		String[] l_command = p_command.split(" ");
		switch (l_command[0]) {
		case "end":
			switch (d_gameEngine.getPhase()) {
			case "edit":
				d_gameEngine.setPhase("start");
				d_gameEngine.getPhaseView().showNextPhaseInfo("start");
				break;
			case "start":
				d_gameEngine.setPhase("play");
				d_gameEngine.getPhaseView().showNextPhaseInfo("play");
				break;
			case "play":
				d_gameEngine.setPhase("end");
				d_gameEngine.getPhaseView().showNextPhaseInfo("end");
				break;
			case "execute":
				d_gameEngine.setPhase("end");
				d_gameEngine.getPhaseView().showNextPhaseInfo("end");
				break;
			}
			break;
		
		// show general map
		case "showmap":
			if (d_gameEngine.getPhase() == "edit")
				d_gameEngine.getGameMap().getD_mapView().showMap();
			else if (d_gameEngine.getPhase() == "play")
				d_gameEngine.getGameMap().getD_mapView().showGameMap();
			break;

		case "savemap":
			d_gameEngine.getGameMap().d_mapEditor.write(l_command[1]);
			break;

		case "validatemap":
			if (d_gameEngine.getGameMap().d_mapEditor.validateMap()) {
				System.out.println("Valid Map");
			}
			break;

		case "loadmap":
			// d_gameEngine.getGameMap().loadMap(l_commands[1]);
			break;

		// Handle editing continents
		case "editcontinent":
			for (int l_i = 1; l_i < l_command.length;) {
				switch (l_command[l_i]) {
				case "-add":
					d_gameEngine.getGameMap().addContinent(Integer.parseInt(l_command[++l_i]),
							Integer.parseInt(l_command[++l_i]));
					l_i++;
					break;
				case "-remove":
					d_gameEngine.getGameMap().removeContinent(Integer.parseInt(l_command[++l_i]));
					l_i++;
					break;
				default:
					System.out.println("Invalid Input");
					break;
				}
			}
			break;

		// Handle editing countries
		case "editcountry":
			for (int l_i = 1; l_i < l_command.length;) {
				switch (l_command[l_i]) {
				case "-add":
					d_gameEngine.getGameMap().addCountry(Integer.parseInt(l_command[++l_i]),
							Integer.parseInt(l_command[++l_i]));
					l_i++;
					break;
				case "-remove":
					d_gameEngine.getGameMap().removeCountry(Integer.parseInt(l_command[++l_i]));
					l_i++;
					break;
				default:
					System.out.println("Invalid Input");
				}
			}
			break;

		// Handle editing neighbors
		case "editneighbor":
			for (int l_i = 1; l_i < l_command.length;) {
				switch (l_command[l_i]) {
				case "-add":
					int l_countryId = Integer.parseInt(l_command[++l_i]);
					int l_neighbor = Integer.parseInt(l_command[++l_i]);
					d_gameEngine.getGameMap().addNeighbor(l_countryId, l_neighbor);
					d_gameEngine.getGameMap().addNeighbor(l_neighbor, l_countryId);
					l_i++;
					break;
				case "-remove":
					int l_rCountryId = Integer.parseInt(l_command[++l_i]);
					int l_rNeighbor = Integer.parseInt(l_command[++l_i]);
					d_gameEngine.getGameMap().removeNeighbor(l_rCountryId, l_rNeighbor);
					d_gameEngine.getGameMap().removeNeighbor(l_rNeighbor, l_rCountryId);
					l_i++;
					break;
				default:
					System.out.println("Invalid Input");
					break;
				}
			}
			break;

		case "commit":
			return "commit";

		default:
			System.out.println("Please Enter a Valid Command");
		}
		return "continue";
	}
}
