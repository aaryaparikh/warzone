package Controller;

import Models.Country;
import Models.Player;
import Models.Orders.DeployOrder;

public class CommandHandler{
	private GameEngine d_gameEngine;
	
    public CommandHandler(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Handles player commands.
     * 
     * @param command       The player's command.
     * @param currentPlayer The current player.
     * @return A response string indicating the result of the command.
     */
    public String handlePlayerCommand(String command, Player currentPlayer) {
        String[] commands = command.split(" ");
        if (commands.length == 1 || commands.length == 2) {
            switch (commands[0]) {
                case "showmap":
                	d_gameEngine.getGameMap().showMap();
                    break;
                case "commit":
                    return "commit";
                case "deploy":
                    for (Player player : d_gameEngine.getPlayers()) {
                        if (player.equals(currentPlayer)) {
                            for (Country country : d_gameEngine.getGameMap().getCountries()) {
                                if (country.getName().equals(commands[1])) {
                                    DeployOrder deployOrder = new DeployOrder(player, country, Integer.parseInt(commands[2]));
                                    player.issueOrder(deployOrder);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("Please Enter Valid Command");
            }
        } else {
            switch (commands[0] + " " + commands[1]) {
                case "editcontinent -add":
                	d_gameEngine.getGameMap().addContinent(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
                    break;
                case "editcontinent -remove":
                	d_gameEngine.getGameMap().removeContinent(Integer.parseInt(commands[2]));
                    break;
                case "editcountry -add":
                	d_gameEngine.getGameMap().addCountry(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
                    break;
                case "showmap":
                	d_gameEngine.getGameMap().showMap();
                    break;

                default:
                    System.out.println("Please Enter Valid Command");
            }
        }
        return "continue";
    }
}
