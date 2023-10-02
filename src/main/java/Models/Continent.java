package Models;

/**
 * Represents a continent in the game map.
 */
public class Continent {
    private int d_continentId;
    private int d_continentValue;
    private String d_continentName;

    /**
     * Constructor for Continent with only ID and value.
     *
     * @param p_continentId    The continent's ID.
     * @param p_continentValue The continent's control value.
     */
    public Continent(int p_continentId, int p_continentValue) {
        this.d_continentId = p_continentId;
        this.d_continentValue = p_continentValue;
    }

    /**
     * Constructor for Continent with ID, value, and name.
     *
     * @param p_continentId    The continent's ID.
     * @param p_continentValue The continent's control value.
     * @param p_continentName  The continent's name.
     */
    public Continent(int p_continentId, int p_continentValue, String p_continentName) {
        this.d_continentId = p_continentId;
        this.d_continentValue = p_continentValue;
        this.d_continentName = p_continentName;
    }

    /**
     * Get the continent's ID.
     *
     * @return The continent's ID.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Get the continent's control value.
     *
     * @return The continent's control value.
     */
    public int getContinentValue() {
        return d_continentValue;
    }

    /**
     * Get the continent's name.
     *
     * @return The continent's name.
     */
    public String getContinentName() {
        return d_continentName;
    }
}
