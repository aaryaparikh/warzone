import java.util.*;
public class Country{
    private int d_countryId;
    private int d_continentId;
    private ArrayList <Integer> d_neighborCountries=new ArrayList<>();
    
    Country(int p_countryId,int p_continentId){
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
}
