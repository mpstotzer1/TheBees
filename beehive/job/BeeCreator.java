package beehive.job;

import beehive.logger.Logger;
import beehive.MiscData;
import beehive.department.DepartmentInfo;
import beehive.resource.Resources;
import beehive.temperature.TemperatureInfo;
import beehive.temperature.TemperatureRegulationRanges;

public class BeeCreator extends Job{
    private Resources resources;
    private TemperatureInfo temperatureInfo;
    private MiscData miscData;
    private DepartmentInfo departmentInfo;

    public BeeCreator(Resources resources, TemperatureInfo temperatureInfo, MiscData miscData, DepartmentInfo departmentInfo,
                      double foodCostConstant, double heatConstant, double productionConstant) {
        this.resources = resources;
        this.temperatureInfo = temperatureInfo;
        this.miscData = miscData;
        this.departmentInfo = departmentInfo;

        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    protected void workOverride(){
        if(insideBroodTempRange()){
            int beesToAdd = calcNumBeesToAdd();
            departmentInfo.adjustBeesEverywhere(beesToAdd); //DEBUG
            //hive.addBeesToCluster(beesToAdd);  //DEBUG

            int pollenCost = simpleRound(beesToAdd / modifiers.calcProdMultiplier());
            resources.pollen().sub(pollenCost);
        }
    }
    private boolean insideBroodTempRange(){
        double hiveTemp = temperatureInfo.getHiveTemp();
        double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
        double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

        boolean insideBroodRange = (minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp);
        Logger.logTemperatureDebugging("Inside Brood Range: " + insideBroodRange);

        return insideBroodRange;
    }
    private int calcNumBeesToAdd(){
        int maxBeesPossibleGivenPollen = (int)(resources.pollen().getAmount() * modifiers.calcProdMultiplier());
        int maxBeesProducedPerTick = miscData.maxNumBeesProducedPerTick();

        return Math.min(maxBeesPossibleGivenPollen, maxBeesProducedPerTick);
    }
    private int simpleRound(double roundee){
        roundee += .5;

        return (int)(roundee);
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
