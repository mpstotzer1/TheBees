package beehive.world;

import beehive.MiscData;

import java.util.Random;

public class Season{
    private MiscData miscData;
    private SeasonType seasonType;
    private int seasonCounter;
    private double currentTemp;

    public Season(MiscData miscData){
        this.miscData = miscData;
        seasonType = SeasonType.SPRING;
        seasonCounter = miscData.seasonLength();
        currentTemp = calcAverageTemp();
    }

    public void update(){
        nudgeCurrentTemp();
        seasonCounter--;
        if(seasonCounter <= 0){
            seasonType = getNextSeason();
            seasonCounter = miscData.seasonLength();
        }
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
    private SeasonType getNextSeason(){
        return switch(seasonType){
            case SPRING -> SeasonType.SUMMER;
            case SUMMER -> SeasonType.FALL;
            case FALL -> SeasonType.WINTER;
            case WINTER -> SeasonType.SPRING;
        };
    }

    public SeasonType getSeasonType(){ return seasonType; }
    //public void setSeasonType(SeasonType seasonType){ this.seasonType = seasonType; }
    public double getCurrentTemp() { return currentTemp; }
    public int getSeasonCounter(){ return seasonCounter; }
}
