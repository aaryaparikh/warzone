package Controller;

import java.util.List;
import Models.Player;
import Utils.MapCommandHandler;

/**
 * Represents the issue orders phase of the game.
 */
public class IssueOrders extends GamePhase {
    
    /**
     * Constructor method to initialize the issue orders phase.
     *
     * @param p_gameEngine     The game engine.
     * @param p_commandHandler The command handler.
     */
    public IssueOrders(GameEngine p_gameEngine, MapCommandHandler p_commandHandler) {
        super(p_gameEngine, p_commandHandler);
    }

    /**
     * Function that takes player's input and adds their orders to the queue.
     */
    public void issueOrders() {
    	List<Player> l_playerPool = super.d_gameEngine.getPlayers();
		int l_remainedPlayers = l_playerPool.size();

    	while(l_remainedPlayers > 0) {
    		for (Player l_player : l_playerPool) {
	        	if (l_player.getD_reinforcementPool() != 0) {
	                System.out.println("[Player " + l_player.getName() + "'s turn][" + l_player.getD_reinforcementPool() +" armies need to deploy]");
	                l_player.issueOrder();
	        	} else
                	l_remainedPlayers -= 1;
	        }
    	}
        
        System.out.println("[All players have deployed all reinforcement]");
        super.d_gameEngine.getPhaseView().showNextPhaseInfo("execute");
        super.d_gameEngine.setphase("execute");
    }
}

