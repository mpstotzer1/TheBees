package beehive.department;

import beehive.logger.Logger;

import java.util.ArrayList;
import java.util.Random;

public class DepartmentInfo {
	private Department nurse;
	private Department forager;
	private Department guard;
	private Department waxMason;
	private Department houseBee;
	private Department fanner;
	private Department cluster;
	private Department drone;
    private ArrayList<Department> allDepartments = new ArrayList<Department>();
	private ArrayList<Department> workerDepartments = new ArrayList<Department>();

    public DepartmentInfo(Department nurse, Department forager, Department guard, Department waxMason,
			Department houseBee, Department fanner, Department cluster, Department drone) {
		this.nurse = nurse;
		this.forager = forager;
		this.guard = guard;
		this.waxMason = waxMason;
		this.houseBee = houseBee;
		this.fanner = fanner;
		this.cluster = cluster;
		this.drone = drone;

		workerDepartments.add(nurse);
		workerDepartments.add(forager);
		workerDepartments.add(guard);
		workerDepartments.add(waxMason);
		workerDepartments.add(houseBee);
		workerDepartments.add(fanner);
		workerDepartments.add(cluster);

		allDepartments.addAll(workerDepartments);
		allDepartments.add(drone);
	}


	private void adjustBeesEverywhere(int numBees, ArrayList<Department> departments){
		Logger.productionDebugging("Adjusted " + numBees);

		//For each department, adjust by (deptNumBess/totalBees  *  totalNumBeesAdjusting) with totalBees NOT being the method call but instead being
		//the original total number BEFORE we started adding/subtracting bees.
		//While doing so, add up the exact integer number of bees that have been killed so far. When you've gone through
		//all the departments, subtract this from the original numBees parameter to get how many bee(s) you've missed.
		//Then, go through a while loop and subtract these remaining fractional bees (it shouldn't be higher than 1?)
		//This method must NEVER subtract more bees from a department than the department has (otherwise not enough bees
		//will be subtracted).
		//Much of the old code below can be recycled :)


//		//First adds/subs equal number of bees from each department, then adds/subs any remaining bees randomly
//		int sign = calcSign(numBees);
//
//		int numDepartments = departments.size();
//		int remainder = Math.abs(numBees) % numDepartments;
//		int beesToAddPerDepartment = (Math.abs(numBees) - remainder) / numDepartments;
//
//		for(Department dept: departments){
//			dept.attemptAdjustBees(sign * beesToAddPerDepartment);
//		}
//
//		Random rand = new Random();
//		while(remainder > 0){
//			int randIndex = rand.nextInt(departments.size());
//			departments.get(randIndex).attemptAdjustBees(sign);
//
//			remainder--;
//		}
	}
	private int calcSign(int number){
		if(number < 0){ return -1;}
		else{ return 1; }
	}
	public void addWorkers(int numWorkers){
		adjustBeesEverywhere(numWorkers, workerDepartments);
	}
	public void killBees(int beesToKill){
		beesToKill *= -1;
		adjustBeesEverywhere(beesToKill, allDepartments);
		Logger.productionDebugging(beesToKill + " bees were killed");
	}
	public void killPercentBees(double percentToKill){
		int total = getTotalBees();
		int beesToKill = (int) (total * .01 * percentToKill);

		killBees(beesToKill);
	}
	public int getTotalBees(){
		int total = 0;
		for(Department dept: allDepartments){
			total += dept.getNumBees();
		}
		return total;
	}

    
    public Department getNurse() { return nurse; }
    public Department getForager() { return forager; }
    public Department getGuard() { return guard; }
    public Department getWaxMason() { return waxMason; }
    public Department getHouseBee() { return houseBee; }
    public Department getFanner() { return fanner; }
    public Department getDrone() { return drone; }
    public Department getCluster() { return cluster; }
}
