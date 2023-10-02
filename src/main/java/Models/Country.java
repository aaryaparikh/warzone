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
    public int getD_countryId() {
		return d_countryId;
	}

	public void setD_countryId(int d_countryId) {
		this.d_countryId = d_countryId;
	}

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

	public void subtractArmies(int d_armies) {
		armies -= d_armies;
		
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
    
    public int getContinent() {
        return d_continentId;
    }

	public int getNumberOfArmiesPresent() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setNumberOfArmiesPresent(int i) {
		// TODO Auto-generated method stub
		
	}


}