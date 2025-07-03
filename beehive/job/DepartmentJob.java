package beehive.job;

import beehive.department.Department;
import beehive.resource.Resource;

public class DepartmentJob extends Job{
	private Resource resource;
	private ResourceAdjustStrategy resourceAdjustStrategy;
	private Department department;

	public DepartmentJob(Resource resource, ResourceAdjustStrategy resourceAdjustStrategy, Department department,
						 double foodCostConstant, double heatConstant, double productionConstant){
		this.resource = resource;
		this.resourceAdjustStrategy = resourceAdjustStrategy;
		this.department = department;
		modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
	}

	protected void workOverride(){
		int amountResourceProduced = calcProduction();

		resourceAdjustStrategy.execute(resource, amountResourceProduced);
	}
	public int calcProduction(){
		return (int)(department.getNumBees() * modifiers.calcProdMultiplier());
	}
	public int calcFoodCost(){
		return (int)(department.getNumBees() * modifiers.calcFoodMultiplier());
	}
	public double calcHeat(){
		return department.getNumBees() * modifiers.calcHeatMultiplier();
	}
}