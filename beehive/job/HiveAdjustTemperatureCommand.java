package beehive.job;

import beehive.Hive;

public class HiveAdjustTemperatureCommand implements HiveCommand{
    private Hive hive;

    public HiveAdjustTemperatureCommand(){}

    public void execute(Hive hive, double multiplier){
        this.hive = hive;
        updateTemperature();
    }
    //updateTemperature() helper functions
    private void updateTemperature(){
        double deltaHeat = calcProductionHeat() + calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp();
        hive.getTemperatureInfo().changeHiveTemp(deltaHeat);

        regulateHiveTemperature();
    }
    private int calcProductionHeat(){
        int heat = 0;
        for(int i = 0; i < hive.getDepartmentInfo().getDepartments().size(); i++){
            heat += hive.getDepartmentInfo().getDepartments().get(i).getTotalHeat();
        }
        return heat;
    }
    private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
        double deltaTemp = hive.getWorldInfo().getWorldTemp() - hive.getTemperatureInfo().getHiveTemp();
        deltaTemp *= .1;
        return deltaTemp;
    }
    private void regulateHiveTemperature(){
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

        if(tempChange > step){ return step; }
        return tempChange;
    }
    private int calcTempRegulationFoodCost(double tempAdjustment){
        double foodCost = tempAdjustment * hive.getTemperatureInfo().getTempRegulationFoodCostConstant();
        foodCost /= hive.getUpgrades().get("insulation");

        return (int)(foodCost);
    }
}
