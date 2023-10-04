package Services;

import java.util.Scanner;
import Controller.GameEngine;
import Models.Player;

/**
 * StartupService for all command in startup phase
 */
public class StartUpGameService {
	private GameEngine d_gameEngine;
	
    /**
     * Constructor for Startup Service
     *
     * @param p_gameMap the game engine object
     */
	public StartUpGameService(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}
	
    /**
     * Main function for startup service
     *
     * @param args
     */
    public void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner l_scanner=new Scanner(System.in);
        
        Player l_defaultPlayer = new Player("You", d_gameEngine);
        d_gameEngine.addPlayer(l_defaultPlayer);
        d_gameEngine.assignCountriesRandomly();
        
        String l_userInput;
        while(true) {
        	
            l_userInput=l_scanner.nextLine();
            String l_commands[]=l_userInput.split(" ");
            
            switch(l_commands[0]){
	            case "end":
	            	boolean ifAllPlayerAssigned = true;
	            	for (Player l_player : d_gameEngine.getPlayers())
	            		if (l_player.getD_countries().size() == 0) {
		                	System.out.println("Please assign countries before end startup phase.");
		                	ifAllPlayerAssigned = false;
		                	break;
	            		}
	            	if (ifAllPlayerAssigned) {
	            		d_gameEngine.setphase("play");
	            		d_gameEngine.getPhaseView().showNextPhaseInfo("play");
		                return;      
	            	}
	            	break;
	            case "showmap": 
	                d_gameEngine.getGameMap().getD_mapView().showGameMap(); 
	                break;
	            case "loadmap":
	            	if (l_commands.length < 2)
	                	System.out.println("Please enter valid map file path");
	            	else
	            		d_gameEngine.setGameMap(d_gameEngine.getGameMap().d_mapEditor.loadMap(l_commands[1]));
	                break;
	            case "gameplayer":
	            	if (l_commands.length < 3)
	                	System.out.println("Please enter enough parameter for order gameplayer");
	            	else {
    	                Player l_player = new Player(l_commands[2], d_gameEngine);
	            		switch(l_commands[1]) {
		    	            case "-add": 
		    	                if(d_gameEngine.addPlayer(l_player))
		    	                	System.out.println("Player " + l_commands[2] + " is added.");
		    	                else
		    	                	System.out.println("Player " + l_commands[2] + " already exists. Can't add again.");
		    	                break;  
		    	            case "-remove": 
		    	                if(d_gameEngine.removePlayer(l_player))
		    	                	System.out.println("Player " + l_commands[2] + " is removed.");
		    	                else
		    	                	System.out.println("Player " + l_commands[2] + " don't exist. Can't remove.");
		    	                break;  
		                    default:
		                        System.out.println("Please enter valid parameter for order gameplayer");
	            		}
	            	}
	                break;
	            case "assigncountries":
	            	d_gameEngine.assignCountriesRandomly();
                	System.out.println("All countries are assigned to players");
	            	break;
                default:
                    System.out.println("Invalid Input");
            }   
        }

    }
}
