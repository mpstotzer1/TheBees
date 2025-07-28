package beehive.job;

import beehive.Upgrades;
import beehive.department.DepartmentInfo;
import beehive.logger.Logger;
import beehive.temperature.TemperatureInfo;


public class HiveTemperatureRegulator extends Job{
    private TemperatureInfo temperatureInfo;
    private Upgrades upgrades;
    private DepartmentInfo departmentInfo;

    public HiveTemperatureRegulator(TemperatureInfo temperatureInfo, Upgrades upgrades, DepartmentInfo departmentInfo,
                                    double productionConstant, double foodCostConstant, double heatConstant){
        super(productionConstant, foodCostConstant, heatConstant);

        this.temperatureInfo = temperatureInfo;
        this.upgrades = upgrades;
        this.departmentInfo = departmentInfo;
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
        int totalBees = departmentInfo.getTotalBees();
        double step = temperatureInfo.getTempRegulationStepCnst() * prodMods.calcMultiplier() * totalBees;

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
        int foodCost = (int)(tempAdjustmentAbsolute * modCnst);

        Logger.productionDebugging ("Food Cost for Temp Regulation: " + foodCost);
        return foodCost;
    }

    public double calcHeat(){
        double tempAdjustmentAbsolute = Math.abs(calcTempAdjustment());
        double heatMod = heatMods.calcMultiplier();

        return tempAdjustmentAbsolute * heatMod;
    }
}
