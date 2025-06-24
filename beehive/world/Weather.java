package beehive.world;

import java.util.Random;

public class Weather {
    private WeatherType weatherType;
    private int counter;

    public Weather(){
        weatherType = WeatherType.MILD;
        counter = 0;
    }

    public void update(){
        counter++;
        if(counter >= 20){
            weatherType = randomWeatherType();
            counter = 0;
        }
    }
    private WeatherType randomWeatherType(){
        Random rand = new Random();

        WeatherType[] weatherTypes = WeatherType.values();
        int randomIndex = rand.nextInt(weatherTypes.length);

        return weatherTypes[randomIndex];
    }

    public double getAdditionalTemperature(){
        return weatherType.getAdditionalTemp();
    }
}
