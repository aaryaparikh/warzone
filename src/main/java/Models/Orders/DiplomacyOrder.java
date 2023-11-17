package Models.Orders;

import Controller.GameEngine;
import Models.Player;

/**
 * Players can negotiate with another player.
 *
 */
public class DiplomacyOrder extends Order {
	/**
	 * Order giving player
	 */
	private Player d_player;

	/**
	 * PLayer with who the negotiation takes place
	 */
	private Player d_targetPlayer;

	/**
	 * Constructor to assign initial values
	 *
	 * @param p_player       player who is committing deploy order
	 * @param p_targetPlayer negotiate with which player
	 */
	public DiplomacyOrder(Player p_player, Player p_targetPlayer) {
		this.d_player = p_player;
		this.d_targetPlayer = p_targetPlayer;
	}

	/**
	 * Method to execute diplomacy order.
	 *
	 * @param p_game the object representing the game.
	 * @return a response string indicating the result of the order execution.
	 */
	@Override
	public String execute(GameEngine p_game) {

		// Check if the player is still in game.
		if (d_player.getD_countries().size() == 0)
			return (String.format("Player \"%s\" is not in game.", d_player.getName()));

		// Check if the target player is still in game.
		if (d_targetPlayer.getD_countries().size() == 0)
			return (String.format("Target player \"%s\" is not in game, can't negotiate.", d_targetPlayer.getName()));

		// Execute negotiate card order
		if (d_targetPlayer.getNegotiatedPlayers().contains(d_player))
			return String.format("Player \"%s\" has already negotiated with player \"%s\", can't do it again.",
					d_targetPlayer.getName(), d_player.getName());
		else {
			d_player.addNegotiatedPlayers(d_targetPlayer);
			d_targetPlayer.addNegotiatedPlayers(d_player);
			return String.format("Player \"%s\" manage to negotiate with player \"%s\".", d_player.getName(),
					d_targetPlayer.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderType() {
		return "Negotiate";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrderInfo() {
		return String.format("%s negotiate %d", d_player.getName());
	}
}
