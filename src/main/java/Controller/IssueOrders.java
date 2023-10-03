package Controller;

import java.util.List;
import Models.Player;

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
    public IssueOrders(GameEngine p_gameEngine, CommandHandler p_commandHandler) {
        super(p_gameEngine, p_commandHandler);
    }

    /**
     * Function that takes player's input and adds their orders to the queue.
     */
    public void issueOrders() {
    	List<Player> l_playerPool = super.d_gameEngine.getPlayers();
    	
    	while(l_playerPool.size() != 0) {
    		int l_remainedReinforcement = 0;
    		for (Player l_player : l_playerPool) {
	        	if (l_player.getD_reinforcementPool() != 0) {
	                System.out.println("[Player " + l_player.getD_name() + "'s turn to deploy]");
	                l_player.issueOrder();
	        	}
	        	l_remainedReinforcement += l_player.getD_reinforcementPool();
	        }
    		if (l_remainedReinforcement == 0)
    			break;
    	}
        
        System.out.println("[All players have deployed all reinforcement]");
    }
}

