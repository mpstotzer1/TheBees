package beehive.job;

import beehive.Hive;
import beehive.Logger;
import beehive.temperature.TemperatureRegulationRanges;

public class BeeCreator extends Job{
    private Hive hive;

    public BeeCreator(Hive hive, double foodCostConstant, double heatConstant, double productionConstant) {
        this.hive = hive;
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }


    protected void workOverride(){
        if(insideBroodTempRange()){
            hive.adjustBeesEverywhere(calcNumBeesToAdd()); //DEBUG
            hive.getResources().pollen().setAmount(0);
            Logger.log("Number bees to be produced: " + calcNumBeesToAdd());
            //hive.addBeesToCluster(calcNumBeesToAdd());  DEBUG

            hive.getTemperatureInfo().changeHiveTemp(calcHeat());
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

        double numBees = pollen * multiplier;
        numBees = Math.clamp(numBees, 0.0, hive.getMiscData().maxNumBeesProducedPerTick());

        return (int)(numBees);
    }

    public double calcHeat(){
        int beesProduced = calcNumBeesToAdd();
        double heatMod = modifiers.calcHeatMultiplier();

        return beesProduced * heatMod;
    }
    public int calcFoodCost(){
        int beesProduced = calcNumBeesToAdd();
        double foodMod = modifiers.calcFoodMultiplier();

        return (int)(beesProduced * foodMod);
    }
}
