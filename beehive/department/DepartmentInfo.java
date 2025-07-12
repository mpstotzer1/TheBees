package beehive.department;

import java.util.ArrayList;

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


	public void adjustBeesEverywhere(int numBees){
		int numDepartments = workerDepartments.size();
		int remainder = numBees % numDepartments;
		int beesToAddPerDepartment = (numBees - remainder) / numDepartments;

		for(Department dept: workerDepartments){
			dept.adjustBees(beesToAddPerDepartment);
		}

		for(Department dept : workerDepartments){
			if(remainder <= 0){ break; }

			dept.adjustBees(1);
			remainder--;
		}
	}
	public void addBeesToDepartment(int beesToAdd, Department destination){
		destination.adjustBees(beesToAdd);
	}
	public void killBees(int beesToKill){
		beesToKill *= -1;
		adjustBeesEverywhere(beesToKill);

		//If you're out of bees, your hive is dead and you lose the game (womp womp womp)
		//Logger.log(beesToKill + " bees killed");
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
    //public ArrayList<Department> getDepartments() { return departments; }
}
