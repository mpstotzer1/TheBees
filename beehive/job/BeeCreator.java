package beehive.job;

import beehive.logger.Logger;
import beehive.MiscData;
import beehive.department.DepartmentInfo;
import beehive.resource.Resource;
import beehive.temperature.TemperatureInfo;
import beehive.temperature.TemperatureRegulationRanges;

public class BeeCreator extends Job{
    private Resource pollen;
    private TemperatureInfo temperatureInfo;
    private MiscData miscData;
    private DepartmentInfo departmentInfo;

    public BeeCreator(Resource pollen, TemperatureInfo temperatureInfo, MiscData miscData, DepartmentInfo departmentInfo,
                      double foodCostConstant, double heatConstant, double productionConstant) {
        super(foodCostConstant, heatConstant, productionConstant);

        this.pollen = pollen;
        this.temperatureInfo = temperatureInfo;
        this.miscData = miscData;
        this.departmentInfo = departmentInfo;
    }

    protected void workOverride(){
        if(insideBroodTempRange()){
            int beesToAdd = calcNumBeesToAdd();
            departmentInfo.adjustBeesEverywhere(beesToAdd); //DEBUG
            //hive.addBeesToCluster(beesToAdd);  //DEBUG

            int pollenCost = simpleRound(beesToAdd / prodMods.calcMultiplier());
            pollen.sub(pollenCost);
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
        int maxBeesPossibleGivenPollen = (int)(pollen.getAmount() * prodMods.calcMultiplier());
        int maxBeesProducedPerTick = miscData.maxNumBeesProducedPerTick();

        return Math.min(maxBeesPossibleGivenPollen, maxBeesProducedPerTick);
    }
    private int simpleRound(double roundee){
        roundee += .5;

        return (int)(roundee);
    }

    public double calcHeat(){
        int beesProduced = calcNumBeesToAdd();
        double heatMod = heatMods.calcMultiplier();

        return beesProduced * heatMod;
    }
    public int calcFoodCost(){
        int beesProduced = calcNumBeesToAdd();
        double foodMod = foodMods.calcMultiplier();

        return (int)(beesProduced * foodMod);
    }
}
