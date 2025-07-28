package beehive.world;

public enum WeatherType {
    FRIGID(-20.0),
    COLD(-10.0),
    MILD(0.0),
    HOT(10.0),
    SCORCHING(20.0);

    private final double additionalTemp;

    WeatherType(double additionalTemp){
        this.additionalTemp = additionalTemp;
    }

    public double getAdditionalTemp(){ return additionalTemp; }
}
