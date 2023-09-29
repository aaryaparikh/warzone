import java.util.*;
public class Country{
    public int d_countryId,d_continentId;
    public ArrayList <Integer> d_neighborCountries=new ArrayList<>();
    
    Country(int p_countryId,int p_continentId){
        this.d_countryId = p_countryId;
        d_continentId=p_continentId;
    }
}