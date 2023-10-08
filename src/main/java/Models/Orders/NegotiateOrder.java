package Models.Orders;

import Controller.GameEngine;
import Models.Player;

public class NegotiateOrder extends Order {
	private Player d_player;
	private Player d_targetPlayer;
	
	public NegotiateOrder(Player p_player, Player p_targetPlayer) {
		this.d_player = p_player;
		this.d_targetPlayer = p_targetPlayer;
	}
	
	@Override
	public String execute(GameEngine p_game) {
		
		// Check if the player is still in game.
		if (d_player.getD_countries().size() == 0)
			return(String.format("Player \"%s\" is not in game.", d_player.getName()));
		
		// Check if the target player is still in game.
		if (d_targetPlayer.getD_countries().size() == 0)
			return(String.format("Target player \"%s\" is not in game, can't negotiate.", d_targetPlayer.getName()));

		// Execute negotiate card order
		if (d_targetPlayer.getNegotiatedPlayers().contains(d_player))
			return String.format("Player \"%s\" has already negotiated with player \"%s\", can't do it again.", 
					d_targetPlayer.getName(), d_player.getName());
		else {
			d_targetPlayer.addNegotiatedPlayers(d_player);
			d_player.deleteCardsOwned("negotiate");
			return String.format("Player \"%s\" manage to negotiate with player \"%s\".", 
					d_player.getName(),d_targetPlayer.getName());
		}
	}

}
