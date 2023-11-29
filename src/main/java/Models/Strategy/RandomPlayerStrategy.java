package Models.Strategy;

import java.util.List;
import java.util.Random;

import Controller.LogEntryBuffer;
import Models.Country;
import Models.Player;
import Models.Orders.AdvanceOrder;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

/**
 * RandomPlayerStrategy represents the Random strategy of the Strategy pattern.
 * This strategy focuses on deploying armies aggressively and moving to
 * neighboring countries.
 * 
 * @author Yurui
 */
public class RandomPlayerStrategy extends PlayerStrategy {

	/**
	 * Creates an RandomPlayerStrategy instance with the specified player, country
	 * list, and log entry buffer.
	 * 
	 * @param p_player         The player associated with this strategy.
	 * @param p_countryList    The list of countries owned by the player.
	 * @param p_logEntryBuffer The buffer for logging strategy-related entries.
	 */
	public RandomPlayerStrategy(Player p_player, List<Country> p_countryList, LogEntryBuffer p_logEntryBuffer) {
		super(p_player, p_countryList, p_logEntryBuffer);
	}

	/**
	 * Determines the country to attack to. The Randon player does not attack, so
	 * this returns null.
	 * 
	 * @return null
	 */
	protected Country toAttack(Country p_sourceCountry) {
		return null;
	}

	/**
	 * Determines which territory should be defended. The Random player randomly
	 * decides to defend its country.
	 * 
	 * @return the player's country to defend
	 */
	protected Country toDefend() {
		Random l_rand = new Random();
		return d_player.getD_countries().get(l_rand.nextInt(d_player.getD_countries().size()));
	}

	/**
	 * Determines where to launch an attack from. The Random player does not attack,
	 * so it returns null
	 * 
	 * @return null
	 */
	protected Country toAttackFrom() {
		return null;
	}

	/**
	 * Determine where armies are moved to.
	 * 
	 * @return country to move
	 */
	protected Country toMoveFrom(Country p_sourceCountry) {
		Random l_rand = new Random();
		return d_player.getD_countries().get(l_rand.nextInt(d_player.getD_countries().size()));
	}

	/**
	 * Create an order. the Random player can only use Deploy orders and move armies
	 * 
	 * @return created order
	 */
	public Order createOrder() {
		Random l_rand = new Random();

		if (d_player.getD_reinforcementPool() > 0) {
			int l_armies = l_rand.nextInt(d_player.getD_reinforcementPool()) + 1;
			Country l_deployCountry = toDefend();

			// Log order
			String l_response = String.format("Random Strategy deploy \"%s\" armies on \"%s\" for Player \"%s\".",
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
			Country l_randomSourceCountry = toMoveFrom(null);
			Country l_randomTargetCountry = findNeighborCountry(l_randomSourceCountry);

			// Depend on country in player's view
			Country l_randomSourceCountryInList = null;

			// If no source country
			if (l_randomSourceCountry == null || l_randomTargetCountry == null)
				return null;
			else
				for (Country l_country : d_countryList)
					if (l_country.getCountryId() == l_randomSourceCountry.getCountryId())
						l_randomSourceCountryInList = l_country;

			// If source country no armies
			if (l_randomSourceCountryInList.getArmies() == 0)
				return null;

			int l_movedArmies = l_rand.nextInt(l_randomSourceCountryInList.getArmies()) + 1;

			// Log order
			String l_response = String.format(
					"Random Strategy advance \"%s\" armies from \"%s\" to \"%s\" for Player \"%s\".", l_movedArmies,
					l_randomSourceCountry.getCountryId(), l_randomTargetCountry.getCountryId(), d_player.getName());
			d_logEntryBuffer.setString(l_response);
			System.out.println(l_response);

			// Update game information
			for (Country l_country : d_countryList)
				if (l_country.getCountryId() == l_randomSourceCountry.getCountryId())
					l_country.setArmies(0);

			return new AdvanceOrder(d_player, l_randomSourceCountry, l_randomTargetCountry, l_movedArmies);
		}
	}

	/**
	 * Find a neighbor country
	 * 
	 * @param p_country The source country
	 * @return neighbor country
	 */
	private Country findNeighborCountry(Country p_country) {
		Random l_rand = new Random();
		int l_neighborId = p_country.getNeighborCountries()
				.get(l_rand.nextInt(p_country.getNeighborCountries().size()));
		for (Country l_country : d_player.getD_gameEngine().getGameMap().getCountries())
			if (l_country.getCountryId() == l_neighborId)
				return l_country;
		return null;
	}
}