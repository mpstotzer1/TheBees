package beehive.department;

import java.util.ArrayList;
import beehive.job.DepartmentJob;

public class Department {
	private int numBees;
	private ArrayList<DepartmentJob> departmentJobs = new ArrayList<DepartmentJob>();
	
	public Department(DepartmentJob departmentJob){
		numBees=0;
		departmentJobs.add(departmentJob);
	}
	public Department(DepartmentJob departmentJob1, DepartmentJob departmentJob2){
		numBees=0;
		departmentJobs.add(departmentJob1);
		departmentJobs.add(departmentJob2);
	}

	public void produce(){
		for(int i = 0; i < departmentJobs.size(); i++){
			departmentJobs.get(i).produce(numBees);
		}
	}
	public int getTotalFoodCost(){
		int foodCost = 0;
		for(int i = 0; i < departmentJobs.size(); i++){
			foodCost += departmentJobs.get(i).getFoodCost(numBees);
		}

		return foodCost;
	}
	public int getTotalHeat(){
		int heatGenerated = 0;
		for(int i = 0; i < departmentJobs.size(); i++){
			heatGenerated += departmentJobs.get(i).getHeat(numBees);
		}

		return heatGenerated;
	}

	public int getNumBees(){ return numBees; }
	public void transferAllBees(Department department){
		department.adjustBees(numBees);
		numBees = 0;
	}
	public void adjustBees(int n){
		if((numBees + n) < 0){
			numBees = 0;
		}else{
			numBees += n;
		}
	}
	public void killAllBees(){
		numBees = 0;
	}
}