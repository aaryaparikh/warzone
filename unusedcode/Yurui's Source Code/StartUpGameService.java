package Services;

import java.util.Scanner;
import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * StartupService for all command in startup phase
 * 
 * @author Jonathan
 */
public class StartUpGameService {
	private GameEngine d_gameEngine;

	/**
	 * Constructor for Startup Service
	 *
	 * @param p_gameEngine the game engine object
	 */
	public StartUpGameService(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Main function for startup service
	 *
	 * @param args main function parameters
	 */
	public void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner l_scanner = new Scanner(System.in);

		// Decide whether add a default player "You"
		if (d_gameEngine.getPlayers().size() == 0) {
			Player l_defaultPlayer = new Player("You", d_gameEngine);
			d_gameEngine.addPlayer(l_defaultPlayer);
		}

		// Initialize Game map
		for (Country l_country : d_gameEngine.getGameMap().getCountries())
			l_country.setOwner(null);

		String l_userInput;
		while (true) {

			l_userInput = l_scanner.nextLine();
			String l_commands[] = l_userInput.split(" ");

			// handle command
			switch (l_commands[0]) {
			case "end":
				boolean ifAllPlayerAssigned = true;
				for (Player l_player : d_gameEngine.getPlayers())
					if (l_player.getD_countries().size() == 0) {
						System.out.println("Please assign all players before play.");
						ifAllPlayerAssigned = false;
						break;
					}

				boolean ifAllCountryAssigned = true;
				for (Country l_country : d_gameEngine.getGameMap().getCountries())
					if (l_country.getOwner() == null) {
						System.out.println("Please assign all countries before play.");
						ifAllCountryAssigned = false;
						break;
					}

				if (ifAllPlayerAssigned && ifAllCountryAssigned) {
					d_gameEngine.setPhase("play");
					d_gameEngine.getPhaseView().showNextPhaseInfo("play");
					return;
				}
				break;

			// move back to map editor
			case "backtoedit":
				d_gameEngine.setPhase("edit");
				d_gameEngine.getPhaseView().showNextPhaseInfo("edit");
				return;
			case "showmap":
				d_gameEngine.getGameMap().getD_mapView().showGameMap();
				break;
			case "loadmap":
				if (l_commands.length < 2)
					System.out.println("Please enter valid map file path");
				else
					d_gameEngine.setGameMap(d_gameEngine.getGameMap().d_mapEditor.loadMap(l_commands[1]));
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

							if (d_gameEngine.addPlayer(l_player))
								System.out.println("Player " + l_commands[l_i + 1] + " is added.");
							else
								System.out
										.println("Player " + l_commands[l_i + 1] + " already exists. Can't add again.");
							break;
						case "-remove":
							if (d_gameEngine.removePlayer(l_player))
								System.out.println("Player " + l_commands[l_i + 1] + " is removed.");
							else
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
				else if (d_gameEngine.getPlayers().size() >= d_gameEngine.getGameMap().getCountries().size())
					System.out.println("Can't assign countries because too many players");
				else {
					d_gameEngine.assignCountriesRandomly();
					System.out.println("All countries are assigned to players");
				}
				break;
			default:
				System.out.println("Invalid Input");
			}
		}

	}
}
