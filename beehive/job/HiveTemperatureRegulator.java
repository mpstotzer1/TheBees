package beehive.job;

import beehive.Hive;

public class HiveTemperatureRegulator extends Modifiable{
    private Hive hive;

    public HiveTemperatureRegulator(double foodCostConstant, double heatConstant, double productionConstant){
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    public void update(Hive hive){
        this.hive = hive;

        regulateHiveTemperature();
        modifiers.update();
    }
    //helper functions
    private void regulateHiveTemperature(){
        double tempAdjustment = calcTempAdjustment() * modifiers.calcProdMultiplier();
        hive.getTemperatureInfo().changeHiveTemp(tempAdjustment);

        int foodCost = calcTempRegulationFoodCost(tempAdjustment);
        hive.subFood(foodCost);
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
        return (hive.getTemperatureInfo().getHiveTemp() > hive.getTemperatureInfo().getTemperatureRegulationRanges().getMaxTemperature());
    }
    private boolean hiveTooCold(){
        return (hive.getTemperatureInfo().getHiveTemp() < hive.getTemperatureInfo().getTemperatureRegulationRanges().getMinTemperature());
    }
    private double maxMinusHive(){
        double maxTemp = hive.getTemperatureInfo().getTemperatureRegulationRanges().getMaxTemperature();
        double currentHiveTemp = hive.getTemperatureInfo().getHiveTemp();

        return (maxTemp - currentHiveTemp);
    }
    private double minMinusHive(){
        double minTemp = hive.getTemperatureInfo().getTemperatureRegulationRanges().getMinTemperature();
        double currentHiveTemp = hive.getTemperatureInfo().getHiveTemp();

        return (minTemp - currentHiveTemp);
    }
    private double clampToStep(double tempChange) {
        double step = hive.getTemperatureInfo().getTempRegulationStep();

        if(tempChange > step){ return step; }
        return tempChange;
    }
    private int calcTempRegulationFoodCost(double tempAdjustment){
        double foodCost = tempAdjustment * modifiers.calcFoodMultiplier();
        foodCost /= hive.getUpgrades().get("insulation");

        return (int)(foodCost);
    }
}
