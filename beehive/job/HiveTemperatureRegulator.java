package beehive.job;

import beehive.Hive;

public class HiveTemperatureRegulator extends Job{
    private Hive hive;

    public HiveTemperatureRegulator(Hive hive, double foodCostConstant, double heatConstant, double productionConstant){
        this.hive = hive;
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    protected void workOverride(){
        double tempAdjustment = calcTempAdjustment();
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

        if(Math.abs(tempChange) < step){
            return tempChange;
        }else{
            if(tempChange < 0){ return step * -1; }
            else{ return step; }
        }
    }
    private int calcTempRegulationFoodCost(double tempAdjustment){
        double foodCost = Math.abs(tempAdjustment) / modifiers.calcFoodMultiplier();
        foodCost /= hive.getUpgrades().get("insulation");

        return (int)(foodCost);
    }

    public int calcFoodCost(){
        double tempAdjustment = calcTempAdjustment();

        return calcTempRegulationFoodCost(tempAdjustment);
    }
    public double calcHeat(){
        double heatMod = modifiers.calcHeatMultiplier();

        return calcTempAdjustment() * heatMod;
    }
}
