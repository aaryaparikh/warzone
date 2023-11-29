package Utils;

import java.util.ArrayList;

import Controller.GameEngine;
import Models.GameMap;
import Models.Player;
import Models.Strategy.AggressivePlayerStrategy;
import Models.Strategy.BenevolentPlayerStrategy;
import Models.Strategy.CheaterPlayerStrategy;
import Models.Strategy.RandomPlayerStrategy;

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
				System.out.println("Please enter valid map file name to load.");
			else {
				if (p_commands[1].split("\\.")[1].equals("map")) {
					MapEditorAdapter l_mapEditor = new MapEditorAdapter(d_gameEngine.getGameMap());
					GameMap l_map = l_mapEditor.loadMap(l_commands[1]);

					if (!d_gameEngine.getGameMap().equals(l_map)) {
						System.out.println("\"" + l_commands[1] + "\" is loaded as the game map.");
						d_logEntryBuffer.setString("\"" + l_commands[1] + "\" is loaded as the game map.");
						d_gameEngine.setGameMap(l_map);
					}
				} else {
					MapEditor l_mapEditor = new MapEditor(d_gameEngine.getGameMap());
					GameMap l_map = l_mapEditor.loadMap(l_commands[1]);

					if (!d_gameEngine.getGameMap().equals(l_map)) {
						System.out.println("\"" + l_commands[1] + "\" is loaded as the game map.");
						d_logEntryBuffer.setString("\"" + l_commands[1] + "\" is loaded as the game map.");
						d_gameEngine.setGameMap(l_map);
					}
				}
			}
			break;

		case "savegame":
			if (p_commands.length < 2)
				System.out.println("Please enter file path to save game.");
			else {
				GameEditor.saveGameToFile(d_gameEngine, "src/main/resources/Games/" + p_commands[1], null);
				String l_response2 = String.format("Game is saved in \"%s\"", p_commands[1]);
				System.out.println(l_response2);
				d_logEntryBuffer.setString(l_response2);
			}

		case "loadgame":
			if (p_commands.length < 2)
				System.out.println("Please enter file path to load game.");
			else {
				String l_response2 = String.format("Game is loaded from \"%s\"", p_commands[1]);
				System.out.println(l_response2);
				d_logEntryBuffer.setString(l_response2);
				GameEditor.loadGameFromFile("src/main/resources/Games/" + p_commands[1]);
				System.exit(0);
			}

		case "tournament":
			if (l_commands.length < 9) {
				System.out.println(
						"Please enter tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
				break;
			}

			int l_index = 1;

			d_gameEngine.d_tournament.d_listOfMapFiles.clear();

			// set game maps for tournament
			for (; l_index < l_commands.length; l_index += 1) {
				if (l_commands[l_index].equals("-M"))
					continue;
				if (l_commands[l_index].equals("-P"))
					break;

				GameMap l_map = null;
				if (l_commands[l_index].split("\\.")[1].equals("map")) {
					MapEditorAdapter l_mapEditor = new MapEditorAdapter(d_gameEngine.getGameMap());
					l_map = l_mapEditor.loadMap(l_commands[l_index]);
				} else {
					MapEditor l_mapEditor = new MapEditor(d_gameEngine.getGameMap());
					l_map = l_mapEditor.loadMap(l_commands[l_index]);
				}
				if (l_map != null) {
					l_map.d_mapInfo = l_commands[l_index];
					d_gameEngine.d_tournament.d_listOfMapFiles.add(l_map);
				}
			}

			if ((d_gameEngine.d_tournament.d_listOfMapFiles.size() > 5)
					|| (d_gameEngine.d_tournament.d_listOfMapFiles.size() < 1)) {
				System.out.println("Please enter tournament -M between 1 to 5 maps");
				break;
			}

			d_gameEngine.setPlayers(new ArrayList<>());

			// set player strategy for tournament
			for (; l_index < l_commands.length; l_index += 1) {
				if (l_commands[l_index].equals("-P"))
					continue;
				if (l_commands[l_index].equals("-G"))
					break;

				Player l_player = null;

				switch (l_commands[l_index]) {
				case "":
					break;
				case "human":
					break;
				case "aggressive":
					l_player = new Player("Aggressive", d_gameEngine);
					d_gameEngine.addPlayer(l_player);
					l_player.setD_strategy(new AggressivePlayerStrategy(l_player,
							DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()), d_logEntryBuffer));
					break;
				case "benevolent":
					l_player = new Player("Benevolent", d_gameEngine);
					d_gameEngine.addPlayer(l_player);
					l_player.setD_strategy(new BenevolentPlayerStrategy(l_player,
							DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()), d_logEntryBuffer));
					break;
				case "random":
					l_player = new Player("Random", d_gameEngine);
					d_gameEngine.addPlayer(l_player);
					l_player.setD_strategy(new RandomPlayerStrategy(l_player,
							DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()), d_logEntryBuffer));
					break;
				case "cheater":
					l_player = new Player("Cheater", d_gameEngine);
					d_gameEngine.addPlayer(l_player);
					l_player.setD_strategy(new CheaterPlayerStrategy(l_player,
							DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()), d_logEntryBuffer));
					break;
				}
			}

			if ((d_gameEngine.getPlayers().size() > 4) || (d_gameEngine.getPlayers().size() < 2)) {
				System.out.println("Please enter tournament -P between 2 to 4 players");
				break;
			}

			if (l_index + 1 < l_commands.length) {
				if ((Integer.parseInt(l_commands[l_index + 1]) > 5)
						|| (Integer.parseInt(l_commands[l_index + 1]) < 1)) {
					System.out.println("Please enter tournament -G between 1 to 5");
					break;
				} else
					d_gameEngine.d_tournament.d_numberOfGames = Integer.parseInt(l_commands[l_index + 1]);
			}

			if (l_index + 3 < l_commands.length) {
				if ((Integer.parseInt(l_commands[l_index + 3]) > 50)
						|| (Integer.parseInt(l_commands[l_index + 3]) < 10)) {
					System.out.println("Please enter tournament -D between 10 to 50");
					break;
				} else
					d_gameEngine.d_tournament.d_maxNumberOfTurns = Integer.parseInt(l_commands[l_index + 3]);
			}

			for (Player l_player : d_gameEngine.getPlayers())
				if (l_player.getD_strategy() == null)
					l_player.setD_strategy(new CheaterPlayerStrategy(l_player,
							DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()), d_logEntryBuffer));

			return "tournament";

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
							Boolean l_ifNeedValidStrategy = true;
							String l_userStrategy = "";

							while (l_ifNeedValidStrategy == true) {
								l_ifNeedValidStrategy = false;
								System.out.println(
										">> Please Enter Valid Player Strategy (\"Enter\"/human, aggressive, benevolent, random, cheater)");
								l_userStrategy = d_gameEngine.d_sc.nextLine();

								switch (l_userStrategy) {
								case "":
									break;
								case "human":
									break;
								case "aggressive":
									l_player.setD_strategy(new AggressivePlayerStrategy(l_player,
											DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
											d_logEntryBuffer));
									break;
								case "benevolent":
									l_player.setD_strategy(new BenevolentPlayerStrategy(l_player,
											DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
											d_logEntryBuffer));
									break;
								case "random":
									l_player.setD_strategy(new RandomPlayerStrategy(l_player,
											DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
											d_logEntryBuffer));
									break;
								case "cheater":
									l_player.setD_strategy(new CheaterPlayerStrategy(l_player,
											DeepCopyList.deepCopy(d_gameEngine.getGameMap().getCountries()),
											d_logEntryBuffer));
									break;
								default:
									l_ifNeedValidStrategy = true;
								}
							}

							l_response = String
									.format(l_userStrategy + " player \"" + l_commands[l_i + 1] + "\" is added.");
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
