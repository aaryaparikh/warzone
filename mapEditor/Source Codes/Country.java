package comp691.project.map;
import java.util.ArrayList;
import java.util.List;

class Country {
    private String name;
    private int armies;
    private Player owner;
    private List<Country> neighbors;
    private Continent continent;

    public Country(String name) {
        this.name = name;
        this.armies = 0;
        this.neighbors = new ArrayList<>();
        this.continent = continent;
    }

    public String getName() {
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

    public List<Country> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Country neighbor) {
        neighbors.add(neighbor);
    }

    public void removeNeighbor(Country neighbor) {
        neighbors.remove(neighbor);
    }
    
    public Continent getContinent() {
        return continent;
    }
}