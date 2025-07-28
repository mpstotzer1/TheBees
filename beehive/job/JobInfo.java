package beehive.job;

import java.util.ArrayList;

public class JobInfo {
	//Job nomenclature: "department" + "ResourceInfluenced"
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
	private DepartmentJob clusterIdle;
	//Hive jobs
	private HiveTemperatureRegulator hiveTemperatureRegulator;
	private BeeCreator beeCreator;
	//Lists of jobs
    private ArrayList<DepartmentJob> departmentJobs = new ArrayList<>();
	private ArrayList<Job> allJobs = new ArrayList<>();
	
    //The constructor
    public JobInfo(DepartmentJob foragerNectar, DepartmentJob foragerPollen, DepartmentJob droneXP, DepartmentJob waxMasonWax,
				   DepartmentJob guardStrength, DepartmentJob nurseQueenHealth, DepartmentJob houseBeeHygiene,
				   DepartmentJob clusterIdle,
				   BeeCreator beeCreator, HiveTemperatureRegulator hiveTemperatureRegulator){
		this.foragerNectar = foragerNectar;
		this.foragerPollen = foragerPollen;
		this.droneXP = droneXP;
		this.waxMasonWax = waxMasonWax;
		this.guardStrength = guardStrength;
		this.nurseQueenHealth = nurseQueenHealth;
		this.houseBeeHygiene = houseBeeHygiene;
		this.clusterIdle = clusterIdle;
		this.beeCreator = beeCreator;
		this.hiveTemperatureRegulator = hiveTemperatureRegulator;

		departmentJobs.add(foragerNectar);
		departmentJobs.add(foragerPollen);
		departmentJobs.add(droneXP);
		departmentJobs.add(waxMasonWax);
		departmentJobs.add(guardStrength);
		departmentJobs.add(nurseQueenHealth);
		departmentJobs.add(houseBeeHygiene);
		departmentJobs.add(clusterIdle);

		allJobs.addAll(departmentJobs);
		allJobs.add(hiveTemperatureRegulator);
		allJobs.add(beeCreator);
	}
	
	//Getters and Setters
	public DepartmentJob getNurseQueenHealth() { return nurseQueenHealth; }
	public DepartmentJob getForagerNectar() { return foragerNectar; }
	public DepartmentJob getForagerPollen() { return foragerPollen; }
	public DepartmentJob getGuardStrength() { return guardStrength; }
	public DepartmentJob getWaxMasonWax() { return waxMasonWax; }
	public DepartmentJob getHouseBeeHygiene() { return houseBeeHygiene; }
	public DepartmentJob getDroneXP() { return droneXP; }
	public DepartmentJob getClusterIdle() { return clusterIdle; }
	public HiveTemperatureRegulator getHiveTemperatureRegulator() { return hiveTemperatureRegulator; }
	public BeeCreator getBeeCreator() { return beeCreator; }
	public ArrayList<DepartmentJob> getDepartmentJobs() { return departmentJobs; }
	public ArrayList<Job> getAllJobs() {return allJobs; }
}
