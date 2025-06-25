package beehive.world;

import java.util.Random;

public class Season{
    private SeasonType seasonType;
    private int seasonLength;
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
        return (seasonType.getHighTemp() + seasonType.getLowTemp()) / 2.0;
    }
    private void moveToNextSeason(){
        switch(seasonType){
            case SPRING:
                this.seasonType = SeasonType.SUMMER;
                return;
            case SUMMER:
                seasonType = SeasonType.FALL;
                return;
            case FALL:
                seasonType = SeasonType.WINTER;
                return;
            case WINTER:
                seasonType = SeasonType.SPRING;
                return;
        }
    }

    public SeasonType getSeasonType(){ return seasonType; }
    public void setSeasonType(SeasonType seasonType){ this.seasonType = seasonType; }
    public double getCurrentTemp() { return currentTemp; }
    public int getDuration(){ return seasonLength; }
}
