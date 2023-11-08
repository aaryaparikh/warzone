package Models.Orders;

import Controller.GameEngine;

/**
 * Order abstract
 * 
 * @author YURUI
 */
public abstract class Order {

	/**
	 * method to execute command
	 * 
	 * @param p_game object calling this function
	 * @return Positive response if command was successful, otherwise negative reply
	 */
	public abstract String execute(GameEngine p_game);

	/**
	 * method to get command type
	 * 
	 * @return the command type
	 */
	public abstract String getOrderType();
}