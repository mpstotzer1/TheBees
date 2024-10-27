//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
public class Job {
	private int prodConst;
	private int foodConst;
	private int heatConst;
	private Department department;
	
	//Constructor
	public Job(Department dept){
		department = dept;
	}
	public Job(int pc, int fc, int hc, Department dept){
		prodConst = pc;
		foodConst = fc;
		heatConst = hc;
		department = dept;
	}
	
	//Return how much the accomplishing the job will produce, eat, and heat up the hive
	public int getResource(){
		return department.getNumBees()*prodConst;
	}
	public int getFoodCost(){
		return department.getNumBees()*foodConst;
	}
	public int getHeat(){
		return department.getNumBees()*heatConst;
	}
	
	//Getters
	public int getProdConst(){ return prodConst; }
	public int getfoodConst(){ return foodConst; }
	public int getheatConst(){ return heatConst; }
	//Setters
	public void setProdConst(int p){ prodConst = p; }
	public void setFoodConst(int c){ foodConst = c; }
	public void setHeatConst(int h){ heatConst = h; }
}
