//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
//Its output can be controlled by modifiers
package beehive.job;

import beehive.Hive;

public class HiveJob extends Job {
    HiveCommand hiveCommand;

    public HiveJob(HiveCommand hiveCommand, double foodCostConstant, double heatCostConstant){
        this.hiveCommand = hiveCommand;
        this.foodCostConstant = foodCostConstant;
        this.heatCostConstant = heatCostConstant;
    }

    //Useful Methods
    public int getFoodCost(){
        double multiplier = calcListMultiplier(foodMod);
        return (int)(numBees * multiplier * foodCostConstant);
    }
    public int getHeat(){
        double multiplier = calcListMultiplier(heatMod);
        return (int)(numBees * multiplier * heatCostConstant);
    }
    public void work(Hive hive){
        double multiplier = calcListMultiplier(prodMod);
        hiveCommand.execute(hive, multiplier);
    }

}