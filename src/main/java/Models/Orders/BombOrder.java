package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

/**
 * Players can bomb another country.
 *
 */
public class BombOrder extends Order {
	/**
	 * Order giving player
	 */
	private Player d_player;

	/**
	 * Country where bomb is to be executed
	 */
	private Country d_targetCountry;

	/**
	 * Constructor to assign initial values
	 *
	 * @param p_player        player who is committing bomb order
	 * @param p_targetCountry bomb which country
	 */
	public BombOrder(Player p_player, Country p_targetCountry) {
		this.d_player = p_player;
		this.d_targetCountry = p_targetCountry;
	}

	/**
	 * Method to execute bomb order.
	 *
	 * @param p_game the object representing the game.
	 * @return a response string indicating the result of the order execution.
	 */
	@Override
	public String execute(GameEngine p_game) {

		// Check if the player controls the specified country.
		if (d_player.getD_countries().contains(d_targetCountry))
			return (String.format("Player \"%s\" control target country \"%d\", can't bomb.", d_player.getName(),
					d_targetCountry.getCountryId()));

		// Check if the player has negotiated with player who owns target country.
		if (d_player.getNegotiatedPlayers().contains(d_targetCountry.getOwner())) {
			return String.format("Can't bomb, there is a negotiation between \"%s\" and \"%s\".", d_player.getName(),
					d_targetCountry.getOwner().getName());
		}

		// Check if the target country is adjacent to player's countries.
		boolean l_ifBombNeighbor = false;
		for (Country l_ownedCountry : d_player.getD_countries())
			if (l_ownedCountry.getNeighborCountries().contains(d_targetCountry.getCountryId()))
				l_ifBombNeighbor = true;
		if (!l_ifBombNeighbor)
			return String.format("Player \"%s\" is not adjacent to target country \"%d\", can't bomb.",
					d_player.getName(), d_targetCountry.getCountryId());

		// Execute bomb card order
		if (d_targetCountry.getArmies() >= 0) {
			int previousArmis = d_targetCountry.getArmies();
			d_targetCountry.setArmies(d_targetCountry.getArmies() / 2);
			return String.format("Player \"%s\" bombed country \"%d\", kill \"%s\" armies.", d_player.getName(),
					d_targetCountry.getCountryId(), previousArmis - d_targetCountry.getArmies());
		} else {
			return String.format("Player \"%s\" throw a bomb to a country \"%d\" without armies.", d_player.getName(),
					d_targetCountry.getCountryId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderType() {
		return "Bomb";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderInfo() {
		return String.format("%s bomb %d %d %d", d_player.getName(), d_targetCountry.getCountryId());
	}
}
