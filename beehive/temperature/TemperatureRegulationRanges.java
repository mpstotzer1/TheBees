package beehive.temperature;

public enum TemperatureRegulationRanges {
    BROOD(91, 97),
    GENERAL(57, 113),
    OFF(-1000, 1000);

    private final double minTemperature;
    private final double maxTemperature;

    // Constructor for the enum
    TemperatureRegulationRanges(double minTemperature, double maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }
    public double getMaxTemperature() {
        return maxTemperature;
    }
}





//    // You can also add other methods if needed, e.g., to check if a temperature is within the season's range
//    public boolean isTemperatureInSeason(int temperature) {
//        return temperature >= minTemperature && temperature <= maxTemperature;
//    }
//EXAMPLE USAGE OF ENUM VALUES
//    public static void main(String[] args) {
//        // Accessing min and max temperatures
//        System.out.println("Spring Min Temperature: " + Season.SPRING.getMinTemperature());
//        System.out.println("Spring Max Temperature: " + Season.SPRING.getMaxTemperature());
//
//        System.out.println("Summer Min Temperature: " + Season.SUMMER.getMinTemperature());
//        System.out.println("Summer Max Temperature: " + Season.SUMMER.getMaxTemperature());
//
//        // Looping through all seasons and printing their temperatures
//        for (Season season : Season.values()) {
//            System.out.println(season + ": Min=" + season.getMinTemperature() + ", Max=" + season.getMaxTemperature());
//        }
//
//        // Using the helper method
//        int currentTemperature = 18;
//        System.out.println("Is " + currentTemperature + " degrees in Summer? " + Season.SUMMER.isTemperatureInSeason(currentTemperature));
//        System.out.println("Is " + currentTemperature + " degrees in Spring? " + Season.SPRING.isTemperatureInSeason(currentTemperature));
//    }