package Services;

import java.util.Scanner;

import Models.GameMap;

/**
 * MapService for all command in map edit phase
 */
public class MapService {
	public static GameMap d_map;
	
    /**
     * Constructor for Map Service
     *
     * @param p_gameMap the game map object
     */
	public MapService(GameMap p_gameMap) {
		d_map = p_gameMap;
	}
	
    /**
     * Main function for map service
     *
     * @param args
     */
    public void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner l_sc=new Scanner(System.in);
        
        GameMap l_map = d_map;
        
        while(true){
            String l_userInput;
            l_userInput=l_sc.nextLine();
            String l_commands[]=l_userInput.split(" ");
            switch(l_commands[0]) {
                case "showmap": 
                    l_map.showMap(); 
                    break;
                case "savemap":
                	if(l_commands.length < 2)
                        System.out.println("Please enter file path to save map.");
                    else
                        l_map.d_mapEditor.write(l_commands[1]); 
                    break;
                case "validatemap":
                    if(l_map.d_mapEditor.validateMap()) {
                        System.out.println("Valid Map");
                    }
                    break;
                case "loadmap":
                	if(l_commands.length < 2)
                        System.out.println("Please enter file path to load map.");
                	else
                		l_map=l_map.d_mapEditor.loadMap(l_commands[1]);
                    break;
                case "editmap":
                	if(l_commands.length < 2)
                        System.out.println("Please enter file path to load map.");
                	else
                		l_map=l_map.d_mapEditor.loadMap(l_commands[1]);
                    break;
                case "exit":
                    return;
                case "end":
                    return;
                case "editcontinent":
                	if(l_commands.length < 3)
                		System.out.println("Please enter enough parameter for editing continent.");
                	else
	                    switch(l_commands[1]) {
	                        case "-add":
	                        	if(l_commands.length < 4)
	                        		System.out.println("Please enter enough parameter for adding continent.");
	                        	else
	                        		l_map.addContinent(Integer.parseInt(l_commands[2]), Integer.parseInt(l_commands[3]));
	                            break;
	                        case "-remove":
	                        	if(l_commands.length < 3)
	                        		System.out.println("Please enter enough parameter for removing continent.");
	                        	else
	                        		l_map.removeContinent(Integer.parseInt(l_commands[2]));
	                            break;
	                        default:
	                            System.out.println("Invalid Input");
	                            break;
	                    }
                    break;
                case "editcountry":
                	if(l_commands.length < 3)
                		System.out.println("Please enter enough parameter for editing country.");
                    switch(l_commands[1]) {
                        case "-add":
                        	if(l_commands.length < 4)
                        		System.out.println("Please enter enough parameter for adding country.");
                        	else
                        		l_map.addCountry(Integer.parseInt(l_commands[2]), Integer.parseInt(l_commands[3])); 
                            break;
                        case "-remove":
                        	if(l_commands.length < 3)
                        		System.out.println("Please enter enough parameter for removing country.");
                        	else
                        		l_map.removeCountry(Integer.parseInt(l_commands[2]));
                            break;
                        default:
                            System.out.println("Invalid Input");
                    }
                    break;
                case "editneighbor":
                	if(l_commands.length < 4)
                		System.out.println("Please enter enough parameter for editing neighbor.");
                        switch(l_commands[1]) {
                            case "-add":
                                int l_countryId=Integer.parseInt(l_commands[2]);
                                int l_neighbor=Integer.parseInt(l_commands[3]);
                                l_map.addNeighbor(l_countryId,l_neighbor);
                                l_map.addNeighbor(l_neighbor, l_countryId);
                                break;
                            case "-remove":
                                int l_rCountryId=Integer.parseInt(l_commands[2]);
                                int l_rNeighbor=Integer.parseInt(l_commands[3]);
                                l_map.removeNeighbor(l_rCountryId, l_rNeighbor); 
                                l_map.removeNeighbor(l_rNeighbor, l_rCountryId); 
                                break;
                            default:
                                System.out.println("Invalid Input");
                            break;
                        }
                    break;
            default:
                System.out.println("Invalid Input");
            }         
        }
        
    }
}
