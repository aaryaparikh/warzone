package comp691.project.map;

class Continent {
    private String name;
    private int controlValue;

    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
    }

    public String getName() {
        return name;
    }

    public int getControlValue() {
        return controlValue;
    }
}