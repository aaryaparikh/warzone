package Models.Strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 * AggressivePlayerStrategy represents the Aggressive strategy of the Strategy
 * pattern. This strategy focuses on deploying armies aggressively and attacking
 * neighboring countries.
 * 
 * @author Yurui
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

	/**
	 * Creates an AggressivePlayerStrategy instance with the specified player,
	 * country list, and log entry buffer.
	 * 
	 * @param p_player         The player associated with this strategy.
	 * @param p_countryList    The list of countries owned by the player.
	 * @param p_logEntryBuffer The buffer for logging strategy-related entries.
	 */
	public AggressivePlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack. The Aggressive player targets the
	 * neighboring country with the lowest armies.
	 * 
	 * @param p_sourceCountry The country from which the attack is initiated.
	 * @return The target country to attack.
	 */
	protected Country toAttack(Country p_sourceCountry) {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());

		int l_attackTarget = 0;
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())

					// Judge whether neighbor is control by himself
					for (int l_neighbor : p_sourceCountry.getNeighborCountries()) {
						boolean l_ifNeighborOwned = false;
						for (Country l_neighborOwned : d_player.getD_countries())
							if (l_neighbor == l_neighborOwned.getCountryId())
								l_ifNeighborOwned = true;

						// Could attack country not controled
						if (l_ifNeighborOwned == false)
							l_attackTarget = l_neighbor;
					}
		}

		if (l_attackTarget != 0)
			for (Country l_country : d_player.getD_gameEngine().getGameMap().getCountries())
				if (l_country.getCountryId() == l_attackTarget)
					return l_country;
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Aggressive player does not
	 * defend, so it returns null.
	 * 
	 * @return null
	 */
	protected Country toDefend() {
		return null;
	}

	/**
	 * Determines where to launch an attack from. The Aggressive player selects the
	 * country with the most armies.
	 * 
	 * @return The player's country with the most armies.
	 */
	protected Country toAttackFrom() {
		Collections.sort(d_countryList, Comparator.comparingInt(Country::getArmies).reversed());
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryOwned.getCountryId() == l_countryInGame.getCountryId())

					// Judge whether neighbor is control by himself
					for (int l_neighbor : l_countryOwned.getNeighborCountries()) {
						boolean l_ifNeighborOwned = false;
						for (Country l_neighborOwned : d_player.getD_countries())
							if (l_neighbor == l_neighborOwned.getCountryId())
								l_ifNeighborOwned = true;

						// Could attack country not controled
						if (l_ifNeighborOwned == false)
							return l_countryOwned;
					}
		}
		return null;
	}

	/**
	 * Determines where to move armies from. The Aggressive player does not move
	 * armies, so it returns null.
	 * 
	 * @param p_targetCountry The country from which the attack is initiated.
	 * @return null
	 */
	protected Country toMoveFrom(Country p_targetCountry) {
		for (Country l_countryInGame : d_countryList) {
			for (Country l_countryOwned : d_player.getD_countries())
				if (l_countryInGame.getCountryId() == l_countryOwned.getCountryId())
					if (p_targetCountry.getNeighborCountries().contains(l_countryInGame.getCountryId())
							&& (l_countryInGame.getArmies() != 0))
						return l_countryOwned;
		}
		return null;
	}

	/**
	 * Creates an order based on the Aggressive strategy. Deploys armies first and
	 * advances to attack if possible.
	 * 
	 * @return The created order.
	 */
	public Order createOrder() {

		// Deploy strongest country each time
		if (d_player.getD_reinforcementPool() > 0) {
			Random l_rand = new Random();
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool()) + 1;
			Country l_deployCountry = toAttackFrom();

			// Log order
			String l_response = String.format("Aggressive Strategy deploy \"%s\" armies on \"%s\" for Player \"%s\".",
					l_armies, l_deployCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);

			// Update game information
			d_player.decreaseReinforcementPool(l_armies);
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_deployCountry.getCountryId())
					l_country.addArmies(l_armies);

			return new DeployOrder(d_player, l_deployCountry, l_armies);
		} else {
			Country l_attackSourceCountry = toAttackFrom();
			Country l_attackSourceCountryInList = null;

			// No more attack order
			if (l_attackSourceCountry == null)
				return null;
			else
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
						l_attackSourceCountryInList = l_country;

			// Aggrerate armies before attacking
			Country l_moveSourceCountry = toMoveFrom(l_attackSourceCountry);
			if (l_moveSourceCountry != null) {
				int l_moveArmies = l_moveSourceCountry.getArmies();

				// Log order
				String l_response = String.format(
						"Aggressive Strategy advance to aggregate \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".",
						l_moveArmies, l_moveSourceCountry.getCountryId(), l_attackSourceCountry.getCountryId(),
						d_player.getName());
				d_logEntryBuffer.setString(l_response);
				System.out.println(l_response);

				// Update game information
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
						l_country.setArmies(l_country.getArmies() + l_moveSourceCountry.getArmies());
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_moveSourceCountry.getCountryId())
						l_country.setArmies(0);

				return new AdvanceOrder(d_player, l_moveSourceCountry, l_attackSourceCountry, l_moveArmies);
			} else {
				Country l_attackTargetCountry = toAttack(l_attackSourceCountry);

				if (l_attackSourceCountryInList.getArmies() == 0)
					return null;

				int l_attackArmies = l_attackSourceCountryInList.getArmies();

				// Log order
				String l_response = String.format(
						"Aggressive Strategy advance \"%s\" armies from \"%s\" to attack \"%s\" for Player \"%s\".",
						l_attackArmies, l_attackSourceCountry.getCountryId(), l_attackTargetCountry.getCountryId(),
						d_player.getName());
				d_logEntryBuffer.setString(l_response);
				System.out.println(l_response);

				// Update game information
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_attackSourceCountry.getCountryId())
						l_country.setArmies(0);

				return new AdvanceOrder(d_player, l_attackSourceCountry, l_attackTargetCountry, l_attackArmies);
			}
		}
	}
}
