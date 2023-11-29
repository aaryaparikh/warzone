package Models.Strategy;

import java.util.List;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.Order;

/**
 * Abstract class representing a strategy in the Strategy pattern.
 */
public abstract class PlayerStrategy {

	/**
	 * The list of countries associated with the strategy.
	 */
	public List<Country> d_countryList;

	/**
	 * The player associated with the strategy.
	 */
	public Player d_player;

	/**
	 * Buffer for logging strategy-related entries.
	 */
	public LogEntryBuffer d_logEntryBuffer;

	/**
	 * Constructs a PlayerStrategy instance.
	 *
	 * @param p_player         The player associated with the strategy.
	 * @param p_countryList    The list of countries associated with the strategy.
	 * @param p_logEntryBuffer Buffer for logging strategy-related entries.
	 */
	protected PlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		d_player = p_player;
		d_countryList = p_countryList;
		d_logEntryBuffer = p_logEntryBuffer;
	}

	/**
	 * Determines the country to attack.
	 *
	 * @param p_sourceCountry The country from which the attack is initiated.
	 * @return The target country to attack.
	 */
	protected abstract Country toAttack(Country p_sourceCountry);

	/**
	 * Determines where to launch an attack from.
	 *
	 * @return The player's country with the most armies.
	 */
	protected abstract Country toAttackFrom();

	/**
	 * Determines where armies are moved from.
	 *
	 * @param p_sourceCountry The country from which the attack is initiated.
	 * @return The source country for moving armies.
	 */
	protected abstract Country toMoveFrom(Country p_sourceCountry);

	/**
	 * Determines which territory should be defended.
	 *
	 * @return The player's country with the most armies for defense.
	 */
	protected abstract Country toDefend();

	/**
	 * Creates an order based on the strategy.
	 *
	 * @return The created order.
	 */
	public abstract Order createOrder();
}
