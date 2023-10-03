package Services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Start-up Phase: <br>
 * 1. Load the map for game play <br>
 * 2. Show map <br>
 * 3. Add players and assign countries randomly
 */
public class StartUpGameService {
    public static void main(String[] args) {
        
    	GameMap d_map = new GameMap();
    	
        @SuppressWarnings("resource")
			Scanner l_loadMap=new Scanner(System.in);
        
        System.out.println("Please load the map..  [Use command: loadmap <filename.txt>]");
        
        //load map from file saved from mapEditor
        String l_userInputToLoadMap;
        l_userInputToLoadMap=l_loadMap.nextLine();
        String l_loadMapCommand[]=l_userInputToLoadMap.split(" ");

        if (l_loadMapCommand.length == 2) {
        	switch(l_loadMapCommand[0]){
        		case "loadmap": ((GameMap) d_map).loadMapFromFile(l_loadMapCommand[1]);
        	}
//            String d_filename = "your_map_file.txt";
        } else {
        	System.out.println("Command cannot be executed. Please check your input again.");
        }
    
        GameEngine d_gameEngine = new GameEngine(d_map);
    	
        System.out.println("Please add players..  [Use command: gameplay -add <player name> or gameplay -remove <player name>]");
        
        String addPlayerStatus = "in-progress";
        while (!(addPlayerStatus == "complete")) {
        	
        @SuppressWarnings("resource")
			Scanner l_addPlayers=new Scanner(System.in);
        
        //add players
        String l_userInputToAddPlayers;
        l_userInputToAddPlayers=l_addPlayers.nextLine();
        String l_addPlayersCommand[]=l_userInputToAddPlayers.split(" ");
        
        if (l_addPlayersCommand.length > 0) {
        	switch(l_addPlayersCommand[0]){
        	case "gameplay" :
        		switch (l_addPlayersCommand[1]){
	        		case "complete":
	        			addPlayerStatus = "complete";
	        			System.out.println(" ");
	        			break;
	        		case "-add":
	        			Player addPlayer = new Player(l_addPlayersCommand[2]);
	        			addPlayer.setName(l_addPlayersCommand[2]);
	        			d_gameEngine.addPlayer(addPlayer);
	        			System.out.println("Player " + l_addPlayersCommand[2] + " added.");
	        			System.out.println(" ");
	        			break;
	        		case "-remove":
	        			Player removePlayer = new Player(l_addPlayersCommand[2]);
	        			d_gameEngine.removePlayer(removePlayer);
	        			System.out.println("Player " + l_addPlayersCommand[2] + " removed.");
	        			System.out.println(" ");
	        			break;
        		}
        	}
//            String d_filename = "your_map_file.txt";
            if (!(addPlayerStatus == "complete")) {
        	System.out.println("Please continue to add or remove players.. [Once done: gameplay complete]");
            }
        } else {
        	System.out.println("Command cannot be executed. Please check your input again.");
        	}
        }

        
        @SuppressWarnings("resource")
			Scanner l_assignCountries=new Scanner(System.in);
        
        System.out.println("Please assign the countries..  [Use command: assigncountries]");
        
        // assign countries randomly for each players
        String l_userInputToAssignCountries;
        l_userInputToAssignCountries=l_assignCountries.nextLine();
        String l_assignCountriesCommand[]=l_userInputToAssignCountries.split(" ");

        if (l_assignCountriesCommand.length == 1) {
        	switch(l_assignCountriesCommand[0]){
        		case "assigncountries": d_gameEngine.assignCountriesRandomly();
        	}
        } else {
        	System.out.println("Command cannot be executed. Please check your input again.");
        }

        
        // assign reinforcements for each players
        d_gameEngine.assignReinforcements();;
        
        // show map
        ((GameMap) d_map).showMap();
        
    }
}
