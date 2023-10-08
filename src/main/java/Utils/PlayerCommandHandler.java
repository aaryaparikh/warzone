package Utils;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.BombOrder;
import Models.Orders.DeployOrder;

/**
 * Handles player commands.
 * 
 * @author YURUI
 */
public class PlayerCommandHandler {
	private GameEngine d_gameEngine;

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public PlayerCommandHandler(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Handles player commands.
	 *
	 * @param p_command       The player's command.
	 * @param p_currentPlayer The current player.
	 * 
	 * @return handle command result
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
		case "showmap":
			d_gameEngine.getGameMap().getD_mapView().showGameMap();
			return "stayCurrentPlayer";

		// Handle deploying armies
		case "deploy":
			if (l_command.length < 3) {
				String l_response = String.format("Please enter enough parameter for deploy order");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			if (l_command.length > 3) {
				String l_response = String.format("Please enter less parameter for deploy order");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			boolean l_ifCountryInMap = false;
			for (Country l_country : d_gameEngine.getGameMap().getCountries()) {
				if (l_country.getCountryId() == Integer.parseInt(l_command[1])) {
					l_ifCountryInMap = true;

					// Check if the player controls the specified country.
					if (!p_currentPlayer.getD_countries().contains(l_country)) {
						String l_response = String.format("Player \"%s\" does not control country \"%d\"",
								p_currentPlayer.getName(), l_country.getCountryId());
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}

					// Check if the player has enough armies.
					if (p_currentPlayer.getD_reinforcementPool() < Integer.parseInt(l_command[2])) {
						String l_response = String.format("Player \"%s\" does not have enough armies",
								p_currentPlayer.getName());
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}

					DeployOrder deployOrder = new DeployOrder(p_currentPlayer, l_country,
							Integer.parseInt(l_command[2]));
					p_currentPlayer.addOrder(deployOrder);
					p_currentPlayer.decreaseReinforcementPool(Integer.parseInt(l_command[2]));
					break;
				}
			}

			if (l_ifCountryInMap == false) {
				String l_response = String.format("Please deploy to a valid country in the map");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}
			break;

		// Handle advancing armies
		case "advance":
			if (l_command.length < 4) {
				String l_response = String.format("Please enter enough parameter for advance order");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			if (l_command.length > 4) {
				String l_response = String.format("Please enter less parameter for advance order");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			boolean l_ifAdvanceSourceCountryInMap = false;
			boolean l_ifAdvanceTargetCountryInMap = false;
			for (Country l_sourceCountry : d_gameEngine.getGameMap().getCountries()) {
				if (l_sourceCountry.getCountryId() == Integer.parseInt(l_command[1])) {
					l_ifAdvanceSourceCountryInMap = true;

					// Check if the player controls the specified country.
					if (!p_currentPlayer.getD_countries().contains(l_sourceCountry)) {
						String l_response = String.format("Player \"%s\" does not control this country \"%d\"",
								p_currentPlayer.getName(), l_sourceCountry.getCountryId());
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}

					// Check if the target country exists in the map and is neighbor of source country.
					for (Country l_targetCountry : d_gameEngine.getGameMap().getCountries()) {
						if (l_targetCountry.getCountryId() == Integer.parseInt(l_command[2])) {
							l_ifAdvanceTargetCountryInMap = true;
							if (!l_sourceCountry.getNeighborCountries().contains(l_targetCountry.getCountryId())) {
								String l_response = String.format("Target country \"%d\" is not the neighbor of source country \"%d\"",
										l_targetCountry.getCountryId(), l_sourceCountry.getCountryId());
								System.out.println(l_response);
								return "stayCurrentPlayer";
							} else {								
								
								// Check if the player has enough armies.
								if (l_sourceCountry.getArmies() < Integer.parseInt(l_command[3])) {
									String l_response = String.format("Player \"%s\" does not have enough armies in source country.",
											p_currentPlayer.getName());
									System.out.println(l_response);
									return "stayCurrentPlayer";
								}
								
								// Issue a valid advance order
								AdvanceOrder advanceOrder = new AdvanceOrder(p_currentPlayer, l_sourceCountry, l_targetCountry,
										Integer.parseInt(l_command[3]));
								p_currentPlayer.addOrder(advanceOrder);
								break;
							}
						}
					}
					
					// Target country is not in map
					if (l_ifAdvanceTargetCountryInMap == false) {
						String l_response = String.format("Target country \"%d\" does not exist.",
								Integer.parseInt(l_command[2]));
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}
				}
			}
			
			// Source country is not in map
			if (l_ifAdvanceSourceCountryInMap == false) {
				String l_response = String.format("Source country \"%d\" does not exist.",
						Integer.parseInt(l_command[1]));
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}
			
			break;
		default:
			System.out.println("Please enter a valid command");
			return "stayCurrentPlayer";

		
		// Handle bomb
		case "bomb":
			if (l_command.length < 2) {
				String l_response = String.format("Please enter enough parameter for bomb card order.");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			if (l_command.length > 2) {
				String l_response = String.format("Please enter less parameter for bomb card order.");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			boolean l_ifBombCountryInMap = false;
			for (Country l_country : d_gameEngine.getGameMap().getCountries()) {
				if (l_country.getCountryId() == Integer.parseInt(l_command[1])) {
					l_ifBombCountryInMap = true;

					// Check if the player controls the specified country.
					if (p_currentPlayer.getD_countries().contains(l_country)) {
						String l_response = String.format("Player \"%s\" control this country \"%d\", can't bomb.",
								p_currentPlayer.getName(), l_country.getCountryId());
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}
					
					// Check if the target country is adjacent to player's countries.
					for (Country l_ownedCountry : p_currentPlayer.getD_countries())
						if (!l_ownedCountry.getNeighborCountries().contains(l_country.getCountryId())) {
							String l_response = String.format("Player \"%s\" is not adjacent to this country \"%d\", can't bomb.",
									p_currentPlayer.getName(), l_country.getCountryId());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

					// Check if the player has bomb card.
					if (p_currentPlayer.getCardsOwned().get(l_command[0]) == 0) {
						String l_response = String.format("Player \"%s\" does not have bomb card.",
								p_currentPlayer.getName());
						System.out.println(l_response);
						return "stayCurrentPlayer";
					}

					BombOrder bombOrder = new BombOrder(p_currentPlayer, l_country);
					p_currentPlayer.addOrder(bombOrder);
					p_currentPlayer.deleteCardsOwned(l_command[0]);
					break;
				}
			}

			if (l_ifBombCountryInMap == false) {
				String l_response = String.format("Please bomb to a valid country in the map");
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}
			break;
		}		
		return "nextPlayer";
	}

}
