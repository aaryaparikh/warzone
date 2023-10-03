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
            if(l_commands.length == 1 || l_commands.length == 2) {
                switch(l_commands[0]) {
                    case "showmap": 
                        l_map.showMap(); 
                        break;
                    case "savemap": 
                        l_map.d_mapEditor.write(l_commands[1]); 
                        break;
                    case "validatemap":
                        if(l_map.d_mapEditor.validateMap()) {
                            System.out.println("Valid Map");
                        }
                        break;
                    case "loadmap":
                        l_map=l_map.d_mapEditor.loadMap(l_commands[1]);
                        break;
                    case "editmap":
                        l_map=l_map.d_mapEditor.loadMap(l_commands[1]);
                        break;
                    case "exit":
                        return;
                    case "end":
                        return;
                default:
                    System.out.println("Invalid Input");
                }
            }
            else{
                switch(l_commands[0]){
                    case "editcontinent":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    l_map.addContinent(Integer.parseInt(l_commands[++l_i]), Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                case "-remove":
                                    l_map.removeContinent(Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        } 
                        break;
                    case "editcountry":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    l_map.addCountry(Integer.parseInt(l_commands[++l_i]), Integer.parseInt(l_commands[++l_i])); 
                                    l_i++;
                                    break;
                                case "-remove":
                                    l_map.removeCountry(Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                            }
                        }
                        break;
                    case "editneighbor":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    int l_countryId=Integer.parseInt(l_commands[++l_i]);
                                    int l_neighbor=Integer.parseInt(l_commands[++l_i]);
                                    l_map.addNeighbor(l_countryId,l_neighbor);
                                    l_map.addNeighbor(l_neighbor, l_countryId);
                                    l_i++;
                                    break;
                                case "-remove":
                                    int l_rCountryId=Integer.parseInt(l_commands[++l_i]);
                                    int l_rNeighbor=Integer.parseInt(l_commands[++l_i]);
                                    l_map.removeNeighbor(l_rCountryId, l_rNeighbor); 
                                    l_map.removeNeighbor(l_rNeighbor, l_rCountryId); 
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                break;
                            }
                        }
                        break;
                    case "showmap": 
                        l_map.showMap(); 
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            }
            
        }
        
    }
}
