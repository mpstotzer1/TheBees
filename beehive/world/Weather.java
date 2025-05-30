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
        switch(weatherType){
            case FRIGID:
                return -10.0;
            case COLD:
                return -5.0;
            case MILD:
                return 0.0;
            case HOT:
                return 5.0;
            case SCORCHING:
                return 10.0;
        }

        return 0.0;
    }
}
