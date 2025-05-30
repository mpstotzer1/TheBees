package beehive.department;

//Departments are a group of bees that perform one or more jobs
import beehive.job.Job;

import java.util.ArrayList;

public class Department {
	private int numBees;
	private ArrayList<Job> jobs = new ArrayList<Job>();
	
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
	
	//Useful methods for getting the foodCost and heat per bee in department (sum of its jobs)
	public int getTotalFoodCost(){
		int foodCost = 0;
		for(int i=0; i < jobs.size(); i++){
			foodCost += jobs.get(i).getFoodCost(numBees);
		}
		foodCost /= jobs.size();
		return foodCost;
	}
	public int getTotalHeat(){
		int heatGenerated = 0;
		for(int i=0; i < jobs.size(); i++){
			heatGenerated += jobs.get(i).getHeat(numBees);
		}
		heatGenerated /= jobs.size();
		return heatGenerated;
	}
	
	
	public void produce(){
		for(int i=0; i < jobs.size(); i++){
			jobs.get(i).produce(numBees);
		}
	}
	
	//Getters
	public int getNumBees(){ return numBees; }
	//Setters
	public void setNumBees(int n){ numBees = n; }
	public void addBees(int n){ numBees += n; }
	public void subBees(int n){ numBees -= n; }
}