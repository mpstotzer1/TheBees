package beehive;

import java.util.Random;

import beehive.event.Situation;
import beehive.job.Job;
import beehive.logger.Logger;
import beehive.resource.PotentResource;
import beehive.resource.Resource;
import beehive.world.SeasonType;

public class Hive {
	private boolean gameLost;
	private boolean isWinter;
	private HiveModuleContainer hiveModuleContainer;

	public Hive(HiveModuleContainer hiveModuleContainer){
		gameLost = false;
		isWinter = (hiveModuleContainer.getWorldInfo().getSeason().getSeasonType() == SeasonType.WINTER);

		this.hiveModuleContainer = hiveModuleContainer;
	}

	public void update(){
		if(hiveModuleContainer.getDepartmentInfo().getTotalBees() <= 0){
			gameLost = true;

			return;
		}

		Logger.logTemperatureDebugging("Current Hive Temp: " + hiveModuleContainer.getTemperatureInfo().getHiveTemp());

		if(isWinter){ updateWinter(); }
		else{ updateSummer(); }

		//Maybe throw warnings/memos here?
	}
	public void updateSummer(){
		updateWorld();
		updateJobs();
		updateOccurrences();
		//Maybe throw warnings/memos?

		if(hiveModuleContainer.getDepartmentInfo().getTotalBees() <= 0){
			setGameLost(true);
		}
	}

	private void updateWorld(){
		hiveModuleContainer.getWorldInfo().update();
		hiveModuleContainer.getTemperatureInfo().changeHiveTemp(calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp());
	}
	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
		double deltaTemp = hiveModuleContainer.getWorldInfo().getWorldTemp() - hiveModuleContainer.getTemperatureInfo().getHiveTemp();
		deltaTemp *= .1;

		Logger.logTemperatureDebugging("Temperature Change from Weather: " + deltaTemp);
		return deltaTemp;
	}

	private void updateJobs(){
		//The order of work, heat generation, and food subtraction matters!
		for(Job job: hiveModuleContainer.getJobInfo().getDepartmentJobs()){ job.work(); }
		hiveModuleContainer.getJobInfo().getBeeCreator().work();

		double heatGenerated = 0;
		for(Job job: hiveModuleContainer.getJobInfo().getAllJobs()){ heatGenerated += job.calcHeat(); }
		hiveModuleContainer.getTemperatureInfo().changeHiveTemp(heatGenerated);
		Logger.logTemperatureDebugging("Heat Generated: " + heatGenerated);

		int foodCost = 0;
		for(Job job: hiveModuleContainer.getJobInfo().getAllJobs()){ foodCost += job.calcFoodCost(); }
		subFood(foodCost);

		hiveModuleContainer.getJobInfo().getHiveTemperatureRegulator().work();

		updateHoney();
	}
	private void subFood(int initialFoodCost){
		//subFood() refactored using Google Gemini
		PotentResource nectar = hiveModuleContainer.getResources().nectar();
		PotentResource honey = hiveModuleContainer.getResources().honey();
		int remainingDeficit = initialFoodCost;

		remainingDeficit = attemptDrainResource(nectar, remainingDeficit);
		remainingDeficit = attemptDrainResource(honey, remainingDeficit);

		if(remainingDeficit > 0){
			int beesToKill = (int)(remainingDeficit * hiveModuleContainer.getUpgrades().starvationMult());
			hiveModuleContainer.getDepartmentInfo().killBees(beesToKill);
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
		Resource honey = hiveModuleContainer.getResources().honey();
		Resource nectar = hiveModuleContainer.getResources().nectar();
		int amountNectar = hiveModuleContainer.getResources().nectar().getAmount();
		int nectarUsedByFanners = hiveModuleContainer.getJobInfo().getFannerHoney().calcProduction();
		//Throw "Unused Fanners!" warning if fannerHoney.calcProduction() > nectar.getAmount()
		int honeyPotency = hiveModuleContainer.getResources().honey().getPotency();

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
		int amountNectar = hiveModuleContainer.getResources().nectar().getAmount();
		int amountHoney = hiveModuleContainer.getResources().honey().getAmount();

		if(amountNectar <= 0 && amountHoney <= 0){//Starving
			//Throw starvation warning
		}
	}
	private void checkHygiene(){
		int amountHygiene = hiveModuleContainer.getResources().hygiene().getAmount();
		int lowHygieneLimit = hiveModuleContainer.getResourceData().lowHygiene();

		if(amountHygiene < lowHygieneLimit){
			int beesToKill = lowHygieneLimit - amountHygiene;
			hiveModuleContainer.getDepartmentInfo().killBees(beesToKill);

			//The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
		}
	}
	private void checkQueenHealth(){
		int queenHealth = hiveModuleContainer.getResources().queenHealth().getAmount();
		int lowQueenHealth = hiveModuleContainer.getResourceData().lowQueenHealth();

		if(queenHealth <= lowQueenHealth){
			addProdModToAllJobs(1, 0.85);
		}
	}
	private void checkTemperature(){
		double currentHiveTemp = hiveModuleContainer.getTemperatureInfo().getHiveTemp();

		if(currentHiveTemp < 55 || currentHiveTemp > 113){
			hiveModuleContainer.getDepartmentInfo().killPercentBees(3.0);

			Logger.logTemperatureDebugging((hiveModuleContainer.getDepartmentInfo().getTotalBees() * .03) + " bees killed via temperature");
		}
	}
	private void addProdModToAllJobs(int duration, double modifier){
		for(Job job: hiveModuleContainer.getJobInfo().getAllJobs()){
			job.getProdMod().addMod(duration, modifier);
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
				hiveModuleContainer.getResources().honey().setAmount(0);
				hiveModuleContainer.getDepartmentInfo().killBees(hiveModuleContainer.getDepartmentInfo().getGuard().getNumBees());
			}else if(randPredator == 1){//Wasps--only in summer?
				hiveModuleContainer.getResources().honey().setAmount( (int)(hiveModuleContainer.getResources().honey().getAmount() / 2) );
				hiveModuleContainer.getDepartmentInfo().killPercentBees(2);
				hiveModuleContainer.getJobInfo().getBeeCreator().getProdMod().addMod(40, .5);
			}else if(randPredator == 2){//Hornets
				hiveModuleContainer.getDepartmentInfo().killPercentBees(7);
			}else if(randPredator == 3){//Mice--only in winter?
				hiveModuleContainer.getResources().wax().subPercent(.10);
				hiveModuleContainer.getResources().honey().subPercent(.10);
				hiveModuleContainer.getResources().pollen().subPercent(.10);
				hiveModuleContainer.getResources().nectar().subPercent(.10);
			}else if(randPredator == 4){//Bears--Make less common? More of
				hiveModuleContainer.getResources().wax().subPercent(.10);
				hiveModuleContainer.getResources().honey().subPercent(.10);
				hiveModuleContainer.getResources().pollen().subPercent(.10);
				hiveModuleContainer.getResources().nectar().subPercent(.10);
			}

		}
	}
	private int calcChanceAttacked(){
		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
		int numBees = hiveModuleContainer.getDepartmentInfo().getTotalBees();
		if(numBees == 0){ numBees = 1; }
		double percentGuard = hiveModuleContainer.getDepartmentInfo().getGuard().getNumBees() / (double)numBees;
		int amountStrength = hiveModuleContainer.getResources().strength().getAmount();
		double strengthMult = hiveModuleContainer.getUpgrades().strengthMult();

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
			int randIndex = rand.nextInt(hiveModuleContainer.getSituationData().getAllSituations().size());

			Situation temp = hiveModuleContainer.getSituationData().getAllSituations().get(randIndex);
			hiveModuleContainer.getSituationData().getCurrentSituations().add(temp);

		}
	}
	private boolean situationHappened(){
		Random rand = new Random();
		int counter = hiveModuleContainer.getSituationData().getSituationCounter();

		double randNum = rand.nextDouble(0, 1.0);
		double chance = counter / 60.0;

		if(randNum <= chance){
			hiveModuleContainer.getSituationData().incrementSituationCounter();
			return true;
		}else{
			hiveModuleContainer.getSituationData().resetSituationCounter();
			return false;
		}
	}
	private void updateSituations(){
		for(Situation situation: hiveModuleContainer.getSituationData().getCurrentSituations()){
			situation.update(hiveModuleContainer);
		}
	}

	private void updateWinter() {
		//The firstDayOfSeason conditional MUST come before the call to worldInfo().update()
		if(isFirstDayOfSeason()){
			hiveModuleContainer.getDepartmentInfo().getDrone().killAllBees();
			hiveModuleContainer.getSituationData().getCurrentSituations().clear();
			for(Job job: hiveModuleContainer.getJobInfo().getAllJobs()){ job.clearAllModifierLists(); }
			hiveModuleContainer.getResources().nectar().setAmount(0);
		}

		updateWinterFood();
		hiveModuleContainer.getWorldInfo().update();
	}
	private void updateWinterFood(){
		int totalBees = hiveModuleContainer.getDepartmentInfo().getTotalBees();
		double winterFoodCostConstant = hiveModuleContainer.getMiscData().winterFoodCost();

		int foodCost = (int)(totalBees * winterFoodCostConstant);
		subFood(foodCost);
	}
	private boolean isFirstDayOfSeason(){
		int seasonCounter = hiveModuleContainer.getWorldInfo().getSeason().getSeasonCounter();
		int seasonLength = hiveModuleContainer.getMiscData().seasonLength();

		return (seasonCounter == seasonLength);
	}
	private boolean isLastDayOfSeason(){
		int seasonCounter = hiveModuleContainer.getWorldInfo().getSeason().getSeasonCounter();

		return (seasonCounter == 1);
	}

	public boolean getGameLost(){ return gameLost; }
	public void setGameLost(boolean gameLost){ this.gameLost = gameLost; }
}