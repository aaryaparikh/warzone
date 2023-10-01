package Models;
import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private int armies;
    private Player owner;
    private List<Country> neighbors;
    private Continent continent;

    public String d_countryIdString;
    public int d_countryId;
    public int d_continentId;
    public ArrayList <Integer> d_neighborCountries=new ArrayList<Integer>();
    String d_countryName;
    Country(int p_countryId,int p_continentId,String p_countryName){
        this.d_countryId = p_countryId;
        d_continentId=p_continentId;
        d_countryName=p_countryName;
    }

	public Country(String countryID) {
		this.d_countryIdString = countryID;
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