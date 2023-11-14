package Models.Strategy;

import java.util.List;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.Order;

/**
 *	Strategy of the Strategy pattern
 */
public abstract class PlayerStrategy {
	
	/**
	 *	THe Strategy needs to have access to the map to determine target territories for the orders.   
	 */
	public List<Country> d_countryList;
	
	/**
	 * 
	 */
	public Player d_player; 
	
	
	public LogEntryBuffer d_logEntryBuffer;
	
	protected abstract Country toAttack(); 
	protected abstract Country toAttackFrom(); 
	protected abstract Country toMoveFrom(); 
	protected abstract Country toDefend();
	public abstract Order createOrder();	
	
	PlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer){
		d_player = p_player; 
		d_countryList = p_countryList; 
		d_logEntryBuffer = p_logEntryBuffer;
	}
}
