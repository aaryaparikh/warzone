package Controller;

import java.util.Scanner;

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
     *
     * @return A string to output the result of the issue orders phase.
     */
    public String issueOrders() {
        System.out.println("\nIssue orders phase start");

        for (Player player : super.d_gameEngine.getPlayers()) {
            System.out.println("Player " + player.getName() + "'s turn");

            Scanner l_scanner = new Scanner(System.in);
            String l_userInput;
            l_userInput = l_scanner.nextLine();

            while ("continue".equals(super.d_commandHandler.handlePlayerCommand(l_userInput, player))) {
                l_userInput = l_scanner.nextLine();
            }

            l_scanner.close();
        }

        System.out.println("\nIssue orders phase end");
        return "";
    }
}

