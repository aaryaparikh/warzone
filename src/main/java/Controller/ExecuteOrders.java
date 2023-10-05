package Controller;

import java.util.List;

import Models.Player;
import Models.Orders.Order;
import Utils.MapCommandHandler;

/**
 * Represents the execute orders phase of the game.
 * 
 * @author YURUI
 */
public class ExecuteOrders extends GamePhase {

	/**
	 * Constructor method to initialize the phase
	 * 
	 * @param p_gameEngine     object of game Engine
	 * @param p_commandHandler object of game Engine
	 */
	public ExecuteOrders(GameEngine p_gameEngine, MapCommandHandler p_commandHandler) {
		super(p_gameEngine, p_commandHandler);
		// TODO Auto-generated constructor stub
	}

	/**
	 * function that takes player's and that adds to them to the orders queue
	 * 
	 * @return string to output result of issue orders
	 */
	public String executeOrders() {
		List<Player> l_playerPool = super.d_gameEngine.getPlayers();
		int l_remainedPlayers = l_playerPool.size();

		// execute every player's order in round-robin fashion
		while (l_remainedPlayers > 0) {
			for (Player l_player : l_playerPool) {
				Order l_order = l_player.nextOrder();
				if (l_order != null) {
					System.out.println(l_order.execute(super.d_gameEngine));
					
					// check whether the game is over
					if (super.d_gameEngine.checkIfGameIsOver() == true) {
						System.out.println("\n[GAME OVER!]");
						System.out.println("Player:" + l_player.getName() + "is the winner!");
						super.d_gameEngine.getPhaseView().showNextPhaseInfo("end");
						super.d_gameEngine.setPhase("end");
						return "gameOver";
					}
				} else
					l_remainedPlayers -= 1;
			}
		}

		// move to another play round
		System.out.println("[Execute orders phase end, moves to next round]");
		super.d_gameEngine.getPhaseView().showNextPhaseInfo("play");
		super.d_gameEngine.setPhase("play");

		return "nextRound";
	}
}
