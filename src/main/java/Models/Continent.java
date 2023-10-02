package Models;

public class Continent {
    private int d_continentId;
    private int d_continentValue;
    
    public Continent(int p_continentId,int p_continentValue){
        this.d_continentId=p_continentId;
        this.d_continentValue=p_continentValue;
    }
    
    public int getContinentId(){
        return d_continentId;
    }
    public int getContinentValue(){
        return d_continentValue;
    }	
	
	
    String d_continentIdString;
    String d_continentName;
    
    public Continent(int p_continentId,int p_continentValue,String p_continentName){
        this.d_continentId=p_continentId;
        this.d_continentValue=p_continentValue;
        this.d_continentName=p_continentName;
    }

	public Continent(String continentID, int p_continentValue) {
        this.d_continentIdString=continentID;
        this.d_continentValue=p_continentValue;
	}

	public Object getContinentName() {
		return this.d_continentName;
	}

	public int getControlValue() {
		return this.d_continentValue;
	}

}