//Departments are a group of bees that perform one or more jobs
import java.util.ArrayList;

public class Department {
	private int numBees;
	private ArrayList<Job> jobs;
	
	//Constructors
	public Department(Job job){
		numBees=0;
		jobs.add(job);
	}
	public Department(Job job1, Job job2){
		numBees=0;
		jobs.add(job1);
		jobs.add(job2);
	}
	public Department(ArrayList<Job> jobList){
		numBees=0;
		for(int i=0; i < jobList.size(); i++){
			jobs.add(jobList.get(i));
		}
	}
	
	//Useful methods for getting the foodCost and heat per bee in department (averages of jobs)
	public int getFoodCost(){
		int foodCost = 0;
		for(int i=0; i < jobs.size(); i++){
			foodCost += jobs.get(i).getFoodCost();
		}
		foodCost /= jobs.size();
		return foodCost;
	}
	public int getHeat(){
		int heatGenerated = 0;
		for(int i=0; i < jobs.size(); i++){
			heatGenerated += jobs.get(i).getHeat();
		}
		heatGenerated /= jobs.size();
		return heatGenerated;
	}
	
	//Getters
	public int getNumBees(){ return numBees; }
	//Setters
	public void setNumBees(int n){ numBees = n; }
	public void addBees(int n){ numBees += n; }
	public void subBees(int n){ numBees -= n; }
}