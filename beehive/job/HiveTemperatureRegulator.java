package beehive.job;

import beehive.Upgrades;
import beehive.logger.Logger;
import beehive.temperature.TemperatureInfo;


public class HiveTemperatureRegulator extends Job{
    private TemperatureInfo temperatureInfo;
    private Upgrades upgrades;

    public HiveTemperatureRegulator(TemperatureInfo temperatureInfo, Upgrades upgrades, double foodCostConstant, double heatConstant, double productionConstant){
        this.temperatureInfo = temperatureInfo;
        this.upgrades = upgrades;

        initializeModifiers(foodCostConstant, heatConstant, productionConstant);
    }

    protected void workOverride(){
        double tempAdjustment = calcTempAdjustment();
        temperatureInfo.changeHiveTemp(tempAdjustment);

        Logger.logTemperatureDebugging("Temperature Regulation Adjustment: " + tempAdjustment);
    }
    private double calcTempAdjustment(){
        if(hiveTooHot()){
            return clampToStep(maxMinusHive());
        }else if(hiveTooCold()){
            return clampToStep(minMinusHive());
        }
        return 0.0;
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
        double step = temperatureInfo.getTempRegulationStep() * prodMods.calcMultiplier();

        if(Math.abs(tempChange) < step){
            return tempChange;
        }else{
            if(tempChange < 0){ return step * -1; }
            else{ return step; }
        }
    }

    public int calcFoodCost(){
        double tempAdjustmentAbsolute = Math.abs(calcTempAdjustment());
        double modCnst = foodMods.calcMultiplier() / upgrades.insulation();

        return (int)(tempAdjustmentAbsolute * modCnst);
    }

    public double calcHeat(){
        double tempAdjustmentAbsolute = Math.abs(calcTempAdjustment());
        double heatMod = heatMods.calcMultiplier();

        return tempAdjustmentAbsolute * heatMod;
    }
}
