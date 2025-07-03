package beehive.job;

import beehive.temperature.TemperatureInfo;

import java.util.HashMap;

public class HiveTemperatureRegulator extends Job{
    private TemperatureInfo temperatureInfo;
    private HashMap<String, Double> upgrades;

    public HiveTemperatureRegulator(TemperatureInfo temperatureInfo, HashMap<String, Double> upgrades, double foodCostConstant, double heatConstant, double productionConstant){
        this.temperatureInfo = temperatureInfo;
        this.upgrades = upgrades;
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    protected void workOverride(){
        double tempAdjustment = calcTempAdjustment();
        temperatureInfo.changeHiveTemp(tempAdjustment);
    }
    private double calcTempAdjustment(){
        if(hiveTooHot()){
            return clampToStep(maxMinusHive());
        }else if(hiveTooCold()){
            return clampToStep(minMinusHive());
        }
        return 0;
    }
    private boolean hiveTooHot(){
        return (temperatureInfo.getHiveTemp() > temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature());
    }
    private boolean hiveTooCold(){
        return (temperatureInfo.getHiveTemp() < temperatureInfo.getTemperatureRegulationRanges().getMinTemperature());
    }
    private double maxMinusHive(){
        double maxTemp = temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature();
        double currentHiveTemp = temperatureInfo.getHiveTemp();

        return (maxTemp - currentHiveTemp);
    }
    private double minMinusHive(){
        double minTemp = temperatureInfo.getTemperatureRegulationRanges().getMinTemperature();
        double currentHiveTemp = temperatureInfo.getHiveTemp();

        return (minTemp - currentHiveTemp);
    }
    private double clampToStep(double tempChange) {
        double step = temperatureInfo.getTempRegulationStep();

        if(Math.abs(tempChange) < step){
            return tempChange;
        }else{
            if(tempChange < 0){ return step * -1; }
            else{ return step; }
        }
    }

    public int calcFoodCost(){
        double tempAdjustmentAbsolute = Math.abs(calcTempAdjustment());

        double foodCost = tempAdjustmentAbsolute / modifiers.calcFoodMultiplier();
        foodCost /= upgrades.get("insulation");

        return (int)(foodCost);
    }

    public double calcHeat(){
        double heatMod = modifiers.calcHeatMultiplier();

        return calcTempAdjustment() * heatMod;
    }
}
