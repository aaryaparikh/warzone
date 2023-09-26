import java.util.*;
public class Continent{
    int d_continentId,d_continentValue;
    public ArrayList<Country> d_countries; 
    Continent(p_continentId,p_continentValue){
        this.d_continentId=p_continentId;
        this.d_continentValue=p_continentValue;
    }
    void removeContinent()
    {
        this.d_continentId=null;
        this.d_continentValue=null;
    }

}
