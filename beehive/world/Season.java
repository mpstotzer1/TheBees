package beehive.world;

import java.util.Random;

public class Season{
    private SeasonType seasonType;
    private double seasonLength;
    private double currentTemp;

    public Season(){
        seasonType = SeasonType.SPRING;
        setSeasonLength();
        currentTemp = calcAverageTemp();
    }

    public void update(){
        nudgeCurrentTemp();
        seasonLength--;
        if(seasonLength <= 0){
            moveToNextSeason();
            setSeasonLength();
        }
    }

    private void setSeasonLength(){
        seasonLength = 120;
    }
    private void nudgeCurrentTemp(){
        if(currentTemp <= seasonType.getLowTemp()){
            currentTemp++;
        }else if(currentTemp >= seasonType.getHighTemp()){
            currentTemp--;
        }else{
            currentTemp += calcNudge();
        }
    }
    private double calcNudge(){
        Random rand = new Random();

        if(rand.nextBoolean()){ return 1.0; }
        else{ return -1.0; }
    }
    private double calcAverageTemp(){
        return seasonType.getHighTemp() + seasonType.getLowTemp() / 2.0;
    }
    private void moveToNextSeason(){
        switch(seasonType){
            case SPRING:
                seasonType = SeasonType.SUMMER;
            case SUMMER:
                seasonType = SeasonType.FALL;
            case FALL:
                seasonType = SeasonType.WINTER;
            case WINTER:
                seasonType = SeasonType.SPRING;
        }
    }

    public SeasonType getSeasonType(){ return seasonType; }
    public double getCurrentTemp() { return currentTemp; }
}
