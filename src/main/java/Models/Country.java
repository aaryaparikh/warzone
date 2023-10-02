package Models;
import java.util.ArrayList;
import java.util.List;

public class Country {
    private int d_countryId;
    private int d_continentId;
    private ArrayList <Integer> d_neighborCountries=new ArrayList<>();	
	
    public Country(int p_countryId,int p_continentId){
        this.d_countryId = p_countryId;
        d_continentId=p_continentId;
    }
    
    public int getCountryId(){
        return d_countryId;
    }
    
    public int getContinentId(){
        return d_continentId;
    }
    
    public void addNeighbor(int p_neighborCountryId)
    {
        d_neighborCountries.add(p_neighborCountryId);
    }
    
    public void removeNeighbor(int p_neighborCountryId)
    {
        d_neighborCountries.remove(p_neighborCountryId);
    }
    
    public ArrayList<Integer> getNeighborCountries(){
        return d_neighborCountries;
    }    
    
    
    
    private String name;
    private int armies;
    private Player owner;
    private List<Country> neighbors;
    private Continent continent;

    public String d_countryIdString;
    public int getD_countryId() {
		return d_countryId;
	}

	public void setD_countryId(int d_countryId) {
		this.d_countryId = d_countryId;
	}

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