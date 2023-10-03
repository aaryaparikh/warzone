package Models.Orders;

import Controller.GameEngine;

/**
 * Order abstract
 */
public abstract class Order {

	/**
	 * method to execute deploy command
	 * 
	 * @param p_game object calling this function
	 * @return Positive response if command was successful, otherwise negative reply
	 */
	public abstract String execute(GameEngine p_game);
}