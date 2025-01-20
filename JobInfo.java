//All the job-related info
//Job nomenclature: "department" + "ResourceInfluenced"

import java.util.ArrayList;

public class JobInfo {
	//Additive jobs
	private Job foragerNectar;
	private Job foragerPollen;
	private Job droneXP;
	private Job waxMasonWax;
	//Setting jobs
	private Job guardStrength;
	private Job nurseQueenHealth;
	private Job houseBeeHygiene;
	//Null jobs
	private Job fannerHoney;
	private Job clusterIdle;
	//The usual list of jobs
    private ArrayList<Job> jobs;
	
    //The constructor
    public JobInfo(Job foragerNectar, Job foragerPollen, Job droneXP, Job waxMasonWax,
					Job guardStrength, Job nurseQueenHealth, Job houseBeeHygiene,
					Job fannerHoney, Job clusterIdle){
		this.foragerNectar = foragerNectar;
		this.foragerPollen = foragerPollen;
		this.droneXP = droneXP;
		this.waxMasonWax = waxMasonWax;
		this.guardStrength = guardStrength;
		this.nurseQueenHealth = nurseQueenHealth;
		this.houseBeeHygiene = houseBeeHygiene;
		this.fannerHoney = fannerHoney;
		this.clusterIdle = clusterIdle;
		//Populate the "jobs" list
		this.jobs.add(foragerNectar);
		this.jobs.add(foragerPollen);
		this.jobs.add(droneXP);
		this.jobs.add(waxMasonWax);
		this.jobs.add(guardStrength);
		this.jobs.add(nurseQueenHealth);
		this.jobs.add(houseBeeHygiene);
		this.jobs.add(fannerHoney);
		this.jobs.add(clusterIdle);
	}
	
	//Getters and Setters
	public Job getNurseQueenHealth() { return nurseQueenHealth; }
	public void setNurseQueenHealth(Job nurseQueenHealth) { this.nurseQueenHealth = nurseQueenHealth; }
	public Job getForagerNectar() { return foragerNectar; }
	public void setForagerNectar(Job foragerNectar) { this.foragerNectar = foragerNectar; }
	public Job getForagerPollen() { return foragerPollen; }
	public void setForagerPollen(Job foragerPollen) { this.foragerPollen = foragerPollen; } // typo assumed to be foragerConst
	public Job getGuardStrength() { return guardStrength; }
	public void setGuardStrength(Job guardStrength) { this.guardStrength = guardStrength; }
	public Job getWaxMasonWax() { return waxMasonWax; }
	public void setWaxMasonWax(Job waxMasonWax) { this.waxMasonWax = waxMasonWax; }
	public Job getHouseBeeHygiene() { return houseBeeHygiene; }
	public void setHouseBeeHygiene(Job houseBeeHygiene) { this.houseBeeHygiene = houseBeeHygiene; }
	public Job getFannerHoney() { return fannerHoney; }
	public void setFannerHoney(Job fannerHoney) { this.fannerHoney = fannerHoney; }
	public Job getDroneXP() { return droneXP; }
	public void setDroneXP(Job droneXP) { this.droneXP = droneXP; }
	public Job getClusterIdle() { return clusterIdle; }
	public void setClusterIdle(Job clusterIdle) { this.clusterIdle = clusterIdle; }
	public ArrayList<Job> getJobs() { return jobs; }
	//public void setJobs(List<Job> jobs) { this.jobs = jobs; }
}
