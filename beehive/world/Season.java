package beehive.world;

import java.util.Random;

public class Season{
    private SeasonType seasonType;
    private double seasonLength;
    private double minTemp;
    private double maxTemp;
    private double currentTemp;

    public Season(){
        seasonType = SeasonType.SPRING;
        setSeasonLength();
        setTempRange();
        currentTemp = calcAverageTemp();
    }


    public void update(){
        nudgeCurrentTemp();
        seasonLength--;
        if(seasonLength <= 0){
            moveToNextSeason();
            setTempRange();
            setSeasonLength();
        }
    }

    private void setTempRange(){
        switch(seasonType){
            case SPRING:
                minTemp = 50;
                maxTemp = 70;
            case SUMMER:
                minTemp = 70;
                maxTemp = 100;
            case FALL:
                minTemp = 40;
                maxTemp = 60;
            case WINTER:
                minTemp = 20;
                maxTemp = 50;
        }
    }
    private void nudgeCurrentTemp(){
        if(currentTemp <= minTemp){
            currentTemp++;
        }else if(currentTemp >= maxTemp){
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
        return maxTemp + minTemp / 2.0;
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
    private void setSeasonLength(){
        seasonLength = 300;
    }

    public double getMinTemp() { return minTemp; }
    public double getMaxTemp() { return maxTemp; }
    public double getCurrentTemp() { return currentTemp; }
}
