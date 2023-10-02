package Models;
import java.util.ArrayList;
import java.util.List;

public class Country {
    private int d_countryId;
    private int d_continentId;
    private int d_armies;
    private Player d_owner;

    private ArrayList <Integer> d_neighborCountries=new ArrayList<Integer>();	
	
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
    
    public int getArmies() {
		return d_armies;
	}

	public void setArmies(int d_armies) {
		this.d_armies = d_armies;
	}

	public Player getOwner() {
		return d_owner;
	}

	public void setOwner(Player d_owner) {
		this.d_owner = d_owner;
	}

    public void addArmies(int numArmies) {
    	this.d_armies += numArmies;
    }

	public void subtractArmies(int d_armies) {
		this.d_armies -= d_armies;
		
	}

}