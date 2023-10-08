package Models.Orders;

import Controller.GameEngine;
import Models.Country;
import Models.Player;

public class BombOrder extends Order {
	private Player d_player;
	private Country d_targetCountry;
	
	public BombOrder(Player p_player, Country p_targetCountry) {
		this.d_player = p_player;
		this.d_targetCountry = p_targetCountry;
	}
	
	@Override
	public String execute(GameEngine p_game) {
		
		// Check if the player controls the specified country.
		if (d_player.getD_countries().contains(d_targetCountry))
			return(String.format("Player \"%s\" control target country \"%d\", can't bomb.",
					d_player.getName(), d_targetCountry.getCountryId()));
		
		// Check if the target country is adjacent to player's countries.
		for (Country l_ownedCountry : d_player.getD_countries())
			if (!l_ownedCountry.getNeighborCountries().contains(d_targetCountry.getCountryId()))
				return(String.format("Player \"%s\" is not adjacent to target country \"%d\", can't bomb.",
						d_player.getName(), d_targetCountry.getCountryId()));
		
		/*
		if (d_player.d_negotiatedPlayerNames.contains
				(p_game.getGameMap().getCountries().get(d_country).getPlayer().getName())) {
			return String.format("Cannot bomb, as diplomacy is established between \"%s\" and \"%s\".",
					d_player.getName(),
					p_game.getGameMap().getCountries().get(d_country).getPlayer().getName());
		}*/

		// Execute bomb card order
		if (d_targetCountry.getArmies() >= 0) {
			int previousArmis = d_targetCountry.getArmies();
			d_targetCountry.setArmies(d_targetCountry.getArmies() / 2);
			d_player.deleteCardsOwned("bomb");
			return String.format("Player \"%s\" bombed country \"%d\", kill \"%s\" armies.", d_player.getName(),
					d_targetCountry.getCountryId(), previousArmis-d_targetCountry.getArmies());
		} else {
			return String.format("Player \"%s\" throw a bomb to a country \"%d\" without armies.", 
					d_player.getName(), d_targetCountry.getCountryId());
		}
	}

}
