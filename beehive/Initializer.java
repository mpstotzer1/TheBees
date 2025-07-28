package beehive;

import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.event.Situation;
import beehive.event.SituationData;
import beehive.event.situationStrategies.*;
import beehive.job.*;
import beehive.resource.ResourceData;
import beehive.resource.Resources;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.temperature.TemperatureInfo;
import beehive.world.Season;
import beehive.world.WorldInfo;

import java.util.ArrayList;

public class Initializer{

	public Hive initializeHive(){
		Upgrades upgrades = initializeUpgrades();
		Resources resources = initializeResources();
		ResourceData resourceData = initializeResourceData();
		TemperatureInfo temperatureInfo = initializeTemperature();
		MiscData miscData = initializeMiscData();
		WorldInfo worldInfo = initializeWorld(miscData);
		DepartmentInfo departmentInfo = initializeDepartments();
		SituationData situationData = initializeSituations();
		JobInfo jobInfo = initializeJobs(resources, departmentInfo, temperatureInfo, miscData, upgrades);

		HiveModuleContainer hiveModuleContainer = new HiveModuleContainer(resources, resourceData, temperatureInfo, worldInfo, departmentInfo, jobInfo, situationData, upgrades, miscData);
		departmentInfo.addWorkers(400);

        return new Hive(hiveModuleContainer);
	}

	private ResourceData initializeResourceData() {
		ResourceData temp = new ResourceData(20, 20);

		return temp;
	}
	private DepartmentInfo initializeDepartments() {
		Department nurse = new Department();
		Department forager = new Department();
		Department guard = new Department();
		Department waxMason = new Department();
		Department houseBee = new Department();
		Department drone = new Department();
		Department cluster = new Department();

		DepartmentInfo temp = new DepartmentInfo(nurse, forager, guard, waxMason, houseBee, cluster, drone);

		return temp;
	}
	private JobInfo initializeJobs(Resources resources, DepartmentInfo departments, TemperatureInfo temperatureInfo, MiscData miscData, Upgrades upgrades) {
		ResourceAddStrategy resourceAddStrategy = new ResourceAddStrategy();
		ResourceSetStrategy resourceSetStrategy = new ResourceSetStrategy();
		ResourceNullStrategy resourceNullStrategy = new ResourceNullStrategy();

		DepartmentJob foragerNectar = new DepartmentJob(resources.nectar(), resourceAddStrategy, departments.getForager(), 0.9, .8, 0.0 );
		DepartmentJob foragerPollen = new DepartmentJob(resources.pollen(), resourceAddStrategy, departments.getForager(), 1.0, .8, 0.0);
		DepartmentJob waxMasonWax = new DepartmentJob(resources.wax(), resourceAddStrategy, departments.getWaxMason(), 1.0, .5, 0.024);
		DepartmentJob droneXP = new DepartmentJob(resources.xp(), resourceAddStrategy, departments.getDrone(), .35, .8, 0.04);

		DepartmentJob nurseQueenHealth = new DepartmentJob(resources.queenHealth(), resourceSetStrategy, departments.getNurse(), 1.0, .4, 0.034);
		DepartmentJob guardStrength = new DepartmentJob(resources.strength(), resourceSetStrategy, departments.getGuard(), 1.0, .5, 0.024);
		DepartmentJob houseBeeHygiene = new DepartmentJob(resources.hygiene(), resourceSetStrategy, departments.getHouseBee(), 1.0, .4, 0.04);

		DepartmentJob clusterIdle = new DepartmentJob(resources.nullResource(), resourceNullStrategy, departments.getCluster(), 1.0, 0.2, 0.007);

		HiveTemperatureRegulator hiveTemperatureRegulator = new HiveTemperatureRegulator(temperatureInfo, upgrades, departments, 1.0, 4.3, 0.0);
		BeeCreator beeCreator = new BeeCreator(resources.pollen(), temperatureInfo, miscData, departments, 1.0, .4, .02);

		JobInfo temp = new JobInfo(foragerNectar, foragerPollen, droneXP, waxMasonWax,
				guardStrength, nurseQueenHealth, houseBeeHygiene,
				clusterIdle,
				beeCreator, hiveTemperatureRegulator);

		return temp;
	}
	private WorldInfo initializeWorld(MiscData miscData) {
		Season season = new Season(miscData);
		WorldInfo temp = new WorldInfo(season);

		return temp;
	}
	private TemperatureInfo initializeTemperature() {
		TemperatureInfo temp = new TemperatureInfo(0.2);

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
	private Upgrades initializeUpgrades() {
		Upgrades upgrades = new Upgrades(
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0,
		1.0);

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
	private MiscData initializeMiscData(){
		MiscData temp = new MiscData(15, 0.4, 120);

		return temp;
	}
}