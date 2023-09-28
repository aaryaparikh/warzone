import java.util.*;
public class Country{
    public int d_countryId;
    public int d_continentId;
    public ArrayList <Integer> d_neighborCountries=new ArrayList<>();
    String d_countryName;
    Country(int p_countryId,int p_continentId,String p_countryName){
        this.d_countryId = p_countryId;
        d_continentId=p_continentId;
        d_countryName=p_countryName;
    }
}