package Models;

class GameContinent {
    private int name;
    private int controlId;

    public GameContinent(int continentName, int controlId2) {
        this.name = continentName;
        this.controlId = controlId2;
    }

    public int getName() {
        return name;
    }

    public int getControlId() {
        return controlId;
    }
}
