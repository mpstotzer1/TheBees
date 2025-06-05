//The Initializer creates a Hive
 /* 	I want the Controller to create an instance of Hive and then instantiate all its variables i.e. make
 * a functioning Hive object. It should essentially keep the Hive in a loop that updates it constantly
 * and doesn't break until the player wins or loses. Every time it calls the Hive.update() function, it should
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
import beehive.job.*;
import beehive.miscData.MiscellaneousData;
import beehive.resource.AbstractResources;
import beehive.resource.PhysicalResources;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.temperature.TemperatureInfo;
import beehive.world.Season;
import beehive.world.WorldInfo;

import java.util.HashMap;

public class Initializer{
    private HashMap<String, Double> upgrades = new HashMap<String, Double>(){{}};

    public Hive createHive(){
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
    	
    	//FOLLOW THIS PATTERN! (maybe a bunch of default constructors is better, though?)
    	Resource pollen = new Resource(100);
    	PotentResource nectar = new PotentResource(100, 3);
    	PotentResource honey = new PotentResource(100, 10);
    	Resource wax = new Resource(100);
    	PhysicalResources physicalResources = new PhysicalResources(pollen, nectar, honey, wax);

    	AbstractResources abstractResources = new AbstractResources(100, 20, 100, 20, 0, 0);

    	TemperatureInfo temperatureInfo = new TemperatureInfo(94, 10000, 10.0, 1.0, 100.0);

		Season season = new Season();
		WorldInfo worldInfo = new WorldInfo(season);


		//Job Section
		ResourceAddStrategy resourceAddStrategy = new ResourceAddStrategy();
		ResourceSetStrategy resourceSetStrategy = new ResourceSetStrategy();
		ResourceNullStrategy resourceNullStrategy = new ResourceNullStrategy();
		HiveCreateBeesCommand hiveCreateBeesCommand = new HiveCreateBeesCommand();
		HiveAdjustTemperatureCommand hiveAdjustTemperatureCommand = new HiveAdjustTemperatureCommand();
    	//AdditiveJobs
		DepartmentJob foragerNectar = new DepartmentJob(physicalResources.getNectar(), resourceAddStrategy, 1.0, 1.0);
		DepartmentJob foragerPollen = new DepartmentJob(physicalResources.getPollen(), resourceAddStrategy, 1.0, 1.0);
		DepartmentJob waxMasonWax = new DepartmentJob(physicalResources.getWax(), resourceAddStrategy, 1.0, 1.0);
		DepartmentJob droneXP = new DepartmentJob(abstractResources.getXp(), resourceAddStrategy, 1.0, 1.0);
    	//SettingJobs
		DepartmentJob nurseQueenHealth = new DepartmentJob(abstractResources.getQueenHealth(), resourceSetStrategy, 1.0, 1.0);
		DepartmentJob guardStrength = new DepartmentJob(abstractResources.getStrength(), resourceSetStrategy, 1.0, 1.0);
		DepartmentJob houseBeeHygiene = new DepartmentJob(abstractResources.getHygiene(), resourceSetStrategy, 1.0, 1.0);
    	//NullJobs
		DepartmentJob clusterIdle = new DepartmentJob(abstractResources.getNullResource(), resourceNullStrategy, 1.0, 1.0);
		DepartmentJob fannerHoney = new DepartmentJob(abstractResources.getNullResource(), resourceNullStrategy, 1.0, 1.0);
		//Autoregulated Hive jobs
		HiveJob createBees = new HiveJob(hiveCreateBeesCommand, 1.0, 1.0);
		HiveJob adjustTemperature = new HiveJob(hiveAdjustTemperatureCommand, 1.0, 1.0);

		DepartmentJobInfo departmentJobInfo = new DepartmenJobInfo(foragerNectar, foragerPollen, waxMasonWax, droneXP,
				nurseQueenHealth, guardStrength, houseBeeHygiene,
				clusterIdle, fannerHoney);
		HiveJobInfo hiveJobInfo = new HiveJobInfo(createBees, adjustTemperature);

    	JobInfo jobInfo = new JobInfo(departmentJobInfo, hiveJobInfo);

    	Department nurse = new Department(nurseQueenHealth);
    	Department forager = new Department(foragerNectar, foragerPollen);
    	Department guard = new Department(guardStrength);
    	Department waxMason = new Department(waxMasonWax);
    	Department houseBee = new Department(houseBeeHygiene);
    	Department fanner = new Department(fannerHoney);
    	Department drone = new Department(droneXP);
    	Department cluster = new Department(clusterIdle);
    	DepartmentInfo departmentInfo = new DepartmentInfo(nurse, forager, guard, waxMason, houseBee, fanner, drone, cluster);

		MiscellaneousData miscellaneousData = new MiscellaneousData();

    	//UpgradeInfo upgradeInfo = new UpgradeInfo(upgrades);

    	//HiveResources hiveResources = new HiveResources();

//Does this work? Instantiating hive with a constructor after it was already semi-constructed as a member variable?
    	Hive hive = new Hive(physicalResources, abstractResources, temperatureInfo, worldInfo, departmentInfo, jobInfo, miscellaneousData, upgrades);
    	return hive;
    }

    //Getters and Setters
	//public Hive getHive(){ return hive; }

}
