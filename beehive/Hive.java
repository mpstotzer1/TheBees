package beehive;
//Each hive has many resources, departments, and jobs.

import java.lang.classfile.attribute.NestHostAttribute;
import java.util.HashMap;
import java.lang.Math;
import java.util.Random;

import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.job.JobInfo;
import beehive.miscData.MiscellaneousData;
import beehive.resource.AbstractResources;
import beehive.resource.PhysicalResources;
import beehive.resource.PotentResource;
import beehive.temperature.TemperatureInfo;
import beehive.temperature.TemperatureRegulationRanges;
import beehive.world.WorldInfo;

import javax.management.remote.rmi.RMIJRMPServerImpl;


public class Hive{
	//Information Classes--important for constructor!
	private boolean gameLost = false;
	private PhysicalResources physicalResources;
	private AbstractResources abstractResources;
	private TemperatureInfo temperatureInfo;
	private WorldInfo worldInfo;
	private DepartmentInfo departmentInfo;
	private JobInfo jobInfo;
	private MiscellaneousData miscellaneousData;
	private HashMap<String, Double> upgrades;

		
	//A "big-boned" constructor
	public Hive(PhysicalResources physicalResources,
				AbstractResources abstractResources,
				TemperatureInfo temperatureInfo,
				WorldInfo worldInfo,
				DepartmentInfo departmentInfo,
				JobInfo jobInfo,
				MiscellaneousData miscellaneousData,
				HashMap<String, Double> upgrades){
		//Your physical resources
		this.physicalResources = physicalResources;
		//Your "abstract" resources
		this.abstractResources = abstractResources;
		//Temperature (NOT a resource!)
		this.temperatureInfo = temperatureInfo;
		this.worldInfo = worldInfo;
		//Your departments
		this.departmentInfo = departmentInfo;
		//Your Jobs (nomenclature: departmentResourceInfluenced)
		this.jobInfo = jobInfo;
		this.miscellaneousData = miscellaneousData;
		//Upgrade-related variables
		this.upgrades = upgrades;
	}
	

	
	//The all-powerful update method
	public void update(){
		updateWorld();
		updateDepartmentJobs();
		updateHiveJobs();

		updateTemperature();
		updateNursery();


		updateOccurrences();


		//Maybe throw warnings/memos?
		//Decrease beehive.queen health
		for(int i = 0; i < queenContainer.getQueens().size(); i++){
			queenContainer.updateHealth(upgrades.get("queenHealthDecMult"));
		}
		//Check if the player has lost
		if(getTotalBees() <= 0){ gameLost = true; }
	}

	private void updateHiveJobs(){

	}



	private void updateDepartmentJobs(){
		//Since honey is not produced (it is converted) it is managed manually here, not in a department
		updateHoney();
		//The rest of the jobs
		updateNonHoney();

		int foodCost = calcProductionFoodCost();
		subFood(foodCost);
	}
	//updateJobs() helper functions
	private void updateHoney(){
		//Honey is complicated since it depends on and influences nectar
		int numFanners = 0;
		numFanners = departmentInfo.getFanner().getNumBees();
		if(physicalResources.getNectar().getAmount() > jobInfo.getFannerHoney().calcProduction(numFanners)){
			physicalResources.getHoney().add(jobInfo.getFannerHoney().calcProduction(numFanners) / physicalResources.getHoney().getPotency());
			physicalResources.getNectar().sub(jobInfo.getFannerHoney().calcProduction(numFanners));
			//Is this else if statement necessary? Could it just be an else?
		} else if(physicalResources.getNectar().getAmount() > 0){
			physicalResources.getHoney().add(physicalResources.getNectar().getAmount() / physicalResources.getHoney().getPotency());
			physicalResources.getNectar().setAmount(0);
			//Throw "Unused Fanners!" warning
		}
	}
	private void updateNonHoney(){
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			departmentInfo.getDepartments().get(i).produce();
		}
	}
	private int calcProductionFoodCost(){
		int foodCost = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			foodCost += departmentInfo.getDepartments().get(i).getTotalFoodCost();
		}
		return foodCost;
	}

//	private void updateTemperature(){
//		double deltaHeat = calcProductionHeat() + calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp();
//		temperatureInfo.changeHiveTemp(deltaHeat);
//
//		regulateHiveTemperature();
//	}
//	//updateTemperature() helper functions
//	private int calcProductionHeat(){
//		int heat = 0;
//		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
//			heat += departmentInfo.getDepartments().get(i).getTotalHeat();
//		}
//		return heat;
//	}
//	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
//		double deltaTemp = worldInfo.getWorldTemp() - temperatureInfo.getHiveTemp();
//		deltaTemp *= .1;
//		return deltaTemp;
//	}
//	private void regulateHiveTemperature(){
//		double tempAdjustment = calcTempAdjustment();
//
//		temperatureInfo.changeHiveTemp(tempAdjustment);
//		int foodCost = calcTempRegulationFoodCost(tempAdjustment);
//		subFood(foodCost);
//	}
//	private double calcTempAdjustment(){
//		if(hiveTooHot()){
//			return clampToStep(maxMinusHive());
//		}else if(hiveTooCold()){
//			return clampToStep(minMinusHive());
//		}
//		return 0;
//	}
//	private int calcTempRegulationFoodCost(double tempAdjustment){
//		double foodCost = tempAdjustment * temperatureInfo.getTempRegulationFoodCostConstant();
//		foodCost /= upgrades.get("insulation");
//
//		return (int)(foodCost);
//	}
//	private boolean hiveTooHot(){
//		return (temperatureInfo.getHiveTemp() > temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature());
//	}
//	private boolean hiveTooCold(){
//		return (temperatureInfo.getHiveTemp() < temperatureInfo.getTemperatureRegulationRanges().getMinTemperature());
//	}
//	private double maxMinusHive() { return temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature() - temperatureInfo.getHiveTemp(); }
//	private double minMinusHive() { return temperatureInfo.getTemperatureRegulationRanges().getMinTemperature() - temperatureInfo.getHiveTemp(); }
//	private double clampToStep(double tempChange) {
//		double step = temperatureInfo.getTempRegulationStep();
//
//		if(tempChange > step){ return step; }
//		return tempChange;
//	}

//	//updateNursery() is nice and simple
//	private void updateNursery(){
//		if(insideBroodTempRange()){
//			createWorkerBees();
//		}
//	}
//	private boolean insideBroodTempRange(){
//		double hiveTemp = temperatureInfo.getHiveTemp();
//		double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
//		double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();
//
//		if(minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp){
//			return true;
//		}else{
//			return false;
//		}
//	}
//	private void createWorkerBees(){
//		int pollen = physicalResources.getPollen().getAmount();
//		pollen /= getTotalBees();
//		addBees(pollen, departmentInfo.getCluster());
//	}

	//World
	private void updateWorld(){
		worldInfo.update();
	}

	//These next methods are all for updating the various occurrences
	private void updateOccurrences(){
		handleStates(); //State handling is based on the immediate state of the hive
		handleEvents(); //Events only last for one tick and are influenced by the state of the hive and random chance
		createSituations(); //Situations endure for a time and adjust the food, heat, and production of jobs
		updateSituations();
		//Maybe throw warnings/memos?
	}


	private void handleStates(){
		checkStarvation();
		checkHygiene();
		checkQueenHealth();
		checkTemperature();
	}
	private void checkStarvation(){
		int amountNectar = physicalResources.getNectar().getAmount();
		int amountHoney = physicalResources.getHoney().getAmount();

		if(amountNectar <= 0 && amountHoney <= 0){//Starving
			//Throw starvation warning
		}
	}
	private void checkHygiene(){
		int amountHygiene = abstractResources.getHygiene().getAmount();
		int lowHygieneLimit = abstractResources.getLowHygiene();

		if(amountHygiene < lowHygieneLimit){
			killBees(lowHygieneLimit - amountHygiene);
			//The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
		}
	}
	private void checkQueenHealth(){
		int queenHealth = abstractResources.getQueenHealth().getAmount();
		int lowQueenHealth = abstractResources.getLowQueenHealth();

		if(queenHealth <= lowQueenHealth){
			for(int i = 0; i < jobInfo.getJobs().size(); i++){
				jobInfo.getJobs().get(i).addProdMod(1, 0.85);
			}
		}
	}
	private void checkTemperature(){
		double currentHiveTemp = temperatureInfo.getHiveTemp();

		if(currentHiveTemp < 55 || currentHiveTemp > 113){
			killPercentBees(3.0);
		}
	}

	private boolean situationHappened(){
		Random rand = new Random();
		int counter = miscellaneousData.getSituationCounter();

		double randNum = rand.nextDouble(0, 1.0);
		double chance = counter / 60.0;

		if(randNum <= chance){
			miscellaneousData.incrementSituationCounter();
			return true;
		}else{
			miscellaneousData.resetSituationCounter();
			return false;
		}
	}
	private void createSituations(){
		//Pest infestation, disease outbreak
		//Pesticides
		//Predator attack

		//Heat wave
		//Cold snap

		//Better foraging
		//Better waxmaking
		//Better whatever whatever whatever

		if(situationHappened()){
			int duration = 1;
			jobInfo.getForagerNectar().addProdMod(duration, .8);

		}


//		//chancePest is a linear equation; more hygiene = lower chance of pests
//		int chancePest = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees())*abstractResources.getStrength().getAmount() + 100);
//		if( chancePest >= (int)(Math.random()*100) ){
//			killPercentBees(.1);
//		}
//
//		//Generate a random number from 0-99
//		int rand = (int)(Math.random() * 100);
//		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
//		int chanceAttacked = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees()) * (abstractResources.getStrength().getAmount() * upgrades.get("strengthMult") + 100));
//
//		//Bad Events
//		if(rand < 5){//Pesticides
//			killPercentBees(.05);
//		}
//		if(rand < 10){//Extreme heat
//			temperatureInfo.incHiveTemp(1.3);
//		}
//		if(rand < 10){//Extreme cold
//			temperatureInfo.decHiveTemp(1.3);
//			jobInfo.getForagerNectar().addProdMod(30, .85);
//			jobInfo.getForagerPollen().addProdMod(30, .85);
//		}
//
//		//Predators
//		if(chanceAttacked >= rand){
//			//Predators (robber bees, wasps, hornets, mice)
//			int randPred = (int)(Math.random() * 4);
//			if(randPred == 0){//Robber Bees
//				physicalResources.getHoney().setAmount(0);
//				killBees(departmentInfo.getGuard().getNumBees());
//			}else if(randPred == 1){//Wasps--only in summer?
//				physicalResources.getHoney().setAmount( (int)(physicalResources.getHoney().getAmount() / 2) );
//				killPercentBees(.02);
//				nurseryInfo.getNursery().destroyOneBrood();
//			}else if(randPred == 2){//Hornets
//				killPercentBees(.07);
//			}else if(randPred == 3){//Mice--only in winter?
//				physicalResources.getWax().subPercent(.10);
//				physicalResources.getHoney().subPercent(.10);
//				physicalResources.getPollen().subPercent(.10);
//				physicalResources.getNectar().subPercent(.10);
//			}else if(randPred == 3){//Bears--Make less common? More of
//				physicalResources.getWax().subPercent(.10);
//				physicalResources.getHoney().subPercent(.10);
//				physicalResources.getPollen().subPercent(.10);
//				physicalResources.getNectar().subPercent(.10);
//			}
//		}


	}

	private void updateSituations(){
		for(int i = 0; i < jobInfo.getJobs().size(); i++){
			jobInfo.getJobs().get(i).updateMods();
		}
	}


	//Some Helpful Methods
	public void addBeesToCluster(int beesToAdd){
		departmentInfo.getCluster().addBees(beesToAdd);
	}
	public void addBeesToDepartment(int beesToAdd, Department destination){
		destination.addBees(beesToAdd);
	}
	public void killBees(int beesToKill){
		int total = getTotalBees();
		double percent = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			percent = departmentInfo.getDepartments().get(i).getNumBees() / (double)total;
			departmentInfo.getDepartments().get(i).subBees((int)(percent * beesToKill));
		}
		//If you're out of bees, your hive is dead and you lose the game (womp womp womp)
	}
	public void killPercentBees(double percentToKill){
		int total = getTotalBees();
		int beesToKill = (int) (total * .01 * percentToKill);

		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			double percent = departmentInfo.getDepartments().get(i).getNumBees() / (double)total;
			departmentInfo.getDepartments().get(i).subBees((int)(percent * beesToKill));
		}
		//If you're out of bees, your hive is dead and you lose the game (womp womp womp)
	}
	public int getTotalBees(){
		int total = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			total += departmentInfo.getDepartments().get(i).getNumBees();
		}
		return total;
	}
	//subFood() refactored using Google Gemini
	public void subFood(int initialFoodCost){
		PotentResource nectar = physicalResources.getNectar();
		PotentResource honey = physicalResources.getHoney();
		int remainingDeficit = initialFoodCost;

		remainingDeficit = attemptDrainResource(nectar, initialFoodCost);
		remainingDeficit = attemptDrainResource(honey, remainingDeficit);

		if(remainingDeficit > 0){
			int beesToKill = (int)(remainingDeficit * upgrades.get("starvationMult"));
			killBees(beesToKill);
			// Do NOT throw starvation warning here!
		}
	}
	//attemptDrainResource() created using Google Gemini
	private int attemptDrainResource(PotentResource resource, int currentDeficit){
		if(currentDeficit <= 0){ return 0; }

		int resourceFoodValue = resource.getFoodValue();

		if(currentDeficit <= resourceFoodValue){
			resource.sub(currentDeficit / resource.getPotency());
			return 0;
		}else{
			resource.setAmount(0);
			return (currentDeficit - resourceFoodValue);
		}
	}


	public boolean getGameLost(){ return gameLost; }
	public void setGameLost(boolean gameLost){ this.gameLost = gameLost; }
	public JobInfo getJobInfo(){ return jobInfo; }
	public PhysicalResources getPhysicalResources(){ return physicalResources; }
	public TemperatureInfo getTemperatureInfo(){ return temperatureInfo; }
	public DepartmentInfo getDepartmentInfo(){ return departmentInfo; }
	public WorldInfo getWorldInfo(){ return worldInfo; }
	public HashMap<String, Double> getUpgrades(){ return upgrades; }

}