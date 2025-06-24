package beehive.world;

public class WorldInfo {
    private Season season;
    private Weather weather;


    public WorldInfo(Season season){
        this.season = season;
    }

    public void update(){
        season.update();
        weather.update();
    }

    public double getWorldTemp(){
        return season.getCurrentTemp() + weather.getAdditionalTemperature();
    }
    public Season getSeason(){
        return season;
    }
}