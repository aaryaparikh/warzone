package Utils;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 * Handles player commands.
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
     */
    public String handlePlayerCommand(String p_command, Player p_currentPlayer) {
        String[] l_command = p_command.split(" ");
        switch (l_command[0]) {
        	case "end":
        		switch (d_gameEngine.getPhase()) {
        			case "edit":
                		d_gameEngine.setphase("start");
                        d_gameEngine.getPhaseView().showNextPhaseInfo("start");
                        break;
        			case "start":
                		d_gameEngine.setphase("play");
                		d_gameEngine.getPhaseView().showNextPhaseInfo("play");
                		break;
        			case "play":
        				d_gameEngine.setphase("end");
                		d_gameEngine.getPhaseView().showNextPhaseInfo("end");
                		break;
        			case "execute":
                		d_gameEngine.setphase("end");
                		d_gameEngine.getPhaseView().showNextPhaseInfo("end");
                		break;
        		}
        		break;
            case "showmap":
            	d_gameEngine.getGameMap().showGameMap();
            	return "stayCurrentPlayer";

            // Handle deploying armies
            case "deploy":
                for (Country l_country : d_gameEngine.getGameMap().getCountries()) {
                    if (l_country.getCountryId() == Integer.parseInt(l_command[1])) {
                	    // Check if the player controls the specified country.
                	    if (!p_currentPlayer.getD_countries().contains(l_country)) {
                	        String l_response = String.format("Player \"%s\" does not control country \"%d\"", p_currentPlayer.getD_name(), l_country.getCountryId());
                	        System.out.println(l_response);
                	        return "stayCurrentPlayer";
                	    }
                	    
                	    // Check if the player has enough armies.
                	    if (p_currentPlayer.getD_reinforcementPool() < Integer.parseInt(l_command[2])) {
                	    	String l_response = String.format("Player \"%s\" does not have enough armies", p_currentPlayer.getD_name());
                	    	System.out.println(l_response);
                	        return "stayCurrentPlayer";
                	    }
                	    
                        DeployOrder deployOrder = new DeployOrder(p_currentPlayer, l_country, Integer.parseInt(l_command[2]));
                        p_currentPlayer.addOrder(deployOrder);
                        p_currentPlayer.decreaseReinforcementPool(Integer.parseInt(l_command[2]));
                        break;
                    }
                }
                break;

            default:
                System.out.println("Please Enter a Valid Command");
        }
        return "nextPlayer";
    }

}
