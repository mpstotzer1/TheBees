//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
//Its output can be controlled by modifiers
package beehive.job;

import java.util.ArrayList;
import java.util.Iterator;

import beehive.resource.Resource;
import beehive.event.EventModifier;

public class DepartmentJob extends Job {
    protected Resource resource;
    protected ResourceAdjustStrategy resourceAdjustStrategy;

    public DepartmentJob(Resource resource, ResourceAdjustStrategy resourceAdjustStrategy,
               double foodCostConstant, double heatCostConstant){
        this.resource = resource;
        this.resourceAdjustStrategy = resourceAdjustStrategy;
        this.foodCostConstant = foodCostConstant;
        this.heatCostConstant = heatCostConstant;
    }

    //Useful Methods
    public int getFoodCost(int numBees){
        double multiplier = calcListMultiplier(foodMod);
        return (int)(numBees * multiplier * foodCostConstant);
    }
    public int getHeat(int numBees){
        double multiplier = calcListMultiplier(heatMod);
        return (int)(numBees * multiplier * heatCostConstant);
    }
    public void produce(int numBees){
        int production = calcProduction(numBees);
        resourceAdjustStrategy.execute(this, production);
    }
    private int calcProduction(int numBees){
        double multiplier = calcListMultiplier(prodMod);
        return (int) (numBees * multiplier);
    }

    public Resource getResource(){ return resource; }
}
