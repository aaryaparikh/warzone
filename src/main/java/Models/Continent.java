package Models;

public class Continent {
    int d_continentId;
    String d_continentIdString;
    int d_continentValue;
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

	public String getControlValue() {
		// TODO Auto-generated method stub
		return null;
	}
}