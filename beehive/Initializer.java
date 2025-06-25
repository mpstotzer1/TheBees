//The Initializer creates a Hive
 /* 	I want the Controller to create an instance of Hive and then instantiate all its variables i.e. make
 * a functioning Hive object. It should essentially keep the Hive in a loop that updates it constantly
 * and doesn't break until the player wins or loses. Every time it calls the Hive.produce() function, it should
 * redraw the graphics afterwards. This will come much later.
 * 		It should also manage the upgrades for the Hivemind, probably with a hashmap. Those
 * values will be used to instantiate the Hive object.
 * 		It needs to also control the passage of time and keep a running (but pausable) clock.
 * 		It needs to manage the seasons in the game and adjust the Hive's variables accordingly. Most
 * importantly, it needs to manage how winter and clustering work.
 */

/*		KEY RESPONSIBILITIES
 * -Have the game loop that updates the Hive and the graphics
 * -Create the Hive object from the upgrade hashmaps
 * -Control the seasons (WINTER!)
 * -Control the passage of time
 * */
package beehive;

import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.event.Situation;
import beehive.event.SituationData;
import beehive.event.situationStrategies.*;
import beehive.hivestate.ActiveHive;
import beehive.hivestate.HiveState;
import beehive.hivestate.HiveStateInfo;
import beehive.job.*;
import beehive.resource.ResourceData;
import beehive.resource.Resources;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.temperature.TemperatureInfo;
import beehive.world.Season;
import beehive.world.WorldInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Initializer{

    public Hive createHive(){
		HashMap<String, Double> upgrades = initializeUpgrades();
		Resources resources = initializeResources();
		ResourceData resourceData = initializeResourceData();
		TemperatureInfo temperatureInfo = initializeTemperature();
		WorldInfo worldInfo = initializeWorld();
		JobInfo jobInfo = initializeJobs(resources);
		HiveJobInfo hiveJobInfo = initializeHiveJobs();
		DepartmentInfo departmentInfo = initializeDepartments(jobInfo);
		SituationData situationData = initializeSituations();
		HiveStateInfo hiveStateInfo = initializeHiveState();

    	Hive hive = new Hive(resources, resourceData, temperatureInfo, worldInfo, departmentInfo, jobInfo, hiveJobInfo, situationData, upgrades, hiveStateInfo);

		for(Department dept: departmentInfo.getDepartments()){
			hive.addBeesToDepartment(50, dept);
		}

    	return hive;
    }

	private HiveStateInfo initializeHiveState() {
		HiveStateInfo temp = new HiveStateInfo();

		return temp;
	}
	private ResourceData initializeResourceData() {
		ResourceData temp = new ResourceData(20, 20);

		return temp;
	}
	private DepartmentInfo initializeDepartments(JobInfo jobInfo) {
		Department nurse = new Department(jobInfo.getNurseQueenHealth());
		Department forager = new Department(jobInfo.getForagerNectar(), jobInfo.getForagerPollen());
		Department guard = new Department(jobInfo.getGuardStrength());
		Department waxMason = new Department(jobInfo.getWaxMasonWax());
		Department houseBee = new Department(jobInfo.getHouseBeeHygiene());
		Department fanner = new Department(jobInfo.getFannerHoney());
		Department drone = new Department(jobInfo.getDroneXP());
		Department cluster = new Department(jobInfo.getClusterIdle());

		DepartmentInfo temp = new DepartmentInfo(nurse, forager, guard, waxMason, houseBee, fanner, drone, cluster);

		return temp;
	}
	private HiveJobInfo initializeHiveJobs() {
		HiveTemperatureRegulator hiveTemperatureRegulator = new HiveTemperatureRegulator(0.5, 0.0, 2.5);
		BeeCreator beeCreator = new BeeCreator(0.0, .03, .40);

		HiveJobInfo temp = new HiveJobInfo(hiveTemperatureRegulator, beeCreator);

		return temp;
	}
	private JobInfo initializeJobs(Resources resources) {
		ResourceAddStrategy resourceAddStrategy = new ResourceAddStrategy();
		ResourceSetStrategy resourceSetStrategy = new ResourceSetStrategy();
		ResourceNullStrategy resourceNullStrategy = new ResourceNullStrategy();

		DepartmentJob foragerNectar = new DepartmentJob(resources.nectar(), resourceAddStrategy, 0.5, .03, 2.0);
		DepartmentJob foragerPollen = new DepartmentJob(resources.pollen(), resourceAddStrategy, 0.5, .03, .15);
		DepartmentJob waxMasonWax = new DepartmentJob(resources.wax(), resourceAddStrategy, 0.5, .03, .5);
		DepartmentJob droneXP = new DepartmentJob(resources.xp(), resourceAddStrategy, 0.5, .03, .35);

		DepartmentJob nurseQueenHealth = new DepartmentJob(resources.queenHealth(), resourceSetStrategy, 0.5, .03, 1.0);
		DepartmentJob guardStrength = new DepartmentJob(resources.strength(), resourceSetStrategy, 0.5, .03, 1.0);
		DepartmentJob houseBeeHygiene = new DepartmentJob(resources.hygiene(), resourceSetStrategy, 0.5, .03, 1.0);

		DepartmentJob clusterIdle = new DepartmentJob(resources.nullResource(), resourceNullStrategy, 0.5, .03, 1.0);
		DepartmentJob fannerHoney = new DepartmentJob(resources.nullResource(), resourceNullStrategy, 0.5, .03, 10.0);
		//Job fannerHoney produces "null" because it is managed with separate logic in Hive.java

		JobInfo temp = new JobInfo(foragerNectar, foragerPollen, waxMasonWax, droneXP,
				nurseQueenHealth, guardStrength, houseBeeHygiene,
				clusterIdle, fannerHoney);

		return temp;
	}
	private WorldInfo initializeWorld() {
		Season season = new Season();
		WorldInfo temp = new WorldInfo(season);

		return temp;
	}
	private TemperatureInfo initializeTemperature() {
		TemperatureInfo temp = new TemperatureInfo(10.0);

		return temp;
	}
	private Resources initializeResources() {
		Resource hygiene = new Resource(50);
		Resource queenHealth = new Resource(50);
		Resource strength = new Resource(50);
		Resource xp = new Resource(0);
		Resource wax = new Resource(0);
		Resource pollen = new Resource(0);
		PotentResource nectar = new PotentResource(0, 5);
		PotentResource honey = new PotentResource(0, 10);
		Resource nullResource = new Resource();

		Resources temp = new Resources(hygiene, queenHealth, strength, xp, wax, pollen, nectar, honey, nullResource);

		return temp;
	}
	private HashMap<String, Double> initializeUpgrades() {
		HashMap<String, Double> upgrades = new HashMap<String, Double>(){{}};

		//Productivity upgrades for Jobs
		upgrades.put("nurseQueenHealthProd", 1.0);
		upgrades.put("foragerNectarProd", 1.0);
		upgrades.put("foragerPollenProd", 1.0);
		upgrades.put("guardStrengthProd", 1.0);
		upgrades.put("waxMasonWaxProd", 1.0);
		upgrades.put("houseBeeHygieneProd", 1.0);
		upgrades.put("fannerHoneyProd", 1.0);
		upgrades.put("droneXPProd", 1.0);
		//Decrease food costs globally
		upgrades.put("foodCostMult", 1.0);
		//Insulate hive from temperature fluctuations
		upgrades.put("insulation", 1.0);
		//Increase Strength
		upgrades.put("strengthMult", 1.0);
		//Make Occurrences less disastrous? Or less likely?
		//Make seasons better(more flowers?)
		upgrades.put("flowerMult", 1.0);
		//Kill fewer bees from starvation
		upgrades.put("starvationMult", 1.0);
		//(Adjustable) nursery costs
		upgrades.put("droneCost", 80.0);
		upgrades.put("workerCost", 60.0);

		return upgrades;
	}
	private SituationData initializeSituations(){
		Strategy coldSnapStrategy = new ColdSnap();
		Strategy diseaseStrategy = new Disease();
		Strategy heatWaveStrategy = new HeatWave();
		Strategy matingSeasonStrategy = new MatingSeason();
		Strategy pesticideStrategy = new Pesticide();
		Strategy superbloomStrategy = new Superbloom();
		Strategy varroaStrategy = new Varroa();

		Situation coldSnap = new Situation(30, coldSnapStrategy);
		Situation disease = new Situation(30, diseaseStrategy);
		Situation heatWave = new Situation(30, heatWaveStrategy);
		Situation matingSeason = new Situation(30, matingSeasonStrategy);
		Situation pesticide = new Situation(30, pesticideStrategy);
		Situation superbloom = new Situation(30, superbloomStrategy);
		Situation varroa = new Situation(30, varroaStrategy);

		ArrayList<Situation> allSituations = new ArrayList<Situation>();
		allSituations.add(coldSnap);
		allSituations.add(disease);
		allSituations.add(heatWave);
		allSituations.add(matingSeason);
		allSituations.add(pesticide);
		allSituations.add(superbloom);
		allSituations.add(varroa);

		SituationData temp = new SituationData(allSituations);

		return temp;
	}
}