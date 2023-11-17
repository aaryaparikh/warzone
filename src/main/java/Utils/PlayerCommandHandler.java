package Utils;

import Controller.GameEngine;
import Controller.Phases.EndGamePhase;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.BlockadeOrder;
import Models.Orders.BombOrder;
import Models.Orders.DeployOrder;
import Models.Orders.DiplomacyOrder;

/**
 * Handles player commands.
 * 
 * @author YURUI
 */
public class PlayerCommandHandler extends CommandHandler {

	/**
	 * Constructor for CommandHandler.
	 *
	 * @param p_gameEngine The game engine.
	 */
	public PlayerCommandHandler(GameEngine p_gameEngine) {
		super(p_gameEngine);
	};

	/**
	 * Handles player commands.
	 *
	 * @param p_commands      The player's command.
	 * @param p_currentPlayer The current player.
	 * 
	 * @return handle command result
	 */
	public String handlePlayerCommand(String[] p_commands, Player p_currentPlayer) {
		String[] l_command = p_commands;

		switch (l_command[0]) {
		case "end":
			d_gameEngine.setPhase(new EndGamePhase(d_gameEngine));
			System.out.println("Game is ended by Player \"" + p_currentPlayer.getName() + "\".");
			d_logEntryBuffer.setString("Game is ended by Player \"" + p_currentPlayer.getName() + "\".");
			return "gameEnd";

		case "showmap":
			d_gameEngine.getGameMap().getD_mapView().showGameMap();
			return "stayCurrentPlayer";

		case "savegame":
			if (p_commands.length < 2) {
				System.out.println("Please enter file path to save game.");
				return "stayCurrentPlayer";
			} else {
				for (Player l_player : d_gameEngine.getPlayers())
					while (l_player.recordNextOrder() != null)
						continue;
				GameEditor.saveGameToFile(d_gameEngine, "src/main/resources/" + p_commands[1],
						p_currentPlayer.getName());
				String l_response = String.format("Game is saved in \"%s\"", p_commands[1]);
				System.out.println(l_response);
				d_logEntryBuffer.setString(l_response);
				return "stayCurrentPlayer";
			}

		case "commit":
			if (p_currentPlayer.getD_reinforcementPool() == 0) {
				p_currentPlayer.setIfSignified(true);
				String l_response = String.format("Player \"%s\" has signified.", p_currentPlayer.getName());
				System.out.println(l_response);
				d_logEntryBuffer.setString(l_response);
				break;
			} else {
				String l_response = String.format("Player \"%s\" doesn't deploy all reinforcement, can't commit.",
						p_currentPlayer.getName());
				System.out.println(l_response);
				return "stayCurrentPlayer";
			}

			// Handle deploying armies
		case "deploy":
			try {
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

						DeployOrder l_deployOrder = new DeployOrder(p_currentPlayer, l_country,
								Integer.parseInt(l_command[2]));
						p_currentPlayer.addOrder(l_deployOrder);
						p_currentPlayer.decreaseReinforcementPool(Integer.parseInt(l_command[2]));
						String l_response = String.format(
								"Add an order of deploying \"%s\" armies on \"%s\" to Player \"%s\".", l_command[2],
								l_country.getCountryId(), p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);
						break;
					}
				}

				if (l_ifCountryInMap == false) {
					String l_response = String.format("Please deploy to a valid country in the map");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}
			} catch (Exception e) {
				System.out.println("Invalid command for deploy order.");
				return "stayCurrentPlayer";
			}
			break;

		// Handle advancing armies
		case "advance":
			try {
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

						// Check if the target country exists in the map and is neighbor of source
						// country.
						for (Country l_targetCountry : d_gameEngine.getGameMap().getCountries()) {
							if (l_targetCountry.getCountryId() == Integer.parseInt(l_command[2])) {
								l_ifAdvanceTargetCountryInMap = true;
								if (!l_sourceCountry.getNeighborCountries().contains(l_targetCountry.getCountryId())) {
									String l_response = String.format(
											"Target country \"%d\" is not the neighbor of source country \"%d\"",
											l_targetCountry.getCountryId(), l_sourceCountry.getCountryId());
									System.out.println(l_response);
									return "stayCurrentPlayer";
								} else {

									// Issue a valid advance order
									AdvanceOrder l_advanceOrder = new AdvanceOrder(p_currentPlayer, l_sourceCountry,
											l_targetCountry, Integer.parseInt(l_command[3]));
									p_currentPlayer.addOrder(l_advanceOrder);
									String l_response = String.format(
											"Add an order of advancing \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".",
											l_command[3], l_sourceCountry.getCountryId(),
											l_targetCountry.getCountryId(), p_currentPlayer.getName());
									d_logEntryBuffer.setString(l_response);
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
			} catch (Exception e) {
				System.out.println("Invalid command for advance order.");
				return "stayCurrentPlayer";
			}
			break;

		// Handle bomb
		case "bomb":
			try {
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

				// Check if the player has bomb card.
				if (p_currentPlayer.getCardsOwned().get(l_command[0]) == 0) {
					String l_response = String.format("Player \"%s\" does not have bomb card.",
							p_currentPlayer.getName());
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
						boolean ifTargetCountryAdjacent = false;
						for (Country l_ownedCountry : p_currentPlayer.getD_countries())
							if (l_ownedCountry.getNeighborCountries().contains(l_country.getCountryId()))
								ifTargetCountryAdjacent = true;

						if (ifTargetCountryAdjacent == false) {
							String l_response = String.format(
									"Player \"%s\" is not adjacent to this country \"%d\", can't bomb.",
									p_currentPlayer.getName(), l_country.getCountryId());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

						BombOrder l_bombOrder = new BombOrder(p_currentPlayer, l_country);
						p_currentPlayer.addOrder(l_bombOrder);
						String l_response = String.format("Add a card order to bomb \"%s\" for Player \"%s\".",
								l_country.getCountryId(), p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);

						p_currentPlayer.deleteCardsOwned(l_command[0]);
						l_response = String.format("Delete a bomb card for Player \"%s\".", p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);
						break;
					}
				}

				if (l_ifBombCountryInMap == false) {
					String l_response = String.format("Please bomb to a valid country in the map");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}
			} catch (Exception e) {
				System.out.println("Invalid command for bomb order.");
				return "stayCurrentPlayer";
			}
			break;

		// Handle blockade
		case "blockade":
			try {
				if (l_command.length < 2) {
					String l_response = String.format("Please enter enough parameter for blockade card order.");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				if (l_command.length > 2) {
					String l_response = String.format("Please enter less parameter for blockade card order.");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				// Check if the player has blockade card.
				if (p_currentPlayer.getCardsOwned().get(l_command[0]) == 0) {
					String l_response = String.format("Player \"%s\" does not have blockade card.",
							p_currentPlayer.getName());
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				boolean l_ifBlockadeCountryInMap = false;
				for (Country l_country : d_gameEngine.getGameMap().getCountries()) {
					if (l_country.getCountryId() == Integer.parseInt(l_command[1])) {
						l_ifBlockadeCountryInMap = true;

						// Check if the player controls the specified country.
						if (!p_currentPlayer.getD_countries().contains(l_country)) {
							String l_response = String.format(
									"Player \"%s\" doesn't control this country \"%d\", can't blockade.",
									p_currentPlayer.getName(), l_country.getCountryId());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

						BlockadeOrder l_blockadeOrder = new BlockadeOrder(p_currentPlayer, l_country);
						p_currentPlayer.addOrder(l_blockadeOrder);
						String l_response = String.format("Add a card order to blockade \"%s\" for Player \"%s\".",
								l_country.getCountryId(), p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);

						p_currentPlayer.deleteCardsOwned(l_command[0]);
						l_response = String.format("Delete a blockade card for Player \"%s\".",
								p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);
						break;
					}
				}

				if (l_ifBlockadeCountryInMap == false) {
					String l_response = String.format("Please blockade to a valid country in the map");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}
			} catch (Exception e) {
				System.out.println("Invalid command for blockade order.");
				return "stayCurrentPlayer";
			}
			break;

		// Handle to airlift armies
		case "airlift":
			try {
				if (l_command.length < 4) {
					String l_response = String.format("Please enter enough parameter for airlift order");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				if (l_command.length > 4) {
					String l_response = String.format("Please enter less parameter for airlift order");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				// Check if the player has airlift card.
				if (p_currentPlayer.getCardsOwned().get(l_command[0]) == 0) {
					String l_response = String.format("Player \"%s\" does not have airlift card.",
							p_currentPlayer.getName());
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				boolean l_ifAirliftSourceCountryInMap = false;
				boolean l_ifAirliftTargetCountryInMap = false;
				for (Country l_sourceCountry : d_gameEngine.getGameMap().getCountries()) {
					if (l_sourceCountry.getCountryId() == Integer.parseInt(l_command[1])) {
						l_ifAirliftSourceCountryInMap = true;

						// Check if the player controls the specified country.
						if (!p_currentPlayer.getD_countries().contains(l_sourceCountry)) {
							String l_response = String.format("Player \"%s\" does not control resource country \"%d\"",
									p_currentPlayer.getName(), l_sourceCountry.getCountryId());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

						// Check if the target country exists in the map and is controlled by player.
						for (Country l_targetCountry : d_gameEngine.getGameMap().getCountries()) {
							if (l_targetCountry.getCountryId() == Integer.parseInt(l_command[2])) {
								l_ifAirliftTargetCountryInMap = true;

								if (!p_currentPlayer.getD_countries().contains(l_targetCountry)) {
									String l_response = String.format(
											"Player \"%s\" does not control target country \"%d\"",
											p_currentPlayer.getName(), l_targetCountry.getCountryId());
									System.out.println(l_response);
									return "stayCurrentPlayer";
								} else {

									// Issue a valid airlift card order
									AdvanceOrder l_advanceOrder = new AdvanceOrder(p_currentPlayer, l_sourceCountry,
											l_targetCountry, Integer.parseInt(l_command[3]));
									p_currentPlayer.addOrder(l_advanceOrder);
									String l_response = String.format(
											"Add a card order to airlift \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".",
											l_command[3], l_sourceCountry.getCountryId(),
											l_targetCountry.getCountryId(), p_currentPlayer.getName());
									d_logEntryBuffer.setString(l_response);

									p_currentPlayer.deleteCardsOwned(l_command[0]);
									l_response = String.format("Delete an airlift card for Player \"%s\".",
											p_currentPlayer.getName());
									d_logEntryBuffer.setString(l_response);

									break;
								}
							}
						}

						// Target country is not in map
						if (l_ifAirliftTargetCountryInMap == false) {
							String l_response = String.format("Target country \"%d\" does not exist.",
									Integer.parseInt(l_command[2]));
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}
					}
				}

				// Source country is not in map
				if (l_ifAirliftSourceCountryInMap == false) {
					String l_response = String.format("Source country \"%d\" does not exist.",
							Integer.parseInt(l_command[1]));
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}
			} catch (Exception e) {
				System.out.println("Invalid command for airlift order.");
				return "stayCurrentPlayer";
			}
			break;

		// Handle negotiating
		case "negotiate":
			try {
				if (l_command.length < 2) {
					String l_response = String.format("Please enter enough parameter for negotiate card order.");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				if (l_command.length > 2) {
					String l_response = String.format("Please enter less parameter for negotiate card order.");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				// Check if the player has negotiate card.
				if (p_currentPlayer.getCardsOwned().get(l_command[0]) == 0) {
					String l_response = String.format("Player \"%s\" does not have negotiate card.",
							p_currentPlayer.getName());
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}

				boolean l_ifNegotiatePlayerExist = false;
				for (Player l_player : d_gameEngine.getPlayers()) {
					if (l_player.getName().equals(l_command[1])) {
						l_ifNegotiatePlayerExist = true;

						// Check if the player has already negotiate with target player.
						if (p_currentPlayer.getNegotiatedPlayers().contains(l_player)) {
							String l_response = String.format(
									"Player \"%s\" has negotiated with player \"%s\", can't do it again.",
									p_currentPlayer.getName(), l_player.getName());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

						// Check if the player negotiate with himself.
						if (p_currentPlayer.getName() == l_player.getName()) {
							String l_response = String.format("Player \"%s\" can't negotiate with himself.",
									p_currentPlayer.getName(), l_player.getName());
							System.out.println(l_response);
							return "stayCurrentPlayer";
						}

						DiplomacyOrder l_negotiateOrder = new DiplomacyOrder(p_currentPlayer, l_player);
						p_currentPlayer.addOrder(l_negotiateOrder);
						String l_response = String.format(
								"Add a card order of negotiating with \"%s\" for Player \"%s\".", l_player.getName(),
								p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);

						p_currentPlayer.deleteCardsOwned(l_command[0]);
						l_response = String.format("Delete a negotiate card for Player \"%s\".",
								p_currentPlayer.getName());
						d_logEntryBuffer.setString(l_response);
						break;
					}
				}

				if (l_ifNegotiatePlayerExist == false) {
					String l_response = String.format("Please negotiate to a valid player in game.");
					System.out.println(l_response);
					return "stayCurrentPlayer";
				}
			} catch (Exception e) {
				System.out.println("Invalid command for diplomacy order.");
				return "stayCurrentPlayer";
			}
			break;

		default:
			System.out.println("Please enter a valid command");
			return "stayCurrentPlayer";

		// end switch command
		}
		return "nextPlayer";
	}

}
