public class Continent{
    private int d_continentId,d_continentValue;
    Continent(int p_continentId,int p_continentValue){
        this.d_continentId=p_continentId;
        this.d_continentValue=p_continentValue;
    }
    public int getContinentId(){
        return d_continentId;
    }
    public int getContinentValue(){
        return d_continentValue;
    }
}
