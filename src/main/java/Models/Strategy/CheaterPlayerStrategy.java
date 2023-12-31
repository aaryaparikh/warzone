package Models.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 * CheaterPlayerStrategy represents the Cheater strategy of the Strategy
 * pattern. This strategy cheats by automatically attacking adjacent countries
 * controlled by other players. It also deploys armies during reinforcement and
 * advances to attack when possible.
 * 
 * @author Yurui
 */
public class CheaterPlayerStrategy extends PlayerStrategy {

	/**
	 * True is to attack, false is to defend
	 */
	private boolean d_attackOrDefend = true;

	/**
	 * Army capacity difference
	 */
	private int d_armiesDiff = 0;

	/**
	 * The last attack target
	 */
	private Country d_lastTarget = null;

	/**
	 * Creates a CheaterPlayerStrategy instance with the specified player, country
	 * list, and log entry buffer.
	 * 
	 * @param p_player         The player associated with this strategy.
	 * @param p_countryList    The list of countries owned by the player.
	 * @param p_logEntryBuffer The buffer for logging strategy-related entries.
	 */
	public CheaterPlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack. The Cheater player automatically attacks
	 * adjacent countries controlled by other players.
	 * 
	 * @param p_sourceCountry The country from which the attack is initiated.
	 * @return The target country to attack, or null if no valid target.
	 */
	protected Country toAttack(Country p_sourceCountry) {
		if (p_sourceCountry == null)
			return null;

		int l_attackTarget = 0;
		List<Country> l_neighborList = new ArrayList<Country>();

		// Judge whether neighbor is control by himself
		for (int l_neighbor : p_sourceCountry.getNeighborCountries()) {
			boolean l_ifNeighborOwned = false;
			for (Country l_countryInList : d_countryList)
				if ((l_countryInList.getCountryId() == l_neighbor)
						&& (l_countryInList.getOwner().getName().equals(d_player.getName())))
					l_ifNeighborOwned = true;

			// Could attack country not controled
			if (l_ifNeighborOwned == false) {
				l_attackTarget = l_neighbor;
				for (Country l_country : d_player.getD_gameEngine().getGameMap().getCountries())
					if (l_country.getCountryId() == l_attackTarget)
						l_neighborList.add(l_country);
			}
		}

		Collections.sort(l_neighborList, Comparator.comparingInt(Country::getArmies));

		Country l_countryInList = null;
		for (Country l_country : d_countryList)
			if (l_country.getCountryId() == p_sourceCountry.getCountryId())
				l_countryInList = l_country;

		for (Country l_targetCountry : l_neighborList) {
			for (Country l_neighborCountryInList : d_countryList)
				if (l_neighborCountryInList.getCountryId() == l_targetCountry.getCountryId()) {
					d_armiesDiff = cheatNumberOfArmies(l_countryInList, l_neighborCountryInList);

					// to attck neighbor country with least armies
					if (d_attackOrDefend == true)
						return l_targetCountry;
					else {
						d_attackOrDefend = true;
						break;
					}
				}
		}

		if (d_lastTarget != null)
			d_attackOrDefend = true;
		else
			d_attackOrDefend = false;
		return null;
	}

	/**
	 * Determines where to defend
	 * 
	 * @return The player's country with the most armies, or null if no valid
	 *         source.
	 */
	protected Country toDefend() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		d_armiesDiff = 0;
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId()) {
					toAttack(l_countryInGame);
					if (d_armiesDiff == 0)
						continue;
					else
						return l_countryOwned;
				}
		}

		d_armiesDiff = d_player.getD_reinforcementPool();
		for (Country l_country : d_player.getD_countries())
			if (l_country.getCountryId() == d_countryList.get(0).getCountryId())
				return l_country;
		return d_player.getD_countries().get(0);
	}

	/**
	 * Determines where to launch an attack from.
	 * 
	 * @return attack source country
	 */
	protected Country toAttackFrom() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())
					if ((toAttack(l_countryInGame) != null) || (d_lastTarget != null))
						return l_countryOwned;
		}
		return null;
	}

	/**
	 * Determine where armies are moved to.
	 * 
	 * @return null
	 */
	protected Country toMoveFrom(Country p_sourceCountry) {
		return null;
	}

	/**
	 * Creates an order. The Cheater player deploys armies first and advances to
	 * attack if possible.
	 * 
	 * @return The created order.
	 */
	public Order createOrder() {

		if (d_player.getD_reinforcementPool() > 0) {
			Country l_deployCountry = toDefend();

			// Log order
			String l_response = String.format("Cheater Strategy deploy \"%s\" armies on \"%s\" for Player \"%s\".",
					d_armiesDiff, l_deployCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);

			// Update game information
			d_player.decreaseReinforcementPool(d_armiesDiff);
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_deployCountry.getCountryId())
					l_country.addArmies(d_armiesDiff);

			return new DeployOrder(d_player, l_deployCountry, d_armiesDiff);
		} else {
			Country l_attackSourceCountry = toAttackFrom();
			Country l_attackTargetCountry = toAttack(l_attackSourceCountry);

			Country l_attackSourceCountryInList = null;

			// Judge whether source country is null
			if (l_attackSourceCountry == null)
				return null;

			// Judge whether source country has advanced
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
					l_attackSourceCountryInList = l_country;
			if (l_attackSourceCountryInList.getArmies() == 0)
				return null;

			int l_attackArmies = 0;
			if (l_attackTargetCountry == null) {

				// No enemy country ajacent to source country
				if (d_lastTarget == null)
					return null;

				l_attackTargetCountry = d_lastTarget;
				l_attackArmies = l_attackSourceCountryInList.getArmies();
			} else {
				l_attackArmies = (int) Math.ceil(l_attackTargetCountry.getArmies() * 0.7 * (5 / 3) + 1);
			}

			// Log order
			String l_response = String.format(
					"Cheater Strategy advance \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".", l_attackArmies,
					l_attackSourceCountry.getCountryId(), l_attackTargetCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);

			// Update game information
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
					l_country.setArmies(l_country.getArmies() - l_attackArmies);
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_attackTargetCountry.getCountryId())
					l_country.setOwner(d_player);
			d_lastTarget = l_attackTargetCountry;

			return new AdvanceOrder(d_player, l_attackSourceCountry, l_attackTargetCountry, l_attackArmies);
		}
	}

	/**
	 * To calculate the army capacity difference and decide whether to attack or
	 * defend
	 * 
	 * @param p_sourceCountry The source country.
	 * @param p_targetCountry The target country.
	 * @return The number of armies for cheating.
	 */
	private int cheatNumberOfArmies(Country p_sourceCountry, Country p_targetCountry) {
		d_attackOrDefend = true;
		int l_sourceCountryArmies = p_sourceCountry.getArmies();
		int l_targetCountryArmies = p_targetCountry.getArmies();

		int l_capabilitySourceCountryArmies = (int) Math.ceil(l_sourceCountryArmies * 0.6);
		int l_capabilityTargetCountryArmies = (int) Math.ceil(l_targetCountryArmies * 0.7);

		if (l_capabilitySourceCountryArmies > l_capabilityTargetCountryArmies)
			return 0;
		else if (l_capabilitySourceCountryArmies == l_capabilityTargetCountryArmies)
			return 1;
		else if (l_capabilitySourceCountryArmies < l_capabilityTargetCountryArmies) {
			int l_armies = (l_capabilityTargetCountryArmies + 1 - l_capabilitySourceCountryArmies) * (5 / 3);
			if (l_armies > d_player.getD_reinforcementPool()) {
				d_attackOrDefend = false;
				return d_player.getD_reinforcementPool();
			} else
				return l_armies;
		}
		return 0;
	}
}
