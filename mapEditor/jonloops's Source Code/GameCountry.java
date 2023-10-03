package Models;

import java.util.ArrayList;
import java.util.List;

class GameCountry {
    private int name;
    private int armies;
    private Player owner;
    private List<GameCountry> neighbors;
    private GameContinent continent;
    private int countryContinent;

    public GameCountry(int countryName) {
        this.name = countryName;
        this.armies = 0;
        this.neighbors = new ArrayList<>();
    }

    public int getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public void addArmies(int numArmies) {
        armies += numArmies;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public List<GameCountry> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(GameCountry neighbor) {
        neighbors.add(neighbor);
    }

    public void removeNeighbor(GameCountry neighbor) {
        neighbors.remove(neighbor);
    }
    
    public GameContinent getContinent() {
        return continent;
    }

	public void setContinent(GameContinent Continent) {
		this.continent = continent;
		
	}

	public int getCountryContinent() {
		return countryContinent;
	}

	public void setCountryContinent(int countryContinent) {
		this.countryContinent = countryContinent;
	}
}
