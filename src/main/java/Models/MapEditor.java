package Models;
import java.util.*;
import java.io.*;

public class MapEditor {
    ArrayList <Continent> d_continents=new ArrayList<Continent>();
    ArrayList <Country> d_countries=new ArrayList<Country>();
	int d_continentCount=0;
    int d_countryCount=0;
    
    public ArrayList<Country> getCountries() {
		return d_countries;
	}
    
	public void setCountries(ArrayList<Country> d_countries) {
		this.d_countries = d_countries;
	}

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
        }        
    }
    public void removeNeighbor(int p_countryId,int p_neighborCountryId){
        for(int l_i=0;l_i<d_countryCount;l_i++){
            if(d_countries.get(l_i).getCountryId()==p_countryId){
                d_countries.get(l_i).removeNeighbor(p_neighborCountryId);;
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
        validateMap();
    }
    public void write(String p_filename){
        if(!validateMap())
        {
            return;
        }
        try{
            String l_text;
            l_text="[Continents]\n";
            for(int l_i=0;l_i<d_continentCount;l_i++)
            {
                l_text=l_text+d_continents.get(l_i).getContinentId()+" "+d_continents.get(l_i).getContinentValue()+"\n";
            }
            l_text=l_text+"\n[Countries]\n";
            for(int l_i=0;l_i<d_countryCount;l_i++)
            {
                l_text=l_text+d_countries.get(l_i).getCountryId()+" "+d_countries.get(l_i).getContinentId()+"\n";
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
    public MapEditor loadMap(String p_fileName)
    {
        String l_text="";
        try{
            File l_mapFile = new File("maps/"+p_fileName+".txt");
            Scanner l_reader = new Scanner(l_mapFile);
            while(l_reader.hasNextLine())
            {
                l_text+=l_reader.nextLine();
                l_text+="\n";
            }
            l_reader.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        String l_continents[]=l_text.split("\n\n")[0].split("\n");
        String l_countries[]=l_text.split("\n\n")[1].split("\n");
        String l_neigbors[]=l_text.split("\n\n")[2].split("\n");
        MapEditor l_map=new MapEditor();
        for(int l_i=1;l_i<l_continents.length;l_i++)
        {
            int l_continentId=Integer.parseInt(l_continents[l_i].split(" ")[0]);
            int l_continentValue=Integer.parseInt(l_continents[l_i].split(" ")[1]);
            l_map.addContinent(l_continentId, l_continentValue);
        }
        for(int l_i=1;l_i<l_countries.length;l_i++)
        {
            int l_countryId=Integer.parseInt(l_countries[l_i].split(" ")[0]);
            int l_continentId=Integer.parseInt(l_countries[l_i].split(" ")[1]);
            String l_neighborsOfCountry[]=l_neigbors[l_i].split(" ");
            l_map.addCountry(l_countryId, l_continentId);
            for(int l_j=1;l_j<l_neighborsOfCountry.length;l_j++)
            {
                int l_neighbor=Integer.parseInt(l_neighborsOfCountry[l_j]);
                l_map.addNeighbor(l_countryId, l_neighbor);
            }
        }
        return l_map;
    }
    public boolean validateMap(){
        HashMap <Integer,Integer> l_counter = new HashMap<>();
        for(int l_i=0;l_i<d_continentCount;l_i++)
        {
            if(!l_counter.containsKey(d_continents.get(l_i).getContinentId()))
            {
                l_counter.put(d_continents.get(l_i).getContinentId(), 1);
            }
            else{
                System.out.println("Invalid Map: Duplicate Continents: "+d_continents.get(l_i).getContinentId());
                return false;
            }
            if(d_continents.get(l_i).getContinentValue()<=0)
            {
                System.out.println("Invalid Continent Value for Continent ID: "+d_continents.get(l_i).getContinentId());
                return false;
            }
        }
        l_counter.clear();
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            if(!l_counter.containsKey(d_countries.get(l_i).getCountryId()))
            {
                l_counter.put(d_countries.get(l_i).getContinentId(), 1);
            }
            else{
                System.out.println("Invalid Map: Duplicate Countries: "+d_countries.get(l_i).getCountryId());
                return false;
            }
            if(d_countries.get(l_i).getNeighborCountries().isEmpty())
            {
                System.out.println("Invalid Map: Unreachable Country: No Neighbouring countries assigned to "+d_countries.get(l_i).getCountryId());
                return false;
            }
            int l_check=0;
            for(int l_j=0;l_j<d_continentCount;l_j++)
            {
                if(d_countries.get(l_i).getContinentId()==d_continents.get(l_j).getContinentId())
                {
                    l_check=1;
                    break;
                }
            }
            if(l_check==0)
            {
                System.out.println("Invalid Map: Country Assigned to a Continent that does not exist");
                break;
            }
            for(Integer l_neighbors:d_countries.get(l_i).getNeighborCountries())
            {
                if(l_neighbors==d_countries.get(l_i).getCountryId())
                {
                    System.out.println("Invalid Map: Country cannot be a neighbor of itself. Country ID: "+l_neighbors);
                    return false;
                }
                int l_neighborCounter=0;
                for(Integer l_checker:d_countries.get(l_i).getNeighborCountries())
                {
                    if(l_checker==l_neighbors)
                    {
                        l_neighborCounter++;
                    }
                }
                if(l_neighborCounter>1)
                {
                    System.out.println("Invalid Map: Duplicate Neighbors for Country: "+d_countries.get(l_i).getCountryId());
                    return false;
                }
                int l_checker=0;
                for(int l_j=0;l_j<d_countryCount;l_j++)
                {
                    if(d_countries.get(l_j).getCountryId()==l_neighbors)
                    {
                        l_checker=1;
                    }
                }
                if(l_checker==0)
                {
                    System.out.println("Invalid Map: Neighboring country does not exist: "+l_neighbors);
                    return false;
                }
            }
            int l_checker=0;
            for(int l_j=0;l_j<d_continentCount;l_j++)
            {
                if(d_countries.get(l_i).getContinentId()==d_continents.get(l_j).getContinentId())
                {
                    l_checker=1;
                }
            }
            if(l_checker==0)
            {
                System.out.println("Invalid Map: Country assigned to a continent that does not exist: "+d_countries.get(l_i));
                return false;
            }
        }
        l_counter.clear();
        
        return true;
    }
}