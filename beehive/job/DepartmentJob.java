package beehive.job;

import beehive.department.Department;
import beehive.resource.Resource;

public class DepartmentJob extends Job{
	private Resource resource;
	private ResourceAdjustStrategy resourceAdjustStrategy;
	private Department department;

	public DepartmentJob(Resource resource, ResourceAdjustStrategy resourceAdjustStrategy, Department department,
						 double foodCostConstant, double heatConstant, double productionConstant){
		super(foodCostConstant, heatConstant, productionConstant);

		this.resource = resource;
		this.resourceAdjustStrategy = resourceAdjustStrategy;
		this.department = department;
	}

	protected void workOverride(){
		int amountResourceProduced = calcProduction();

		resourceAdjustStrategy.execute(resource, amountResourceProduced);
	}
	public int calcProduction(){
		return (int)(department.getNumBees() * prodMods.calcMultiplier());
	}
	public int calcFoodCost(){
		return (int)(department.getNumBees() * foodMods.calcMultiplier());
	}
	public double calcHeat(){
		return department.getNumBees() * heatMods.calcMultiplier();
	}
}