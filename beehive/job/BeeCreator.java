package beehive.job;

import beehive.Hive;
import beehive.Logger;
import beehive.temperature.TemperatureRegulationRanges;

public class BeeCreator extends Modifiable{
    private Hive hive;

    public BeeCreator(double foodCostConstant, double heatConstant, double productionConstant) {
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    public void update(Hive hive){
        this.hive = hive;

        updateNursery();
        modifiers.update();
    }
    private void updateNursery(){
        if(insideBroodTempRange()){
            hive.adjustBeesEverywhere(calcNumBeesToAdd()); //DEBUG
            Logger.log("Number bees to be produced: " + calcNumBeesToAdd());
            //hive.addBeesToCluster(calcNumBeesToAdd());  DEBUG

            hive.getTemperatureInfo().changeHiveTemp(calcHeat());
            hive.getResources().pollen().setAmount(0);
        }
    }
    private boolean insideBroodTempRange(){
        double hiveTemp = hive.getTemperatureInfo().getHiveTemp();
        double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
        double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

        boolean insideBroodRange = (minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp);
        Logger.log("Inside Brood Range: " + insideBroodRange);

        return insideBroodRange;
    }
    private int calcNumBeesToAdd(){
        int pollen = hive.getResources().pollen().getAmount();
        double multiplier = modifiers.calcProdMultiplier();

        return (int)(pollen * multiplier);
    }
    private double calcHeat(){
        int beesProduced = calcNumBeesToAdd();
        double heatMod = modifiers.calcHeatMultiplier();

        return beesProduced * heatMod;
    }
}
