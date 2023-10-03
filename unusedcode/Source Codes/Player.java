package comp691.project.map;

import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private List<Country> countries;
    private List<Order> orders;
    private int reinforcementPool;

    public Player(String name) {
        this.name = name;
        this.countries = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.reinforcementPool = 0;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void issueOrder(Order order) {
        orders.add(order);
    }

    public Order nextOrder() {
        if (!orders.isEmpty()) {
            Order order = orders.get(0);
            orders.remove(0);
            return order;
        }
        return null;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void assignReinforcements(int numReinforcements) {
        reinforcementPool += numReinforcements;
    }

    public void executeDeployOrder(DeployOrder deployOrder) {
        Country country = deployOrder.getCountry();
        int numArmies = deployOrder.getNumArmies();
        if (countries.contains(country) && reinforcementPool >= numArmies) {
            country.addArmies(numArmies);
            reinforcementPool -= numArmies;
        }
    }
    
    public int getReinforcementPool() {
        return reinforcementPool;
    }
}