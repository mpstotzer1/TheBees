//The Controller manages the Hive's operations
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

import java.util.HashMap;

public class Initializer{
    private HashMap<String, Double> upgrades = new HashMap<String, Double>(){{}};
	private Hive hive;
    
    public Initializer(){
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
    	//Faster brood production
    	upgrades.put("queenCountdown", 50.0);
    	upgrades.put("droneCountdown", 40.0);
    	upgrades.put("workerCountdown", 30.0);
    	//Unlock how to make a queen (0 is false, 1 is true)
    	upgrades.put("canMakeQueen", 0.0);
    	//Increase Strength
    	upgrades.put("strengthMult", 1.0);
    	//Make Occurrences less disastrous? Or less likely?
    	
    	//Make seasons better(more flowers?)
    	upgrades.put("flowerMult", 1.0);
    	//Kill fewer bees from starvation
    	upgrades.put("starvationMult", 1.0);
    	//(Adjustable) nursery costs
    	upgrades.put("queenCost", 100.0);
    	upgrades.put("droneCost", 80.0);
    	upgrades.put("workerCost", 60.0);
    	//Make queen healthier (i.e. die less quickly)
    	upgrades.put("queenHealthDecMult", 1.0);
    	
    	//FOLLOW THIS PATTERN! (maybe a bunch of default constructors is better, though?)
    	Resource pollen = new Resource(100);
    	PotentResource nectar = new PotentResource(100, 3);
    	PotentResource honey = new PotentResource(100, 10);
    	Resource wax = new Resource(100);
    	PhysicalResources physicalResources = new PhysicalResources(pollen, nectar, honey, wax);
    	
    	AbstractResources abstractResources = new AbstractResources(100, 20, 20, 0, 0);
    	
    	TemperatureInfo temperatureInfo = new TemperatureInfo(94, 10000, 10, 7);
    	
    	QueenContainer queenContainer = new QueenContainer();
    	
    	//AdditiveJobs
    	Job foragerNectar = new AdditiveJob(physicalResources.getNectar());
    	Job foragerPollen = new AdditiveJob(physicalResources.getPollen());
    	Job waxMasonWax = new AdditiveJob(physicalResources.getWax());
    	Job droneXP = new AdditiveJob(abstractResources.getXp());
    	//SettingJobs
    	Job nurseQueenHealth = new SettingJob(queenContainer.getHealthDec());
    	Job guardStrength = new SettingJob(abstractResources.getStrength());
    	Job houseBeeHygiene = new SettingJob(abstractResources.getHygiene());
    	//NullJobs
    	Job clusterIdle = new NullJob();
    	Job fannerHoney = new NullJob();
    	JobInfo jobInfo = new JobInfo(foragerNectar, foragerPollen, waxMasonWax, droneXP,
    									nurseQueenHealth, guardStrength, houseBeeHygiene,
    									clusterIdle, fannerHoney);
    	
    	Department nurse = new Department(nurseQueenHealth);
    	Department forager = new Department(foragerNectar, foragerPollen);
    	Department guard = new Department(guardStrength);
    	Department waxMason = new Department(waxMasonWax);
    	Department houseBee = new Department(houseBeeHygiene);
    	Department fanner = new Department(fannerHoney);
    	Department drone = new Department(droneXP);
    	Department cluster = new Department(clusterIdle);
    	DepartmentInfo departmentInfo = new DepartmentInfo(nurse, forager, guard, waxMason, houseBee, fanner, drone, cluster);

    	
    	NurseryInfo nurseryInfo = new NurseryInfo();
    	
    	//UpgradeInfo upgradeInfo = new UpgradeInfo(upgrades);
    	
    	//HiveResources hiveResources = new HiveResources();
    	
//Does this work? Instantiating hive with a constructor after it was already semi-constructed as a member variable?
    	setHive(new Hive(physicalResources, abstractResources, temperatureInfo, queenContainer, departmentInfo, jobInfo, nurseryInfo, upgrades));
    }

    //Getters and Setters
	public Hive getHive(){ return hive; }
	public void setHive(Hive hive){ this.hive = hive; }
    
}
