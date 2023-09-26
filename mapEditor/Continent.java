import java.util.*;
public class Continent{
    int d_continentId,d_continentValue;
    public ArrayList<Country> d_countries; 
    Continent(int p_continentId,int p_continentValue){
        this.d_continentId=p_continentId;
        this.d_continentValue=p_continentValue;
    }
    void removeContinent()
    {
        this.d_continentId=0;
        this.d_continentValue=0;
    }

}
