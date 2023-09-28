import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class MapEditor {
    ArrayList <Continent> d_continents=new ArrayList<>();
    ArrayList <Country> d_countries=new ArrayList<>();
    int d_continentCount=0,d_countryCount=0;

    public void addContinent(int p_continentId,int p_continentValue,String p_countryName){
        d_continents.add(new Continent(p_continentId, p_continentValue,p_countryName));
        d_continentCount++;
        d_continents.sort((o1,o2)->o1.d_continentId-o2.d_continentId);
    }
    public void removeContinent(int p_continentId)
    {
        for(int l_i=0;l_i<d_continentCount;l_i++)
        {
            if(d_continents.get(l_i).d_continentId==p_continentId)
            {
               d_continents.remove(l_i);
               d_continentCount--;
               break; 
            }
        }
        
    }
    public void addCountry(int p_countryId,int p_continentId,String p_countryName){
        d_countries.add(new Country(p_countryId, p_continentId,p_countryName));
        d_countries.sort((o1,o2)->o1.d_countryId-o2.d_countryId);
        d_countryCount++;
        
    }
    public void removeCountry(int p_countryId)
    {
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            if(d_countries.get(l_i).d_countryId==p_countryId)
            {
                d_countries.remove(l_i);
                d_countryCount--;
                break;
            }
        }
        
    }
    public void addNeighbor(int p_countryId,int p_neighborCountryId){
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            if(d_countries.get(l_i).d_countryId==p_countryId)
            {
                d_countries.get(l_i).d_neighborCountries.add(p_neighborCountryId);
            }
            if(d_countries.get(l_i).d_countryId==p_neighborCountryId)
            {
                d_countries.get(l_i).d_neighborCountries.add(p_countryId);
            }
        }        
        
    }
    public void removeNeighbor(int p_countryId,int p_neighborCountryId)
    {
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            if(d_countries.get(l_i).d_countryId==p_countryId)
            {
                d_countries.get(l_i).d_neighborCountries.remove(p_neighborCountryId);
            }
            if(d_countries.get(l_i).d_countryId==p_neighborCountryId)
            {
                d_countries.get(l_i).d_neighborCountries.remove(p_countryId);
            }
        } 
    }
    public void showMap(){
        System.out.println("Continents");
        for(int l_i=0;l_i<d_continentCount;l_i++)
        {
            System.out.println(d_continents.get(l_i).d_continentName+" "+d_continents.get(l_i).d_continentId);
        }
        System.out.println("Countries");
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            System.out.println(d_countries.get(l_i).d_countryId+" "+d_countries.get(l_i).d_countryName+" "+d_countries.get(l_i).d_continentId);
        }
        System.out.println("Borders");
        for(int l_i=0;l_i<d_countryCount;l_i++)
        {
            System.out.println(d_countries.get(l_i).d_countryId+" "+d_countries.get(l_i).d_neighborCountries);
        }
    }
    public void write(String p_filename){
        try{
            String l_text;
            l_text="[Continents]\n";
            for(int l_i=0;l_i<d_continentCount;l_i++)
            {
                l_text=l_text+d_continents.get(l_i).d_continentName+" "+d_continents.get(l_i).d_continentId+"\n";
            }
            l_text=l_text+"[Countries]\n";
            for(int l_i=0;l_i<d_countryCount;l_i++)
            {
                l_text=l_text+d_countries.get(l_i).d_countryId+" "+d_countries.get(l_i).d_countryName+" "+d_countries.get(l_i).d_continentId+"\n";
            }
            l_text=l_text+"[Borders]\n";
            for(int l_i=0;l_i<d_countryCount;l_i++)
            {
                l_text=l_text+d_countries.get(l_i).d_countryId;
                for(int l_j=0;l_j<d_countries.get(l_i).d_neighborCountries.size();l_j++)
                {
                    l_text+=" "+d_countries.get(l_i).d_neighborCountries.get(l_j);
                }
                l_text+="\n";
            }
            FileWriter l_writer=new FileWriter("maps/"+p_filename+".txt");
            l_writer.write(l_text);
            l_writer.close();
            System.out.println("File Saved Successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String... args)
    {
        
        Scanner sc=new Scanner(System.in);
        MapEditor map=new MapEditor();
        while(true){
            
            String l_temp;
            l_temp=sc.nextLine();
            String l_commands[]=l_temp.split(" ");
            if(l_commands[0].equals("editcontinent"))
            {
                
                if(l_commands[1].equals("-add"))
                {
                    map.addContinent(Integer.parseInt(l_commands[2]), Integer.parseInt(l_commands[3]), l_commands[4]);
                }
                else if(l_commands[1].equals("-remove")){
                    map.removeContinent(Integer.parseInt(l_commands[2]));
                }
            }
            else if(l_commands[0].equals("editcountry"))
            {
                if(l_commands[1].equals("-add"))
                {
                    map.addCountry(Integer.parseInt(l_commands[2]), Integer.parseInt(l_commands[3]), l_commands[4]);
                }
                else if(l_commands[1].equals("-remove"))
                {
                    map.removeCountry(Integer.parseInt(l_commands[2]));
                }
            }
            else if(l_commands[0].equals("editneighbor"))
            {
                if(l_commands[1].equals("-add"))
                {
                    map.addNeighbor(Integer.parseInt(l_commands[2]),Integer.parseInt(l_commands[3]));
                }
                else if(l_commands[1].equals("-remove"))
                {
                    map.removeNeighbor(Integer.parseInt(l_commands[2]), Integer.parseInt(l_commands[3]));
                }
            }
            else if(l_commands[0].equals("showmap"))
            {
                map.showMap();
            }
            else if(l_commands[0].equals("savemap"))
            {
                map.write(l_commands[1]);
            }
            System.out.println("More Commands?(y/n): ");
            String l_temp2;
            l_temp2=sc.nextLine();
            if(l_temp2.equals("n"))
            {
                break;
            }
        }
        sc.close();
    }
}