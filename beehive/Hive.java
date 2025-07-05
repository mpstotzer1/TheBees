package beehive;

import java.util.HashMap;
import java.util.Random;

import beehive.department.DepartmentInfo;
import beehive.event.Situation;
import beehive.event.SituationData;
import beehive.job.Job;
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
	private ResourceData resourceData;
	private TemperatureInfo temperatureInfo;
	private WorldInfo worldInfo;
	private DepartmentInfo departmentInfo;
	private JobInfo jobInfo;
	private SituationData situationData;
	private HashMap<String, Double> upgrades;
	private MiscData miscData;

		
	//A "big-boned" constructor
	public Hive(Resources resources,
				ResourceData resourceData,
				TemperatureInfo temperatureInfo,
				WorldInfo worldInfo,
				DepartmentInfo departmentInfo,
				JobInfo jobInfo,
				SituationData situationData,
				HashMap<String, Double> upgrades,
				MiscData miscData) {
		this.resources = resources;
		this.resourceData = resourceData;
		//Temperature (NOT a resource!)
		this.temperatureInfo = temperatureInfo;
		this.worldInfo = worldInfo;
		this.departmentInfo = departmentInfo;
		//Your Jobs (nomenclature: departmentResourceInfluenced)
		this.jobInfo = jobInfo;
		this.situationData = situationData;
		this.upgrades = upgrades;
		this.miscData = miscData;
	}
	

	
	public void update(){
		if(departmentInfo.getTotalBees() <= 0){
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

//		//Speed up time (10-15x speed?)
//		//Should have an on-track-to-survive-winter flag for the player the rest of the year
//
//		//Kill all the drones
//		getDepartmentInfo().getDrone().killAllBees();
//		//Don't clusterAllBees! Leave them "bee" and winter update, then if survived, all bees are back in their places
//		//Empty situationData.getCurrentSituations()
//		getSituationData().getCurrentSituations().clear();
//		//Clear all modifiers
//		for(Modifiable job: getAllJobs()){
//			job.getModifiers().clearAllMods();
//		}
//		//Essentially give the beehive a clean slate
//
//		//Update method for winter
//		//No events happen!
//		//World, hive temperatures irrelevant
//		//Drain all resources for seasonLength accordingly
//		int durationWinter = getWorldInfo().getSeason().getDuration();
//		//calculate foodCost of wintering hive (just be totalBees * someSmallConstant)
//		double winterFoodCostCnst = 1.0;
//		int foodCost = (int)(getTotalBees() * winterFoodCostCnst);
//
//		//subFood() for each tick
//		subFood(foodCost);
//
//		durationWinter--;
//
//		//Change state to Active and season to SPRING
//		if(durationWinter <= 0){
//			getWorldInfo().getSeason().setSeasonType(SeasonType.SPRING);
//		}
//
//
//		if(getTotalBees() <= 0){
//			setGameLost(true);
//		}
	}

	public void updateSummer(){
		updateWorld();
		updateJobs();
		updateOccurrences();
		//Maybe throw warnings/memos?

		if(departmentInfo.getTotalBees() <= 0){
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

	private void updateJobs(){
		int foodCost = 0;
		double heatGenerated = 0;

		//The order of work, heat generation, and food subtraction matters!
		for(Job job: jobInfo.getDepartmentJobs()){ job.work(); }
		jobInfo.getBeeCreator().work();

		for(Job job: jobInfo.getAllJobs()){ heatGenerated += job.calcHeat(); }
		temperatureInfo.changeHiveTemp(heatGenerated);

		for(Job job: jobInfo.getAllJobs()){ foodCost += job.calcFoodCost(); }
		subFood(foodCost);

		jobInfo.getHiveTemperatureRegulator().work();

		updateHoney();
	}
	private void subFood(int initialFoodCost){
		//subFood() refactored using Google Gemini
		PotentResource nectar = resources.nectar();
		PotentResource honey = resources.honey();
		int remainingDeficit = initialFoodCost;

		remainingDeficit = attemptDrainResource(nectar, remainingDeficit);
		remainingDeficit = attemptDrainResource(honey, remainingDeficit);

		if(remainingDeficit > 0){
			int beesToKill = (int)(remainingDeficit * upgrades.get("starvationMult"));
			Logger.log(beesToKill + " bees are about to be killed via starvation");
			departmentInfo.killBees(beesToKill);
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
	private void updateHoney(){
		Resource honey = getResources().honey();
		Resource nectar = getResources().nectar();
		int amountNectar = getResources().nectar().getAmount();
		int nectarUsedByFanners = jobInfo.getFannerHoney().calcProduction();
		//Throw "Unused Fanners!" warning if fannerHoney.calcProduction() > nectar.getAmount()
		int honeyPotency = getResources().honey().getPotency();

		int nectarDrained = Math.min(amountNectar, nectarUsedByFanners);

		honey.add(nectarDrained / honeyPotency);
		nectar.sub(nectarDrained);
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
			departmentInfo.killBees(beesToKill);

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
			departmentInfo.killPercentBees(3.0);

			Logger.log((departmentInfo.getTotalBees() * .03) + " bees killed via temperature");
		}
	}
	private void addProdModToAllJobs(int duration, double modifier){
		for(Job job: jobInfo.getAllJobs()){
			job.getModifiers().addProdMod(duration, modifier);
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
				departmentInfo.killBees(getDepartmentInfo().getGuard().getNumBees());
			}else if(randPredator == 1){//Wasps--only in summer?
				getResources().honey().setAmount( (int)(getResources().honey().getAmount() / 2) );
				departmentInfo.killPercentBees(2);
				jobInfo.getBeeCreator().getModifiers().addProdMod(40, .5);
			}else if(randPredator == 2){//Hornets
				departmentInfo.killPercentBees(7);
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
		double percentGuard = getDepartmentInfo().getGuard().getNumBees() / departmentInfo.getTotalBees();
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


	public boolean getGameLost(){ return gameLost; }
	public void setGameLost(boolean gameLost){ this.gameLost = gameLost; }
	public JobInfo getJobInfo(){ return jobInfo; }
	public Resources getResources(){ return resources; }
	public TemperatureInfo getTemperatureInfo(){ return temperatureInfo; }
	public DepartmentInfo getDepartmentInfo(){ return departmentInfo; }
	public WorldInfo getWorldInfo(){ return worldInfo; }
	public HashMap<String, Double> getUpgrades(){ return upgrades; }
	public SituationData getSituationData(){ return situationData; }
	public ResourceData getResourceData(){ return resourceData; }
	public MiscData getMiscData(){ return miscData; }
}