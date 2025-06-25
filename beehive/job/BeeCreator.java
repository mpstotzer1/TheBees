package beehive.job;

import beehive.Hive;
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
            hive.addBeesEverywhere(calcNumBeesToAdd()); //DEBUG
            //hive.addBeesToCluster(calcNumBeesToAdd());  DEBUG
            hive.getTemperatureInfo().changeHiveTemp(calcHeat());
            hive.getResources().pollen().setAmount(0);
        }
    }
    private boolean insideBroodTempRange(){
        double hiveTemp = hive.getTemperatureInfo().getHiveTemp();
        double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
        double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

        return (minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp);
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

    //DEBUG
    public int calcNumBeesToAddDebug(Hive hive, int pollen){
        //int pollen = hive.getResources().pollen().getAmount();
        double multiplier = modifiers.calcProdMultiplier();

        return (int)(pollen * multiplier);
    }
}
