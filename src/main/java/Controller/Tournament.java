package Controller;

import java.util.ArrayList;
import java.util.List;

import Controller.Phases.SubPhases.IssueOrderPhase;
import Controller.Phases.SubPhases.PlayMainPhase;
import Models.GameMap;
import Models.Player;
import Utils.DeepCopyList;
import Views.PhaseView;

/**
 * The Tournament class represents a series of games played using a GameEngine.
 * It manages the flow of multiple games, including setup, execution, and result
 * reporting.
 */
public class Tournament {
	/**
	 * Game Engine
	 */
	private GameEngine d_gameEngine;

	/**
	 * Shows the phases of game
	 */
	private PhaseView d_phaseView;

	/**
	 * Map list
	 */
	public List<GameMap> d_listOfMapFiles;

	/**
	 * Number of games
	 */
	public int d_numberOfGames;

	/**
	 * Maximum number of turns
	 */
	public int d_maxNumberOfTurns;

	/**
	 * game result list
	 */
	private List<String> d_gameResults;

	/**
	 * Constructor for the Tournament class.
	 *
	 * @param p_gameEngine     The GameEngine instance to be used for the
	 *                         tournament.
	 * @param p_phaseView      The PhaseView instance to display game phases.
	 * @param p_logEntryBuffer The LogEntryBuffer for logging game information.
	 * @param p_logWriter      The LogWriter for writing logs.
	 */
	public Tournament(GameEngine p_gameEngine, PhaseView p_phaseView, LogEntryBuffer p_logEntryBuffer,
			LogWriter p_logWriter) {
		this.d_gameEngine = p_gameEngine;
		this.d_phaseView = p_phaseView;
		this.d_listOfMapFiles = new ArrayList<>();
		this.d_gameResults = new ArrayList<>();
	}

	/**
	 * Start the tournament by playing multiple games and reporting the results.
	 */
	public void start() {
		if (d_numberOfGames <= 1) {
			System.out.println("No Game in this tournament, Game exit.");
			return;
		}

		List<Player> l_playerListBuffer = new ArrayList<>();
		l_playerListBuffer = DeepCopyList.deepCopy(d_gameEngine.getPlayers(), d_gameEngine);
		
		for (int l_gameNumber = 0; l_gameNumber < d_numberOfGames; l_gameNumber++) {
			System.out.println("\n<Tournament Game " + (l_gameNumber + 1) + " Start>");

			d_gameEngine.setGameMap(d_listOfMapFiles.get(l_gameNumber % d_listOfMapFiles.size()));
			d_gameEngine.setPlayers(DeepCopyList.deepCopy(l_playerListBuffer, d_gameEngine));
			
			d_gameEngine.assignCountriesRandomly();
			d_gameEngine.attachPlayersWithOrderWriter();
			d_gameEngine.executeCommand("showmap".split(" "), null);

			// Enter game play phase
			d_gameEngine.setPhase(new IssueOrderPhase(d_gameEngine));
			d_phaseView.showNextPhaseInfo("play");

			int l_gameTurn = 1;
			while ((d_gameEngine.getPhase() instanceof PlayMainPhase) && (l_gameTurn <= d_maxNumberOfTurns)) {
				d_gameEngine.assignReinforcements();

				d_gameEngine.updateGameMapForPlayers();

				d_gameEngine.issueOrdersInTurn();

				if (d_gameEngine.executeAllCommittedOrders() == "gameOver")
					break;

				l_gameTurn += 1;
			}

			String l_gameResult;
			if (l_gameTurn > d_maxNumberOfTurns) {
				l_gameResult = String.format("Game %d exceed max turn, not end.", l_gameNumber + 1);
				d_gameResults.add(l_gameResult);
			} else {
				l_gameResult = String.format("Game %d end, player \"%s\" is the winner.", l_gameNumber + 1,
						d_gameEngine.getGameMap().getCountries().get(0).getOwner().getName());
				d_gameResults.add(l_gameResult);
			}
		}

		// Enter end phase
		System.out.println("\n<Tournament Game End>");
		for (String l_result : d_gameResults)
			System.out.println(l_result);
	}
}