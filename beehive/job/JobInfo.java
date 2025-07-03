//All the job-related info
//Job nomenclature: "department" + "ResourceInfluenced"
package beehive.job;

import beehive.Hive;

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
	//Hive jobs
	private BeeCreator beeCreator;
	private HiveTemperatureRegulator hiveTemperatureRegulator;
	//List of jobs
    private ArrayList<DepartmentJob> departmentJobs = new ArrayList<>();
	private ArrayList<Job> allJobs = new ArrayList<>();
	
    //The constructor
    public JobInfo(DepartmentJob foragerNectar, DepartmentJob foragerPollen, DepartmentJob droneXP, DepartmentJob waxMasonWax,
				   DepartmentJob guardStrength, DepartmentJob nurseQueenHealth, DepartmentJob houseBeeHygiene,
				   DepartmentJob fannerHoney, DepartmentJob clusterIdle,
				   BeeCreator beeCreator, HiveTemperatureRegulator hiveTemperatureRegulator){
		this.foragerNectar = foragerNectar;
		this.foragerPollen = foragerPollen;
		this.droneXP = droneXP;
		this.waxMasonWax = waxMasonWax;
		this.guardStrength = guardStrength;
		this.nurseQueenHealth = nurseQueenHealth;
		this.houseBeeHygiene = houseBeeHygiene;
		this.fannerHoney = fannerHoney;
		this.clusterIdle = clusterIdle;
		this.beeCreator = beeCreator;
		this. hiveTemperatureRegulator = hiveTemperatureRegulator;

		this.departmentJobs.add(foragerNectar);
		this.departmentJobs.add(foragerPollen);
		this.departmentJobs.add(droneXP);
		this.departmentJobs.add(waxMasonWax);
		this.departmentJobs.add(guardStrength);
		this.departmentJobs.add(nurseQueenHealth);
		this.departmentJobs.add(houseBeeHygiene);
		this.departmentJobs.add(fannerHoney);
		this.departmentJobs.add(clusterIdle);

		allJobs.addAll(departmentJobs);
		allJobs.add(beeCreator);
		allJobs.add(hiveTemperatureRegulator);
	}
	
	//Getters and Setters
	public DepartmentJob getNurseQueenHealth() { return nurseQueenHealth; }
	public DepartmentJob getForagerNectar() { return foragerNectar; }
	public DepartmentJob getForagerPollen() { return foragerPollen; }
	public DepartmentJob getGuardStrength() { return guardStrength; }
	public DepartmentJob getWaxMasonWax() { return waxMasonWax; }
	public DepartmentJob getHouseBeeHygiene() { return houseBeeHygiene; }
	public DepartmentJob getFannerHoney() { return fannerHoney; }
	public DepartmentJob getDroneXP() { return droneXP; }
	public DepartmentJob getClusterIdle() { return clusterIdle; }
	public BeeCreator getBeeCreator() { return beeCreator; }
	public HiveTemperatureRegulator getHiveTemperatureRegulator() { return hiveTemperatureRegulator; }
	public ArrayList<DepartmentJob> getDepartmentJobs() { return departmentJobs; }
	public ArrayList<Job> getAllJobs() {return allJobs; }
}
