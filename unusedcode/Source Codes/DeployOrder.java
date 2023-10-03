package comp691.project.map;

class DeployOrder extends Order {
    private Country country;
    private int numArmies;

    public DeployOrder(Country country, int numArmies) {
        this.country = country;
        this.numArmies = numArmies;
    }

    public Country getCountry() {
        return country;
    }

    public int getNumArmies() {
        return numArmies;
    }

    @Override
    void execute() {
        // Execute deploy order
    }
}
