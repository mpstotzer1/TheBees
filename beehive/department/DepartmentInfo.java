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
    private ArrayList<Department> allDepartments = new ArrayList<>();
	private ArrayList<Department> workerDepartments = new ArrayList<>();

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


	public void addWorkers(int numWorkers){
		int originalTotalBees = getTotalBees();

		int beesAddedSoFar = 0;
		for(Department dept: workerDepartments){
			double percentPopulation = dept.getNumBees() / (double) originalTotalBees;
			int toAdd = (int)(percentPopulation * numWorkers);

			dept.addBees(toAdd);
			beesAddedSoFar += toAdd;
		}

		Random rand = new Random();
		int leftoverBees = numWorkers - beesAddedSoFar;
		while(leftoverBees > 0){
			int randIndex = rand.nextInt(workerDepartments.size());
			workerDepartments.get(randIndex).addBees(1);

			leftoverBees--;
		}
	}
	public void killBees(int beesToKill){
		int originalTotalBees = getTotalBees();

		int beesLeftToKill = beesToKill;
		for(Department dept: allDepartments){
			double percentPopulation = dept.getNumBees() / (double)(originalTotalBees);
			int toKill = (int)(percentPopulation * beesToKill);

			beesLeftToKill -= dept.attemptSubBees(toKill);
		}

		Random rand = new Random();
		while((beesLeftToKill > 0) && (getTotalBees() > 0)){
			int randIndex = rand.nextInt(allDepartments.size());
			Department randDept = allDepartments.get(randIndex);
			if(randDept.getNumBees() > 0){
				beesLeftToKill -= randDept.attemptSubBees(1);
			}
		}

		Logger.productionDebugging("Bees killed: " + (beesToKill - beesLeftToKill));
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
