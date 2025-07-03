package beehive.job;

import beehive.Logger;
import beehive.MiscData;
import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.resource.Resources;
import beehive.temperature.TemperatureInfo;
import beehive.temperature.TemperatureRegulationRanges;

public class BeeCreator extends Job{
    //private Hive hive;
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
            adjustBeesEverywhere(beesToAdd); //DEBUG
            //hive.addBeesToCluster(beesToAdd);  //DEBUG
            Logger.log("Number bees to be produced: " + beesToAdd);

            int pollenCost = simpleRound(beesToAdd / modifiers.calcProdMultiplier());
            resources.pollen().sub(pollenCost);
        }
    }
    private boolean insideBroodTempRange(){
        double hiveTemp = temperatureInfo.getHiveTemp();
        double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
        double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

        boolean insideBroodRange = (minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp);
        Logger.log("Inside Brood Range: " + insideBroodRange);

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
    public void adjustBeesEverywhere(int numBees){
        int numDepartments = departmentInfo.getDepartments().size();
        int remainder = numBees % numDepartments;
        int beesToAddPerDepartment = (numBees - remainder) / numDepartments;

        for(Department dept: departmentInfo.getDepartments()){
            dept.adjustBees(beesToAddPerDepartment);
        }

        for(Department dept : departmentInfo.getDepartments()){
            if(remainder <= 0){ break; }

            dept.adjustBees(1);
            remainder--;
        }
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
