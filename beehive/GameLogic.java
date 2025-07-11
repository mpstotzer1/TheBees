package beehive;

import java.util.Random;

import beehive.event.Situation;
import beehive.job.Job;
import beehive.logger.Logger;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.world.SeasonType;

public class GameLogic {
	private boolean gameLost;

	public GameLogic(){
		gameLost = false;
	}


	public void update(){
		if(ModuleGateway.getDepartmentInfo().getTotalBees() <= 0){
			gameLost = true;
			return;
		}

		updateSummer();

		//Maybe throw warnings/memos?

		if(ModuleGateway.getWorldInfo().getSeason().getSeasonType() == SeasonType.WINTER){
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

		if(ModuleGateway.getDepartmentInfo().getTotalBees() <= 0){
			setGameLost(true);
		}
	}

	private void updateWorld(){
		ModuleGateway.getWorldInfo().update();
		ModuleGateway.getTemperatureInfo().changeHiveTemp(calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp());
	}
	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
		double deltaTemp = ModuleGateway.getWorldInfo().getWorldTemp() - ModuleGateway.getTemperatureInfo().getHiveTemp();
		deltaTemp *= .1;
		Logger.logTemperatureDebugging("Temperature Change from Weather: " + deltaTemp);
		return deltaTemp;
	}

	private void updateJobs(){
		//The order of work, heat generation, and food subtraction matters!
		for(Job job: ModuleGateway.getJobInfo().getDepartmentJobs()){ job.work(); }
		ModuleGateway.getJobInfo().getBeeCreator().work();

		double heatGenerated = 0;
		for(Job job: ModuleGateway.getJobInfo().getAllJobs()){ heatGenerated += job.calcHeat(); }
		ModuleGateway.getTemperatureInfo().changeHiveTemp(heatGenerated);
		Logger.logTemperatureDebugging("Heat Generated: " + heatGenerated);

		int foodCost = 0;
		for(Job job: ModuleGateway.getJobInfo().getAllJobs()){ foodCost += job.calcFoodCost(); }
		subFood(foodCost);

		ModuleGateway.getJobInfo().getHiveTemperatureRegulator().work();

		updateHoney();
	}
	private void subFood(int initialFoodCost){
		//subFood() refactored using Google Gemini
		PotentResource nectar = ModuleGateway.getResources().nectar();
		PotentResource honey = ModuleGateway.getResources().honey();
		int remainingDeficit = initialFoodCost;

		remainingDeficit = attemptDrainResource(nectar, remainingDeficit);
		remainingDeficit = attemptDrainResource(honey, remainingDeficit);

		if(remainingDeficit > 0){
			int beesToKill = (int)(remainingDeficit * ModuleGateway.getUpgrades().get("starvationMult"));
			ModuleGateway.getDepartmentInfo().killBees(beesToKill);
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
		Resource honey = ModuleGateway.getResources().honey();
		Resource nectar = ModuleGateway.getResources().nectar();
		int amountNectar = ModuleGateway.getResources().nectar().getAmount();
		int nectarUsedByFanners = ModuleGateway.getJobInfo().getFannerHoney().calcProduction();
		//Throw "Unused Fanners!" warning if fannerHoney.calcProduction() > nectar.getAmount()
		int honeyPotency = ModuleGateway.getResources().honey().getPotency();

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
		int amountNectar = ModuleGateway.getResources().nectar().getAmount();
		int amountHoney = ModuleGateway.getResources().honey().getAmount();

		if(amountNectar <= 0 && amountHoney <= 0){//Starving
			//Throw starvation warning
		}
	}
	private void checkHygiene(){
		int amountHygiene = ModuleGateway.getResources().hygiene().getAmount();
		int lowHygieneLimit = ModuleGateway.getResourceData().lowHygiene();

		if(amountHygiene < lowHygieneLimit){
			int beesToKill = lowHygieneLimit - amountHygiene;
			ModuleGateway.getDepartmentInfo().killBees(beesToKill);

			//The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
		}
	}
	private void checkQueenHealth(){
		int queenHealth = ModuleGateway.getResources().queenHealth().getAmount();
		int lowQueenHealth = ModuleGateway.getResourceData().lowQueenHealth();

		if(queenHealth <= lowQueenHealth){
			addProdModToAllJobs(1, 0.85);
		}
	}
	private void checkTemperature(){
		double currentHiveTemp = ModuleGateway.getTemperatureInfo().getHiveTemp();

		if(currentHiveTemp < 55 || currentHiveTemp > 113){
			ModuleGateway.getDepartmentInfo().killPercentBees(3.0);

			Logger.logTemperatureDebugging((ModuleGateway.getDepartmentInfo().getTotalBees() * .03) + " bees killed via temperature");
		}
	}
	private void addProdModToAllJobs(int duration, double modifier){
		for(Job job: ModuleGateway.getJobInfo().getAllJobs()){
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
				ModuleGateway.getResources().honey().setAmount(0);
				ModuleGateway.getDepartmentInfo().killBees(ModuleGateway.getDepartmentInfo().getGuard().getNumBees());
			}else if(randPredator == 1){//Wasps--only in summer?
				ModuleGateway.getResources().honey().setAmount( (int)(ModuleGateway.getResources().honey().getAmount() / 2) );
				ModuleGateway.getDepartmentInfo().killPercentBees(2);
				ModuleGateway.getJobInfo().getBeeCreator().getModifiers().addProdMod(40, .5);
			}else if(randPredator == 2){//Hornets
				ModuleGateway.getDepartmentInfo().killPercentBees(7);
			}else if(randPredator == 3){//Mice--only in winter?
				ModuleGateway.getResources().wax().subPercent(.10);
				ModuleGateway.getResources().honey().subPercent(.10);
				ModuleGateway.getResources().pollen().subPercent(.10);
				ModuleGateway.getResources().nectar().subPercent(.10);
			}else if(randPredator == 4){//Bears--Make less common? More of
				ModuleGateway.getResources().wax().subPercent(.10);
				ModuleGateway.getResources().honey().subPercent(.10);
				ModuleGateway.getResources().pollen().subPercent(.10);
				ModuleGateway.getResources().nectar().subPercent(.10);
			}

		}
	}
	private int calcChanceAttacked(){
		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
		double percentGuard = ModuleGateway.getDepartmentInfo().getGuard().getNumBees() / ModuleGateway.getDepartmentInfo().getTotalBees();
		int amountStrength = ModuleGateway.getResources().strength().getAmount();
		double strengthMult = ModuleGateway.getUpgrades().get("strengthMult");

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
			int randIndex = rand.nextInt(ModuleGateway.getSituationData().getAllSituations().size());

			Situation temp = ModuleGateway.getSituationData().getAllSituations().get(randIndex);
			ModuleGateway.getSituationData().getCurrentSituations().add(temp);

		}
	}
	private boolean situationHappened(){
		Random rand = new Random();
		int counter = ModuleGateway.getSituationData().getSituationCounter();

		double randNum = rand.nextDouble(0, 1.0);
		double chance = counter / 60.0;

		if(randNum <= chance){
			ModuleGateway.getSituationData().incrementSituationCounter();
			return true;
		}else{
			ModuleGateway.getSituationData().resetSituationCounter();
			return false;
		}
	}
	private void updateSituations(){
		for(Situation situation: ModuleGateway.getSituationData().getCurrentSituations()){
			situation.update(this);
		}
	}

	public boolean getGameLost(){ return gameLost; }
	public void setGameLost(boolean gameLost){ this.gameLost = gameLost; }
}