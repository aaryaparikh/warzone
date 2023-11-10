package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Constants.GameConstants;
import Controller.Phases.EditMapPhase;
import Controller.Phases.EndGamePhase;
import Controller.Phases.Phase;
import Controller.Phases.SubPhases.ExecuteOrderPhase;
import Controller.Phases.SubPhases.IssueOrderPhase;
import Controller.Phases.SubPhases.PlayMainPhase;
import Controller.Phases.SubPhases.PlaySetupPhase;
import Models.Continent;
import Models.Country;
import Models.GameMap;
import Models.Player;
import Models.Orders.Order;
import Views.PhaseView;

/**
 * Represents the game engine core for the strategy game.
 *
 * @author YURUI
 */
public class GameEngine {
	/**
	 * List of players in the game
	 */
	private List<Player> d_players;

	/**
	 * Map of the game
	 */
	private GameMap d_map;

	/**
	 * Shows the phases of game
	 */
	private PhaseView d_phaseView;

	/**
	 * List of conquering player
	 */
	private List<Player> d_playerConquerInTurn;

	/**
	 * Maintain the game phase
	 */
	private Phase d_gamePhase;

	/**
	 * Log Maintainer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * Game Log Writer
	 */

	@SuppressWarnings("unused")
	private LogWriter d_logWriter;

	/**
	 * Unique scanner in a game
	 */
	public Scanner d_sc;

	/**
	 * Constructor for GameEngine.
	 *
	 * @param p_map The game map.
	 */
	public GameEngine(GameMap p_map) {
		this.d_map = p_map;
		this.d_players = new ArrayList<>();
		this.setPhaseView(new PhaseView(this));
		this.d_playerConquerInTurn = new ArrayList<>();
		this.d_logEntryBuffer = new LogEntryBuffer();
		this.d_logWriter = new LogWriter(d_logEntryBuffer);
		this.d_sc = new Scanner(System.in);
	}

	/**
	 * Function that defines the main flow of a game
	 */
	public void start() {
		setPhase(new EditMapPhase(this));

		try (Scanner l_sc = d_sc) {
			// Enter edit map phase
			getPhaseView().showNextPhaseInfo("edit");
			while (d_gamePhase instanceof EditMapPhase) {
				String l_userInput;
				l_userInput = l_sc.nextLine();
				String l_commands[] = l_userInput.split(" ");
				executeCommand(l_commands, null);
			}

			// Enter game setup phase
			getPhaseView().showNextPhaseInfo("start");
			while (d_gamePhase instanceof PlaySetupPhase) {
				String l_userInput;
				l_userInput = l_sc.nextLine();
				String l_commands[] = l_userInput.split(" ");
				executeCommand(l_commands, null);
			}

			// Enter game play phase
			getPhaseView().showNextPhaseInfo("play");
			while (d_gamePhase instanceof PlayMainPhase) {
				assignReinforcements();

				if ((issueOrdersInTurn() == "gameEnd") || (executeAllCommittedOrders() == "gameOver"))
					break;
			}

			// Enter end phase
			getPhaseView().showNextPhaseInfo("end");
			while (d_gamePhase instanceof EndGamePhase) {
				String l_userInput;
				l_userInput = l_sc.nextLine();
				String l_commands[] = l_userInput.split(" ");
				executeCommand(l_commands, null);
			}
		}
	}

	/**
	 * Executes a game command based on the provided input commands and current
	 * player.
	 *
	 * @param p_commands      An array of strings representing the command and its
	 *                        parameters.
	 * @param p_currentPlayer The current player who is executing the command.
	 * @return A string response generated by the executed command.
	 */
	public String executeCommand(String[] p_commands, Player p_currentPlayer) {
		String l_response = null;
		switch (p_commands[0]) {
		// edit map command
		case "showmap":
			l_response = this.d_gamePhase.showMap(p_commands);
			break;
		case "savemap":
			l_response = this.d_gamePhase.saveMap(p_commands);
			break;
		case "validatemap":
			l_response = this.d_gamePhase.validateMap(p_commands);
			break;
		case "editmap":
			l_response = this.d_gamePhase.editMap(p_commands);
			break;
		case "editcontinent":
			l_response = this.d_gamePhase.editContinent(p_commands);
			break;
		case "editcountry":
			l_response = this.d_gamePhase.editCountry(p_commands);
			break;
		case "editneighbor":
			l_response = this.d_gamePhase.editNeighbor(p_commands);
			break;

		// start up game command
		case "loadmap":
			l_response = this.d_gamePhase.loadMap(p_commands);
			break;
		case "gameplayer":
			l_response = this.d_gamePhase.gamePlayer(p_commands);
			break;
		case "assigncountries":
			l_response = this.d_gamePhase.assignCountries(p_commands);
			break;

		// play game command
		case "deploy":
			l_response = this.d_gamePhase.deploy(p_commands, p_currentPlayer);
			break;
		case "advance":
			l_response = this.d_gamePhase.advance(p_commands, p_currentPlayer);
			break;
		case "bomb":
			l_response = this.d_gamePhase.bomb(p_commands, p_currentPlayer);
			break;
		case "blockade":
			l_response = this.d_gamePhase.blockade(p_commands, p_currentPlayer);
			break;
		case "airlift":
			l_response = this.d_gamePhase.airlift(p_commands, p_currentPlayer);
			break;
		case "negotiate":
			l_response = this.d_gamePhase.negotiate(p_commands, p_currentPlayer);
			break;
		case "commit":
			l_response = this.d_gamePhase.commit(p_commands, p_currentPlayer);
			break;

		// general command
		case "end":
			l_response = this.d_gamePhase.end(p_commands, p_currentPlayer);
			break;
		case "next":
			this.d_gamePhase.next(p_commands);
			break;
		default:
			System.out.println("Invalid command.");
		}
		return l_response;
	}

	/**
	 * Function that takes player's input and adds their orders to the queue.
	 *
	 * @return string to output result of issuing orders
	 */
	public String issueOrdersInTurn() {
		List<Player> l_playerPool = getPlayers();
		for (Player l_player : l_playerPool)
			l_player.setIfSignified(false);

		boolean l_ifRemainPlayers = true;

		// issue order in round-robin fashion
		while (l_ifRemainPlayers) {
			l_ifRemainPlayers = false;
			for (Player l_player : l_playerPool)
				if (!l_player.getIfSignified()) {
					System.out.println("[Player " + l_player.getName() + "'s turn][" + l_player.getD_reinforcementPool()
							+ " armies need to deploy]");

					l_player.issueOrder();

					if (this.getPhase() instanceof EndGamePhase)
						return "gameEnd";
					l_ifRemainPlayers = true;
				}
		}

		System.out.println("[All players have signified]");
		getPhaseView().showNextPhaseInfo("execute");
		setPhase(new ExecuteOrderPhase(this));
		return null;
	}

	/**
	 * function that execute orders from Player's order list
	 *
	 * @return string to output result of executing orders
	 */
	public String executeAllCommittedOrders() {
		List<Player> l_playerPool = getPlayers();
		boolean l_ifRemainPlayers = true;

		// execute every player's deploy order in round-robin fashion
		while (l_ifRemainPlayers) {
			l_ifRemainPlayers = false;
			for (Player l_player : l_playerPool) {
				Order l_order = l_player.nextOrder();
				if (l_order != null) {
					if (l_order.getOrderType() != "Deploy")
						l_player.addOrderAtFirstPosition(l_order);
					else {
						String l_response = l_order.execute(this);
						System.out.println(l_response);
						d_logEntryBuffer.setString(l_response);
						l_ifRemainPlayers = true;
					}
				}
			}
		}

		l_ifRemainPlayers = true;

		// execute every player's other order in round-robin fashion
		while (l_ifRemainPlayers) {
			l_ifRemainPlayers = false;
			for (Player l_player : l_playerPool) {
				Order l_order = l_player.nextOrder();
				if (l_order != null) {
					l_ifRemainPlayers = true;
					String l_response = l_order.execute(this);
					System.out.println(l_response);
					d_logEntryBuffer.setString(l_response);

					// check whether the game is over
					if (checkIfGameIsOver()) {
						System.out.println("\n[GAME OVER!]");
						System.out.println("Player:" + l_player.getName() + " is the winner!");
						setPhase(new EndGamePhase(this));
						d_logEntryBuffer.setString("Player:" + l_player.getName() + " is the winner!");
						return "gameOver";
					}
				}
			}
		}

		// Allocate cards to players
		for (Player l_player : getPlayerConquerInTurn())
			l_player.addCardsOwned("random");
		resetPlayerConquerInTurn();

		// Reset negotiate relationship
		for (Player l_player : getPlayerConquerInTurn())
			l_player.resetNegotiatedPlayers();

		// move to another play round
		System.out.println("[Execute orders phase end, moves to next round]");
		getPhaseView().showNextPhaseInfo("play");
		setPhase(new IssueOrderPhase(this));

		return "nextRound";
	}

	/**
	 * Get the list of players.
	 *
	 * @return The list of players.
	 */
	public List<Player> getPlayers() {
		return this.d_players;
	}

	/**
	 * Get the game map.
	 *
	 * @return The game map.
	 */
	public GameMap getGameMap() {
		return this.d_map;
	}

	/**
	 * Set the game map.
	 *
	 * @param p_map The game map.
	 */
	public void setGameMap(GameMap p_map) {
		this.d_map = p_map;
	}

	/**
	 * Set the game phase.
	 *
	 * @param p_phase The game phase.
	 */
	public void setPhase(Phase p_phase) {
		this.d_gamePhase = p_phase;
	}

	/**
	 * Get the game phase.
	 *
	 * @return The game phase.
	 */
	public Phase getPhase() {
		return d_gamePhase;
	}

	/**
	 * Add a player to the game.
	 *
	 * @param p_player The player to add.
	 *
	 * @return whether it successes to add a player
	 */
	public boolean addPlayer(Player p_player) {
		if (checkPlayerInList(p_player))
			return false;
		else {
			d_players.add(p_player);
			return true;
		}
	}

	/**
	 * Remove a player to the game.
	 *
	 * @param p_player The player to remove.
	 *
	 * @return whether it successes to remove a player
	 */
	public boolean removePlayer(Player p_player) {
		if (!checkPlayerInList(p_player))
			return false;
		else {
			for (Player l_player : d_players)
				if (l_player.getName().equals(p_player.getName())) {

					// remove country owner
					for (Country l_country : d_map.getCountries())
						if (l_country.getOwner().equals(l_player))
							l_country.setOwner(null);
					d_players.remove(l_player);
					break;
				}
			return true;
		}
	}

	/**
	 * Check whether a player in current list.
	 *
	 * @param p_player The player to check.
	 *
	 * @return whether the player is in the list
	 */
	public boolean checkPlayerInList(Player p_player) {
		for (Player l_player : d_players)
			if (l_player.getName().equals(p_player.getName()))
				return true;
		return false;
	}

	/**
	 * Assign countries to players randomly.
	 */
	public void assignCountriesRandomly() {
		List<Country> l_unassignedCountries = new ArrayList<>(d_map.getCountries());

		Collections.shuffle(l_unassignedCountries);

		// reset owner of countries before assign
		for (Player l_player : d_players) {
			l_player.resetCountry();
		}

		// assign country
		for (int l_i = 0; l_i < l_unassignedCountries.size(); l_i++) {
			Player l_player = d_players.get(l_i % d_players.size());
			Country l_country = l_unassignedCountries.get(l_i);
			l_player.addCountry(l_country);
			l_country.setOwner(l_player);
		}
	}

	/**
	 * Assign reinforcements to players.
	 */
	public void assignReinforcements() {
		for (Player l_player : d_players) {
			List<Country> l_ownedCountries = l_player.getD_countries();
			int l_reinforcementArmies = calculateReinforcementArmies(l_player, l_ownedCountries);
			l_player.assignReinforcements(l_reinforcementArmies);
		}
	}

	/**
	 * Calculate reinforcement armies for a player.
	 *
	 * @param p_player         The player for whom to calculate reinforcements.
	 * @param p_ownedCountries The list of countries owned by the player.
	 * @return The number of reinforcement armies.
	 */
	private int calculateReinforcementArmies(Player p_player, List<Country> p_ownedCountries) {
		// Implement reinforcement calculation logic based on game rules
		int l_assignedArmies = p_ownedCountries.size() / 3;

		for (Continent l_continent : d_map.getContinents()) {
			List<Country> l_countryList = new ArrayList<>();
			for (Country l_country : d_map.getCountries()) {
				if (l_country.getContinentId() == l_continent.getContinentId())
					l_countryList.add(l_country);
			}
			if (p_ownedCountries.containsAll(l_countryList))
				l_assignedArmies += l_continent.getContinentValue();
		}

		if (l_assignedArmies > GameConstants.MINIMUN_PLAYER_REINFORCEMENT)
			return l_assignedArmies;
		else
			return GameConstants.MINIMUN_PLAYER_REINFORCEMENT;
	}

	/**
	 * Check if the game is over.
	 *
	 * @return True if the game is over, otherwise false.
	 */
	public Boolean checkIfGameIsOver() {
		for (Player l_player : d_players) {
			if (l_player.getD_countries().size() == d_map.getCountries().size()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the phase view.
	 *
	 * @return PhaseView.
	 */
	public PhaseView getPhaseView() {
		return d_phaseView;
	}

	/**
	 * Set the phase view.
	 *
	 * @param p_phaseView the phase needs to view
	 */
	public void setPhaseView(PhaseView p_phaseView) {
		this.d_phaseView = p_phaseView;
	}

	/**
	 * Get the list of players who conquer at least one territory in this turn
	 *
	 * @return the d_playerConquerInTurn
	 */
	public List<Player> getPlayerConquerInTurn() {
		return d_playerConquerInTurn;
	}

	/**
	 * Set the list of players who conquer at least one territory in this turn
	 *
	 * @param p_player the d_playerConquerInTurn to set
	 */
	public void setPlayerConquerInTurn(Player p_player) {
		this.d_playerConquerInTurn.add(p_player);
	}

	/**
	 * Reset the list of players who conquer at least one territory in this turn
	 */
	public void resetPlayerConquerInTurn() {
		this.d_playerConquerInTurn.clear();

	}

	/**
	 * Get the log entry buffer.
	 *
	 * @return log entry buffer.
	 */
	public LogEntryBuffer getD_logEntryBuffer() {
		return d_logEntryBuffer;
	}

	/**
	 * Set the log entry buffer.
	 *
	 * @param p_logEntryBuffer the log entry buffer
	 */
	public void setD_logEntryBuffer(LogEntryBuffer p_logEntryBuffer) {
		this.d_logEntryBuffer = p_logEntryBuffer;
	}
}
