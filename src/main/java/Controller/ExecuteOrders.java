package Controller;

import Models.Player;
import Models.Orders.Order;

/**
 * Represents the execute orders phase of the game.
 */
public class ExecuteOrders extends GamePhase {
	
	/**
	 * Constructor method to initialize the phase
	 * 
	 * @param p_gameEngine object of game Engine
	 * @param p_commandHandler object of game Engine
	 */
	public ExecuteOrders(GameEngine p_gameEngine, CommandHandler p_commandHandler) {
		super(p_gameEngine, p_commandHandler);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * function that takes player's and that adds to them to the orders queue
	 * 
	 * @return string to output result of issue orders
	 */
	public String executeOrders() {
        super.d_gameEngine.getPhaseView().showNextPhaseInfo("execute");
    	for (Player l_player : super.d_gameEngine.getPlayers()) {
            while (true) {
                Order l_order = l_player.nextOrder();
                if (l_order == null) {
                    break;
                }
                l_order.execute(super.d_gameEngine);
                if (super.d_gameEngine.checkIfGameIsOver() == true) {
                    System.out.println("\nGAME OVER!");
                	return "gameOver";
                }

            }
        }
        System.out.println("[Execute orders phase end, moves to next round]");
        super.d_gameEngine.getPhaseView().showNextPhaseInfo("play");

		return "nextRound";
	}
}
