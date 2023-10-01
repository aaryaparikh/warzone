import java.util.*;
import java.io.FileWriter;
public class MapEditor {
    ArrayList <Continent> d_continents=new ArrayList<>();
    ArrayList <Country> d_countries=new ArrayList<>();
    int d_continentCount=0;
    int d_countryCount=0;

    public void addContinent(int p_continentId,int p_continentValue){
        d_continents.add(new Continent(p_continentId, p_continentValue));
        d_continentCount++;
        d_continents.sort((o1,o2)->o1.getContinentId()-o2.getContinentId()); // Explaination
    }
    public void removeContinent(int p_continentId){
        for(int l_i=0;l_i<d_continentCount;l_i++){
            if(d_continents.get(l_i).getContinentId()==p_continentId){
               d_continents.remove(l_i);
               d_continentCount--;
               break; 
            }
        }   
    }
    public void addCountry(int p_countryId,int p_continentId){
        d_countries.add(new Country(p_countryId, p_continentId));
        d_countries.sort((o1,o2)->o1.getCountryId()-o2.getCountryId());
        d_countryCount++;
    }
    public void removeCountry(int p_countryId){
        for(int l_i=0;l_i<d_countryCount;l_i++){
            if(d_countries.get(l_i).getCountryId()==p_countryId){
                d_countries.remove(l_i);
                d_countryCount--;
                break;
            }
        }
    }
    public void addNeighbor(int p_countryId,int p_neighborCountryId){
        for(int l_i=0;l_i<d_countryCount;l_i++){
            if(d_countries.get(l_i).getCountryId()==p_countryId){
                d_countries.get(l_i).addNeighbor(p_neighborCountryId);;
            }
            if(d_countries.get(l_i).getCountryId()==p_neighborCountryId){
                d_countries.get(l_i).addNeighbor(p_countryId);;
            }
        }        
    }
    public void removeNeighbor(int p_countryId,int p_neighborCountryId){
        for(int l_i=0;l_i<d_countryCount;l_i++){
            if(d_countries.get(l_i).getCountryId()==p_countryId){
                d_countries.get(l_i).removeNeighbor(p_neighborCountryId);;
            }
            if(d_countries.get(l_i).getCountryId()==p_neighborCountryId){
                d_countries.get(l_i).removeNeighbor(p_countryId);;
            }
        } 
    }
    public void showMap(){
        System.out.println("[Continents]");
        for(int l_i=0;l_i<d_continentCount;l_i++)
        {
            System.out.println(d_continents.get(l_i).getContinentId()+" "+d_continents.get(l_i).getContinentValue());
        }
        System.out.println("[Countries]");
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            System.out.println(d_countries.get(l_i).getCountryId()+" "+d_countries.get(l_i).getContinentId());
        }
        System.out.println("[Borders]");
        for(int l_i=0;l_i<d_countryCount;l_i++){
            System.out.println(d_countries.get(l_i).getCountryId()+" "+d_countries.get(l_i).getNeighborCountries());
        }
    }
    public void write(String p_filename){
        try{
            String l_text;
            l_text="[Continents]\n";
            for(int l_i=0;l_i<d_continentCount;l_i++)
            {
                l_text=l_text+d_continents.get(l_i).getContinentId()+" "+d_continents.get(l_i).getContinentValue()+"\n";
            }
            l_text=l_text+"[Countries]\n";
            for(int l_i=0;l_i<d_countryCount;l_i++)
            {
                l_text=l_text+d_countries.get(l_i).getCountryId()+d_countries.get(l_i).getContinentId()+"\n";
            }
            l_text=l_text+"\n[Borders]\n";
            for(int l_i=0;l_i<d_countryCount;l_i++){
                l_text=l_text+d_countries.get(l_i).getCountryId();
                for(int l_j=0;l_j<d_countries.get(l_i).getNeighborCountries().size();l_j++)
                {
                    l_text+=" "+d_countries.get(l_i).getNeighborCountries().get(l_j);
                }
                l_text+="\n";
            }
            FileWriter l_writer=new FileWriter("maps/"+p_filename+".txt");
            l_writer.write(l_text);
            l_writer.close();
            System.out.println("File Saved Successfully");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
   
}
