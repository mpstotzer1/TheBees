//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
//Its output can be controlled by modifiers
package beehive.job;

import beehive.resource.Resource;

public class DepartmentJob extends Modifiable{
	private Resource resource;
	private ResourceAdjustStrategy resourceAdjustStrategy;

	public DepartmentJob(Resource resource, ResourceAdjustStrategy resourceAdjustStrategy,
						 double foodCostConstant, double heatConstant, double productionConstant){
		this.resource = resource;
		this.resourceAdjustStrategy = resourceAdjustStrategy;
		modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
	}

	public void produce(int numBees){
		int production = calcProduction(numBees);

		resourceAdjustStrategy.execute(resource, production);
		modifiers.update();
	}
	public int calcProduction(int numBees){
		return (int)(numBees * modifiers.calcProdMultiplier());
	}
	public int getFoodCost(int numBees){
		double multiplier = modifiers.calcFoodMultiplier();

		return (int)(numBees * multiplier);
	}
	public int getHeat(int numBees){
		double multiplier = modifiers.calcHeatMultiplier();

		return (int)(numBees * multiplier);
	}

}