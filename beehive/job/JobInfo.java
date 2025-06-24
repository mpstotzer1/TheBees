//All the job-related info
//Job nomenclature: "department" + "ResourceInfluenced"
package beehive.job;

import java.util.ArrayList;

public class JobInfo {
	//Additive jobs
	private DepartmentJob foragerNectar;
	private DepartmentJob foragerPollen;
	private DepartmentJob droneXP;
	private DepartmentJob waxMasonWax;
	//Setting jobs
	private DepartmentJob guardStrength;
	private DepartmentJob nurseQueenHealth;
	private DepartmentJob houseBeeHygiene;
	//Null jobs
	private DepartmentJob fannerHoney;
	private DepartmentJob clusterIdle;
	//The usual list of jobs
    private ArrayList<DepartmentJob> departmentJobs = new ArrayList<DepartmentJob>();
	
    //The constructor
    public JobInfo(DepartmentJob foragerNectar, DepartmentJob foragerPollen, DepartmentJob droneXP, DepartmentJob waxMasonWax,
				   DepartmentJob guardStrength, DepartmentJob nurseQueenHealth, DepartmentJob houseBeeHygiene,
				   DepartmentJob fannerHoney, DepartmentJob clusterIdle){
		this.foragerNectar = foragerNectar;
		this.foragerPollen = foragerPollen;
		this.droneXP = droneXP;
		this.waxMasonWax = waxMasonWax;
		this.guardStrength = guardStrength;
		this.nurseQueenHealth = nurseQueenHealth;
		this.houseBeeHygiene = houseBeeHygiene;
		this.fannerHoney = fannerHoney;
		this.clusterIdle = clusterIdle;

		this.departmentJobs.add(foragerNectar);
		this.departmentJobs.add(foragerPollen);
		this.departmentJobs.add(droneXP);
		this.departmentJobs.add(waxMasonWax);
		this.departmentJobs.add(guardStrength);
		this.departmentJobs.add(nurseQueenHealth);
		this.departmentJobs.add(houseBeeHygiene);
		this.departmentJobs.add(fannerHoney);
		this.departmentJobs.add(clusterIdle);
	}
	
	//Getters and Setters
	public DepartmentJob getNurseQueenHealth() { return nurseQueenHealth; }
	//public void setNurseQueenHealth(Job nurseQueenHealth) { this.nurseQueenHealth = nurseQueenHealth; }
	public DepartmentJob getForagerNectar() { return foragerNectar; }
	//public void setForagerNectar(Job foragerNectar) { this.foragerNectar = foragerNectar; }
	public DepartmentJob getForagerPollen() { return foragerPollen; }
	//public void setForagerPollen(Job foragerPollen) { this.foragerPollen = foragerPollen; } // typo assumed to be foragerConst
	public DepartmentJob getGuardStrength() { return guardStrength; }
	//public void setGuardStrength(Job guardStrength) { this.guardStrength = guardStrength; }
	public DepartmentJob getWaxMasonWax() { return waxMasonWax; }
	//public void setWaxMasonWax(Job waxMasonWax) { this.waxMasonWax = waxMasonWax; }
	public DepartmentJob getHouseBeeHygiene() { return houseBeeHygiene; }
	//public void setHouseBeeHygiene(Job houseBeeHygiene) { this.houseBeeHygiene = houseBeeHygiene; }
	public DepartmentJob getFannerHoney() { return fannerHoney; }
	//public void setFannerHoney(Job fannerHoney) { this.fannerHoney = fannerHoney; }
	public DepartmentJob getDroneXP() { return droneXP; }
	//public void setDroneXP(Job droneXP) { this.droneXP = droneXP; }
	public DepartmentJob getClusterIdle() { return clusterIdle; }
	//public void setClusterIdle(Job clusterIdle) { this.clusterIdle = clusterIdle; }
	public ArrayList<DepartmentJob> getJobs() { return departmentJobs; }
	//public void setJobs(List<Job> jobs) { this.jobs = jobs; }
}
