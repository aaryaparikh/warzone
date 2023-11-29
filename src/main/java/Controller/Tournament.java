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
			System.out.print("\n<Tournament Game " + (l_gameNumber + 1) + " Start>");

			for (GameMap l_gameMapEachTurn : d_listOfMapFiles) {
				d_gameEngine.setGameMap(l_gameMapEachTurn);
				System.out.println("\n<Game map is " + l_gameMapEachTurn.d_mapInfo + ">");
				d_gameEngine.setPlayers(DeepCopyList.deepCopy(l_playerListBuffer, d_gameEngine));

				d_gameEngine.assignCountriesRandomly();

				// Kick player if no country owned
				List<Player> l_playerList = new ArrayList<>();
				for (Player l_player : d_gameEngine.getPlayers())
					if (l_player.getD_countries().size() == 0)
						l_playerList.add(l_player);
				for (Player l_player : l_playerList)
					d_gameEngine.getPlayers().remove(l_player);

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
					l_gameResult = "-Draw-";
					d_gameResults.add(l_gameResult);
				} else {
					l_gameResult = d_gameEngine.getGameMap().getCountries().get(0).getOwner().getName();
					d_gameResults.add(l_gameResult);
				}
			}
		}

		// Output report
		System.out.println("\n\n<Tournament Report>");
		System.out.print("Maps: ");
		for (int l_mapIndex = 0; l_mapIndex < d_listOfMapFiles.size(); l_mapIndex++)
			if (l_mapIndex == d_listOfMapFiles.size() - 1)
				System.out.print(d_listOfMapFiles.get(l_mapIndex).d_mapInfo + "\n");
			else
				System.out.print(d_listOfMapFiles.get(l_mapIndex).d_mapInfo + ", ");

		System.out.print("Players: ");
		for (int l_playerIndex = 0; l_playerIndex < l_playerListBuffer.size(); l_playerIndex++)
			if (l_playerIndex == l_playerListBuffer.size() - 1)
				System.out.print(l_playerListBuffer.get(l_playerIndex).getName() + "\n");
			else
				System.out.print(l_playerListBuffer.get(l_playerIndex).getName() + ", ");

		System.out.println("Games: " + d_numberOfGames);
		System.out.println("Max Turns: " + d_maxNumberOfTurns);

		System.out.print("\n\t\t");
		for (int l_gameIndex = 0; l_gameIndex < d_numberOfGames; l_gameIndex++)
			System.out.print("| Game " + (l_gameIndex + 1) + "\t");
		System.out.println();

		for (int l_mapIndex = 0; l_mapIndex < d_listOfMapFiles.size(); l_mapIndex++) {
			System.out.print("| " + d_listOfMapFiles.get(l_mapIndex).d_mapInfo + "\t");

			for (int l_gameIndex = 0; l_gameIndex < d_numberOfGames; l_gameIndex++)
				System.out.print("| " + d_gameResults.get(l_gameIndex * d_listOfMapFiles.size() + l_mapIndex) + "\t");
			System.out.print("|\n");
		}
	}
}