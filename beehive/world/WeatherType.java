package beehive.world;

public enum WeatherType {
    FRIGID(-10.0),
    COLD(-5.0),
    MILD(0.0),
    HOT(5.0),
    SCORCHING(10.0);

    private final double additionalTemp;

    WeatherType(double additionalTemp){
        this.additionalTemp = additionalTemp;
    }

    public double getAdditionalTemp(){ return additionalTemp; }
}
