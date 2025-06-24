package beehive.world;

public enum SeasonType {
    SPRING(50, 70),
    SUMMER(70, 100),
    FALL(40, 60),
    WINTER(20, 50);


    private final double lowTemp;
    private final double highTemp;

    SeasonType(double lowTemp, double highTemp){
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
    }

    public double getLowTemp(){ return lowTemp; }
    public double getHighTemp(){ return highTemp; }
}
