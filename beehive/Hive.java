package beehive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.event.Situation;
import beehive.event.SituationData;
import beehive.job.HiveJobInfo;
import beehive.job.Modifiable;
import beehive.job.JobInfo;
import beehive.resource.ResourceData;
import beehive.resource.Resources;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.temperature.TemperatureInfo;
import beehive.world.WorldInfo;
import beehive.world.SeasonType;


public class Hive {
	private boolean gameLost = false;
	private Resources resources;
	ResourceData resourceData;
	private TemperatureInfo temperatureInfo;
	private WorldInfo worldInfo;
	private DepartmentInfo departmentInfo;
	private JobInfo jobInfo;
	private HiveJobInfo hiveJobInfo;
	private SituationData situationData;
	private HashMap<String, Double> upgrades;

		
	//A "big-boned" constructor
	public Hive(Resources resources,
				ResourceData resourceData,
				TemperatureInfo temperatureInfo,
				WorldInfo worldInfo,
				DepartmentInfo departmentInfo,
				JobInfo jobInfo,
				HiveJobInfo hiveJobInfo,
				SituationData situationData,
				HashMap<String, Double> upgrades){
		this.resources = resources;
		this.resourceData = resourceData;
		//Temperature (NOT a resource!)
		this.temperatureInfo = temperatureInfo;
		this.worldInfo = worldInfo;
		this.departmentInfo = departmentInfo;
		//Your Jobs (nomenclature: departmentResourceInfluenced)
		this.jobInfo = jobInfo;
		this.hiveJobInfo = hiveJobInfo;
		this.situationData = situationData;
		this.upgrades = upgrades;
	}
	

	
	public void update(){
		if(getTotalBees() <= 0){
			gameLost = true;
			return;
		}

		updateSummer();

		//Maybe throw warnings/memos?

		if(worldInfo.getSeason().getSeasonType() == SeasonType.WINTER){
			updateWinter();
		}
	}

	private void updateWinter() {

		//Speed up time (10-15x speed?)
		//Should have an on-track-to-survive-winter flag for the player the rest of the year

		//Kill all the drones
		getDepartmentInfo().getDrone().killAllBees();
		//Don't clusterAllBees! Leave them "bee" and winter update, then if survived, all bees are back in their places
		//Empty situationData.getCurrentSituations()
		getSituationData().getCurrentSituations().clear();
		//Clear all modifiers
		for(Modifiable job: getAllJobs()){
			job.getModifiers().clearAllMods();
		}
		//Essentially give the beehive a clean slate

		//Update method for winter
		//No events happen!
		//World, hive temperatures irrelevant
		//Drain all resources for seasonLength accordingly
		int durationWinter = getWorldInfo().getSeason().getDuration();
		//calculate foodCost of wintering hive (just be totalBees * someSmallConstant)
		double winterFoodCostCnst = 1.0;
		int foodCost = (int)(getTotalBees() * winterFoodCostCnst);

		//subFood() for each tick
		subFood(foodCost);

		durationWinter--;

		//Change state to Active and season to SPRING
		if(durationWinter <= 0){
			getWorldInfo().getSeason().setSeasonType(SeasonType.SPRING);
		}


		if(getTotalBees() <= 0){
			setGameLost(true);
		}
	}


	public void updateSummer(){
		updateWorld();
		updateDepartmentJobs();
		updateHiveJobs();
		updateOccurrences();
		//Maybe throw warnings/memos?

		if(getTotalBees() <= 0){
			setGameLost(true);
		}
	}

	private void updateWorld(){
		getWorldInfo().update();
		getTemperatureInfo().changeHiveTemp(calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp());
	}
	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
		double deltaTemp = getWorldInfo().getWorldTemp() - getTemperatureInfo().getHiveTemp();
		deltaTemp *= .1;
		return deltaTemp;
	}

	private void updateDepartmentJobs(){
		int foodCost = 0;
		double tempChange = 0;

		for(Department dept: getDepartmentInfo().getDepartments()){
			dept.produce();
			tempChange += dept.getTotalHeat();
			foodCost += dept.getTotalFoodCost();
		}
		//Sum foodCost and tempChange handled outside loop so that the order of departments doesn't matter
		getTemperatureInfo().changeHiveTemp(tempChange);
		subFood(foodCost);

		//Honey is converted, not produced: it must be managed manually here instead of through a department
		updateHoney();
	}
	private void updateHoney(){
		int nectarUsedByFanners = calcFannerProduction();
		int amountNectar = getResources().nectar().getAmount();
		Resource honey = getResources().honey();
		Resource nectar = getResources().nectar();
		int honeyPotency = getResources().honey().getPotency();

		if( amountNectar > nectarUsedByFanners){
			honey.add(nectarUsedByFanners / honeyPotency);
			nectar.sub(nectarUsedByFanners);
		}else if(amountNectar > 0){
			honey.add(amountNectar / honeyPotency);
			nectar.setAmount(0);
			//Throw "Unused Fanners!" warning
		}
	}
	private int calcFannerProduction(){
		int numFanners = getDepartmentInfo().getFanner().getNumBees();

		return getJobInfo().getFannerHoney().calcProduction(numFanners);
	}

	private void updateHiveJobs(){
		getHiveJobInfo().getHiveTemperatureRegulator().update(this);
		getHiveJobInfo().getBeeCreator().update(this);
	}

	private void updateOccurrences(){
		handleStates(); //State handling is based on the immediate state of the hive
		handleEvents(); //Events only last for one tick and are influenced by both hive state and random chance
		handleSituations(); //Situations endure for a time and adjust the food, heat, and production of jobs
		//Maybe throw warnings/memos?
	}
	private void handleStates(){
		checkStarvation();
		checkHygiene();
		checkQueenHealth();
		checkTemperature();
	}
	private void checkStarvation(){
		int amountNectar = getResources().nectar().getAmount();
		int amountHoney = getResources().honey().getAmount();

		if(amountNectar <= 0 && amountHoney <= 0){//Starving
			//Throw starvation warning
		}
	}
	private void checkHygiene(){
		int amountHygiene = getResources().hygiene().getAmount();
		int lowHygieneLimit = getResourceData().lowHygiene();

		if(amountHygiene < lowHygieneLimit){
			int beesToKill = lowHygieneLimit - amountHygiene;
			killBees(beesToKill);

			Logger.log(beesToKill + " bees killed via hygiene");
			//The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
		}
	}
	private void checkQueenHealth(){
		int queenHealth = getResources().queenHealth().getAmount();
		int lowQueenHealth = getResourceData().lowQueenHealth();

		if(queenHealth <= lowQueenHealth){
			addProdModToAllJobs(1, 0.85);
		}
	}
	private void checkTemperature(){
		double currentHiveTemp = getTemperatureInfo().getHiveTemp();

		if(currentHiveTemp < 55 || currentHiveTemp > 113){
			killPercentBees(3.0);

			Logger.log((getTotalBees() * .03) + " bees killed via temperature");
		}
	}
	private void addProdModToAllJobs(int duration, double modifier){
		ArrayList<Modifiable> allModifiableJobs = new ArrayList<Modifiable>();
		allModifiableJobs.addAll(getJobInfo().getJobs());
		allModifiableJobs.addAll(getHiveJobInfo().getHiveJobs());

		for(Modifiable job: allModifiableJobs){
			job.addProdMod(duration, modifier);
		}
	}

	private void handleEvents(){
		//Generate a random number from 0-99
		Random rand = new Random();
		int chanceAttacked = calcChanceAttacked();

		//Predators
		if(chanceAttacked >= rand.nextInt(0, 100)){
			//Predators
			int randPredator = rand.nextInt(0, 5);

			if(randPredator == 0){//Robber Bees
				getResources().honey().setAmount(0);
				killBees(getDepartmentInfo().getGuard().getNumBees());
			}else if(randPredator == 1){//Wasps--only in summer?
				getResources().honey().setAmount( (int)(getResources().honey().getAmount() / 2) );
				killPercentBees(2);
				getHiveJobInfo().getBeeCreator().addProdMod(40, .5);
			}else if(randPredator == 2){//Hornets
				killPercentBees(7);
			}else if(randPredator == 3){//Mice--only in winter?
				getResources().wax().subPercent(.10);
				getResources().honey().subPercent(.10);
				getResources().pollen().subPercent(.10);
				getResources().nectar().subPercent(.10);
			}else if(randPredator == 4){//Bears--Make less common? More of
				getResources().wax().subPercent(.10);
				getResources().honey().subPercent(.10);
				getResources().pollen().subPercent(.10);
				getResources().nectar().subPercent(.10);
			}

			Logger.log("Predator attacked");
		}
	}
	private int calcChanceAttacked(){
		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
		double percentGuard = getDepartmentInfo().getGuard().getNumBees() / getTotalBees();
		int amountStrength = getResources().strength().getAmount();
		double strengthMult = getUpgrades().get("strengthMult");

		return (int)(-1 * percentGuard * (amountStrength * strengthMult + 100));
	}
	//		//chancePest is a linear equation; more hygiene = lower chance of pests
//		int chancePest = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees())*resources.strength().getAmount() + 100);
//		if( chancePest >= (int)(Math.random()*100) ){
//			killPercentBees(.1);
//		}
	private void handleSituations(){
		createSituations();
		updateSituations();
	}
	private void createSituations(){
		if(situationHappened()){
			Random rand = new Random();
			int randIndex = rand.nextInt(getSituationData().getAllSituations().size());

			Situation temp = getSituationData().getAllSituations().get(randIndex);
			getSituationData().getCurrentSituations().add(temp);

			Logger.log("Situation Happened");
		}
	}
	private boolean situationHappened(){
		Random rand = new Random();
		int counter = getSituationData().getSituationCounter();

		double randNum = rand.nextDouble(0, 1.0);
		double chance = counter / 60.0;

		if(randNum <= chance){
			getSituationData().incrementSituationCounter();
			return true;
		}else{
			getSituationData().resetSituationCounter();
			return false;
		}
	}
	private void updateSituations(){
		for(Situation situation: getSituationData().getCurrentSituations()){
			situation.update(this);
		}
	}


//	private void updateWorld(){
//		worldInfo.update();
//		temperatureInfo.changeHiveTemp(calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp());
//	}
//	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
//		double deltaTemp = worldInfo.getWorldTemp() - temperatureInfo.getHiveTemp();
//		deltaTemp *= .1;
//		return deltaTemp;
//	}
//
//	private void updateDepartmentJobs(){
//		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
//			Department dept = departmentInfo.getDepartments().get(i);
//
//			dept.produce();
//			temperatureInfo.changeHiveTemp(dept.getTotalHeat());
//			subFood(dept.getTotalFoodCost());
//		}
//		//Honey is converted, not produced: it must be managed manually here instead of through a department
//		updateHoney();
//	}
//	private void updateHoney(){
//		int nectarUsedByFanners = calcFannerProduction();
//		int amountNectar = resources.nectar().getAmount();
//		Resource honey = resources.honey();
//		Resource nectar = resources.nectar();
//		int honeyPotency = resources.honey().getPotency();
//
//		if( amountNectar > nectarUsedByFanners){
//			honey.add(nectarUsedByFanners / honeyPotency);
//			nectar.sub(nectarUsedByFanners);
//		}else if(amountNectar > 0){
//			honey.add(amountNectar / honeyPotency);
//			nectar.setAmount(0);
//			//Throw "Unused Fanners!" warning
//		}
//	}
//	private int calcFannerProduction(){
//		int numFanners = departmentInfo.getFanner().getNumBees();
//
//		return jobInfo.getFannerHoney().calcProduction(numFanners);
//	}
//
//	private void updateHiveJobs(){
//		hiveJobInfo.getHiveTemperatureRegulator().update(this);
//		hiveJobInfo.getBeeCreator().update(this);
//	}
//
//	//These next methods are all for updating the various occurrences
//	private void updateOccurrences(){
//		handleStates(); //State handling is based on the immediate state of the hive
//		handleEvents(); //Events only last for one tick and are influenced by both hive state and random chance
//		handleSituations(); //Situations endure for a time and adjust the food, heat, and production of jobs
//		//Maybe throw warnings/memos?
//	}
//
//	private void handleStates(){
//		checkStarvation();
//		checkHygiene();
//		checkQueenHealth();
//		checkTemperature();
//	}
//	private void checkStarvation(){
//		int amountNectar = resources.nectar().getAmount();
//		int amountHoney = resources.honey().getAmount();
//
//		if(amountNectar <= 0 && amountHoney <= 0){//Starving
//			//Throw starvation warning
//		}
//	}
//	private void checkHygiene(){
//		int amountHygiene = resources.hygiene().getAmount();
//		int lowHygieneLimit = resourceData.lowHygiene();
//
//		if(amountHygiene < lowHygieneLimit){
//			killBees(lowHygieneLimit - amountHygiene);
//			//The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
//		}
//	}
//	private void checkQueenHealth(){
//		int queenHealth = resources.queenHealth().getAmount();
//		int lowQueenHealth = resourceData.lowQueenHealth();
//
//		if(queenHealth <= lowQueenHealth){
//			addProdModToAllJobs(1, 0.85);
//		}
//	}
//	private void checkTemperature(){
//		double currentHiveTemp = temperatureInfo.getHiveTemp();
//
//		if(currentHiveTemp < 55 || currentHiveTemp > 113){
//			killPercentBees(3.0);
//		}
//	}
//	private void addProdModToAllJobs(int duration, double modifier){
//		ArrayList<Modifiable> allModifiableJobs = new ArrayList<Modifiable>();
//		allModifiableJobs.addAll(jobInfo.getJobs());
//		allModifiableJobs.addAll(hiveJobInfo.getHiveJobs());
//
//		for(Modifiable job: allModifiableJobs){
//			job.addProdMod(duration, modifier);
//		}
//	}
//
//	private void handleEvents(){
//		//Generate a random number from 0-99
//		Random rand = new Random();
//		int chanceAttacked = calcChanceAttacked();
//
//		//Predators
//		if(chanceAttacked >= rand.nextInt(0, 100)){
//			//Predators
//			int randPredator = rand.nextInt(0, 5);
//
//			if(randPredator == 0){//Robber Bees
//				resources.honey().setAmount(0);
//				killBees(departmentInfo.getGuard().getNumBees());
//			}else if(randPredator == 1){//Wasps--only in summer?
//				resources.honey().setAmount( (int)(resources.honey().getAmount() / 2) );
//				killPercentBees(2);
//				hiveJobInfo.getBeeCreator().addProdMod(40, .5);
//			}else if(randPredator == 2){//Hornets
//				killPercentBees(7);
//			}else if(randPredator == 3){//Mice--only in winter?
//				resources.wax().subPercent(.10);
//				resources.honey().subPercent(.10);
//				resources.pollen().subPercent(.10);
//				resources.nectar().subPercent(.10);
//			}else if(randPredator == 4){//Bears--Make less common? More of
//				resources.wax().subPercent(.10);
//				resources.honey().subPercent(.10);
//				resources.pollen().subPercent(.10);
//				resources.nectar().subPercent(.10);
//			}
//		}
//	}
//	private int calcChanceAttacked(){
//		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
//		double percentGuard = departmentInfo.getGuard().getNumBees() / getTotalBees();
//		int amountStrength = resources.strength().getAmount();
//		double strengthMult = upgrades.get("strengthMult");
//
//		return (int)(-1 * percentGuard * (amountStrength * strengthMult + 100));
//	}
//
////		//chancePest is a linear equation; more hygiene = lower chance of pests
////		int chancePest = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees())*resources.strength().getAmount() + 100);
////		if( chancePest >= (int)(Math.random()*100) ){
////			killPercentBees(.1);
////		}
//	private void handleSituations(){
//		createSituations();
//		updateSituations();
//	}
//	private void createSituations(){
//		if(situationHappened()){
//			Random rand = new Random();
//			int randIndex = rand.nextInt(situationData.getAllSituations().size());
//
//			Situation temp = situationData.getAllSituations().get(randIndex);
//			situationData.getCurrentSituations().add(temp);
//		}
//	}
//	private boolean situationHappened(){
//		Random rand = new Random();
//		int counter = situationData.getSituationCounter();
//
//		double randNum = rand.nextDouble(0, 1.0);
//		double chance = counter / 60.0;
//
//		if(randNum <= chance){
//			situationData.incrementSituationCounter();
//			return true;
//		}else{
//			situationData.resetSituationCounter();
//			return false;
//		}
//	}
//	private void updateSituations(){
//		for(Situation situation: situationData.getCurrentSituations()){
//			situation.update(this);
//		}
//	}


	//Some Helpful Methods

	public void adjustBeesEverywhere(int numBees){
		int numDepartments = departmentInfo.getDepartments().size();
		int remainder = numBees % numDepartments;
		int beesToAddPerDepartment = (numBees - remainder) / numDepartments;

		for(Department dept: departmentInfo.getDepartments()){
			dept.adjustBees(beesToAddPerDepartment);
		}

		for(Department dept : departmentInfo.getDepartments()){
			if(remainder <= 0){ break; }

			dept.adjustBees(1);
			remainder--;
		}
	}
	public void addBeesToDepartment(int beesToAdd, Department destination){
		destination.adjustBees(beesToAdd);
	}
	public void killBees(int beesToKill){
		beesToKill *= -1;
		adjustBeesEverywhere(beesToKill);

		//If you're out of bees, your hive is dead and you lose the game (womp womp womp)
		//Logger.log(beesToKill + " bees killed");
	}
	public void killPercentBees(double percentToKill){
		int total = getTotalBees();
		int beesToKill = (int) (total * .01 * percentToKill);

		killBees(beesToKill);
	}
	public int getTotalBees(){
		int total = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			total += departmentInfo.getDepartments().get(i).getNumBees();
		}
		return total;
	}
	public void subFood(int initialFoodCost){
		//subFood() refactored using Google Gemini
		PotentResource nectar = resources.nectar();
		PotentResource honey = resources.honey();
		int remainingDeficit = initialFoodCost;

		remainingDeficit = attemptDrainResource(nectar, remainingDeficit);
		remainingDeficit = attemptDrainResource(honey, remainingDeficit);

		if(remainingDeficit > 0){
			int beesToKill = (int)(remainingDeficit * upgrades.get("starvationMult"));
			Logger.log(beesToKill + " bees are about to be killed via starvation");
			killBees(beesToKill);
			// Do NOT throw starvation warning here!
		}
	}
	private int attemptDrainResource(PotentResource resource, int currentDeficit){
		//attemptDrainResource() created using Google Gemini
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
	public HiveJobInfo getHiveJobInfo(){ return hiveJobInfo; }
	public Resources getResources(){ return resources; }
	public TemperatureInfo getTemperatureInfo(){ return temperatureInfo; }
	public DepartmentInfo getDepartmentInfo(){ return departmentInfo; }
	public WorldInfo getWorldInfo(){ return worldInfo; }
	public HashMap<String, Double> getUpgrades(){ return upgrades; }
	public SituationData getSituationData(){ return situationData; }
	public ResourceData getResourceData(){ return resourceData; }
	public ArrayList<Modifiable> getAllJobs(){
		ArrayList<Modifiable> allJobs = new ArrayList<Modifiable>();

		allJobs.addAll(jobInfo.getJobs());
		allJobs.addAll(hiveJobInfo.getHiveJobs());

		return allJobs;
	}

}