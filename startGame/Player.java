
import java.util.ArrayList;
import java.util.List;

public class Player {
    public String d_playerName;
    public List<Country> d_countries;
    public List<Order> d_orders;
    public int d_reinforcementPool;

    public Player(String d_playerName) {
        this.d_playerName = d_playerName;
        this.d_countries = new ArrayList<>();
        this.d_orders = new ArrayList<>();
        this.d_reinforcementPool = 0;
    }

    public void addCountry(Country country) {
        d_countries.add(country);
    }

    public void issueOrder(Order order) {
        d_orders.add(order);
    }

    public Order nextOrder() {
        if (!d_orders.isEmpty()) {
            Order order = d_orders.get(0);
            d_orders.remove(0);
            return order;
        }
        return null;
    }

    public List<Country> getCountries() {
        return d_countries;
    }

    public void assignReinforcements(int numReinforcements) {
        d_reinforcementPool += numReinforcements;
    }

    public void executeDeployOrder(DeployOrder deployOrder) {
        Country country = deployOrder.getCountry();
        int numArmies = deployOrder.getNumArmies();
        if (d_countries.contains(country) && d_reinforcementPool >= numArmies) {
            country.addArmies(numArmies);
            d_reinforcementPool -= numArmies;
        }
    }
    
    public int getReinforcementPool() {
        return d_reinforcementPool;
    }
}
