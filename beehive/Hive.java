package beehive;
//Each hive has many resources, departments, and jobs.

import java.lang.classfile.attribute.NestHostAttribute;
import java.util.HashMap;
import java.lang.Math;

import beehive.department.Department;
import beehive.department.DepartmentInfo;
import beehive.job.JobInfo;
import beehive.queen.QueenContainer;
import beehive.resource.AbstractResources;
import beehive.resource.PhysicalResources;
import beehive.resource.PotentResource;
import beehive.temperature.TemperatureInfo;
import beehive.temperature.TemperatureRegulationRanges;
import beehive.world.WorldInfo;


public class Hive{
	//Information Classes--important for constructor!
	private boolean gameLost = false;
	private PhysicalResources physicalResources;
	private AbstractResources abstractResources;
	private TemperatureInfo temperatureInfo;
	private WorldInfo worldInfo;
	private QueenContainer queenContainer;
	private DepartmentInfo departmentInfo;
	private JobInfo jobInfo;
	private HashMap<String, Double> upgrades;

		
	//A "big-boned" constructor
	public Hive(PhysicalResources physicalResources,
				AbstractResources abstractResources,
				TemperatureInfo temperatureInfo,
				WorldInfo worldInfo,
				QueenContainer queenContainer,
				DepartmentInfo departmentInfo,
				JobInfo jobInfo,
				HashMap<String, Double> upgrades){
		//Your physical resources
		this.physicalResources = physicalResources;
		//Your "abstract" resources
		this.abstractResources = abstractResources;
		//Temperature (NOT a resource!)
		this.temperatureInfo = temperatureInfo;
		this.worldInfo = worldInfo;
		//The beehive.queen.Queen
		this.queenContainer = queenContainer;
		//Your departments
		this.departmentInfo = departmentInfo;
		//Your Jobs (nomenclature: departmentResourceInfluenced)
		this.jobInfo = jobInfo;
		//Upgrade-related variables
		this.upgrades = upgrades;
	}
	

	
	//The all-powerful update method
	public void update(){
		updateWorld();
		updateJobs();
		updateTemperature();
		updateNursery();






		//LEFT OFF HERE






		starvation *= upgrades.get("foodCostMult");

		updateOccurrences(starvation);
			//Maybe throw warnings/memos?
		//Decrease beehive.queen health
		for(int i = 0; i < queenContainer.getQueens().size(); i++){
			queenContainer.updateHealth(upgrades.get("queenHealthDecMult"));
		}
		//Check if the player has lost
		if(getTotalBees() <= 0){ gameLost = true; }
	}

	private void updateJobs(){
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
		if(physicalResources.getNectar().getAmount() > jobInfo.getFannerHoney().willProduce(numFanners)){
			physicalResources.getHoney().add(jobInfo.getFannerHoney().willProduce(numFanners) / physicalResources.getHoney().getPotency());
			physicalResources.getNectar().sub(jobInfo.getFannerHoney().willProduce(numFanners));
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

	private void updateTemperature(){
		double deltaHeat = calcProductionHeat() + calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp();
		temperatureInfo.changeHiveTemp(deltaHeat);

		regulateHiveTemperature();
	}
	//updateTemperature() helper functions
	private int calcProductionHeat(){
		int heat = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			heat += departmentInfo.getDepartments().get(i).getTotalHeat();
		}
		return heat;
	}
	private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
		double deltaTemp = worldInfo.getWorldTemp() - temperatureInfo.getHiveTemp();
		deltaTemp *= .1;
		return deltaTemp;
	}
	private void regulateHiveTemperature(){
		double tempAdjustment = calcTempAdjustment();

		temperatureInfo.changeHiveTemp(tempAdjustment);
		int foodCost = calcTempRegulationFoodCost(tempAdjustment);
		subFood(foodCost);
	}
	private double calcTempAdjustment(){
		if(hiveTooHot()){
			return clampToStep(maxMinusHive());
		}else if(hiveTooCold()){
			return clampToStep(minMinusHive());
		}
		return 0;
	}
	private int calcTempRegulationFoodCost(double tempAdjustment){
		double foodCost = tempAdjustment * temperatureInfo.getTempRegulationFoodCostConstant();
		foodCost /= upgrades.get("insulation");

		return (int)(foodCost);
	}
	private boolean hiveTooHot(){
		return (temperatureInfo.getHiveTemp() > temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature());
	}
	private boolean hiveTooCold(){
		return (temperatureInfo.getHiveTemp() < temperatureInfo.getTemperatureRegulationRanges().getMinTemperature());
	}
	private double maxMinusHive() { return temperatureInfo.getTemperatureRegulationRanges().getMaxTemperature() - temperatureInfo.getHiveTemp(); }
	private double minMinusHive() { return temperatureInfo.getTemperatureRegulationRanges().getMinTemperature() - temperatureInfo.getHiveTemp(); }
	private double clampToStep(double tempChange) {
		double step = temperatureInfo.getTempRegulationStep();

		if(tempChange > step){ return step; }
		return tempChange;
	}

	//updateNursery() is nice and simple
	private void updateNursery(){
		if(insideBroodTempRange()){
			createWorkerBees();
		}
	}
	private boolean insideBroodTempRange(){
		double hiveTemp = temperatureInfo.getHiveTemp();
		double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
		double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

		if(minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp){
			return true;
		}else{
			return false;
		}
	}
	private void createWorkerBees(){
		int pollen = physicalResources.getPollen().getAmount();
		pollen /= getTotalBees();
		addBees(pollen, departmentInfo.getCluster());
	}

	//World
	private void updateWorld(){
		worldInfo.update();
	}

	//These next methods are all for updating the various occurrences
	private void updateOccurrences(int starvation){
		addStates(starvation);
		addEvents();
		addSituations();
		updateSituationTimers();
		//Maybe throw warnings/memos
	}
	private void addStates(int starvation){
		starvation *= upgrades.get("starvationMult");
		//Bad States
		if(starvation > 0){//Starving
			killBees(starvation);
		}
		if(abstractResources.getHygiene().getAmount() < abstractResources.getLowHygiene().getAmount()){//Diseased, potentially be-pested
			killBees(abstractResources.getLowHygiene().getAmount() - abstractResources.getHygiene().getAmount());
			//The number of bees killed by this is NOT tested or fine-tuned. Fix this eventually
			//chancePest is a linear equation; more hygiene = lower chance of pests
			int chancePest = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees())*abstractResources.getStrength().getAmount() + 100);
			if( chancePest >= (int)(Math.random()*100) ){
				killPercentBees(.1);
			}
		}
		if(queenContainer.getQueens().size() == 1){
			if(queenContainer.getQueens().get(0).getHealth() < abstractResources.getLowQueenHealth().getAmount()){//beehive.queen.Queen is infirm
				//All bees become less productive
				for(int i = 0; i < jobInfo.getJobs().size(); i++){
					jobInfo.getJobs().get(i).addProdMod(1, 0.85);
				}
			}
		}
		if(queenContainer.getQueens().size() != 1){//No beehive.queen or multiple queens
			//In either case, production tanks and the nursery shuts down
			for(int i = 0; i < jobInfo.getJobs().size(); i++){ jobInfo.getJobs().get(i).addProdMod(1, 0.5); }
			nurseryInfo.getNursery().shutdown();
			if(queenContainer.getQueens().size() == 0){//No beehive.queen: bees abandon the hive
				killPercentBees(.01);
			}else if(queenContainer.getQueens().size() > 1){//Multiple queens: they start killing each other
				for(int i = 0; i < queenContainer.getQueens().size(); i++){
					queenContainer.getQueens().get(i).subHealth(queenContainer.getQueenFightDamage());
					if(queenContainer.getQueens().get(i).getHealth() <= 0){
						queenContainer.getQueens().remove(i);
					}
				}
				queenContainer.incQueenFightDamage(1.1);
			}
		}else{//One beehive.queen, so queenFightDamage back to base value
			queenContainer.setQueenFightDamage(1);
		}
		if(temperatureInfo.getHiveTemp() < 55 || temperatureInfo.getHiveTemp() > 113){//Too hot or cold for bees
			killPercentBees(.08);
		}
	}
	private void addEvents(){
		//Generate a random number from 0-99
		int rand = (int)(Math.random() * 100);
		//chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
		int chanceAttacked = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees()) * (abstractResources.getStrength().getAmount() * upgrades.get("strengthMult") + 100));
		//Bad Events (catastrophe!)
		if(rand < 5){//Pesticides
			killPercentBees(.05);
		}
		if(rand < 3){//Bear attack
			killPercentBees(.5);
			physicalResources.getHoney().setAmount(0);
			physicalResources.getWax().subPercent(.9);
			physicalResources.getPollen().subPercent(.75);
			physicalResources.getNectar().subPercent(.75);
			nurseryInfo.getNursery().destroyBrood();
		}
		if(rand < 10){//Extreme heat
			temperatureInfo.incHiveTemp(1.3);
		}
		if(rand < 10){//Extreme cold
			temperatureInfo.decHiveTemp(1.3);
			jobInfo.getForagerNectar().addProdMod(30, .85);
			jobInfo.getForagerPollen().addProdMod(30, .85);
		}
		
		//Predators
		if(chanceAttacked >= rand){
			//Predators (robber bees, wasps, hornets, mice)
			int randPred = (int)(Math.random() * 4);
			if(randPred == 0){//Robber Bees
				physicalResources.getHoney().setAmount(0);
				killBees(departmentInfo.getGuard().getNumBees());
			}else if(randPred == 1){//Wasps--only in summer?
				physicalResources.getHoney().setAmount( (int)(physicalResources.getHoney().getAmount() / 2) );
				killPercentBees(.02);
				nurseryInfo.getNursery().destroyOneBrood();
			}else if(randPred == 2){//Hornets
				killPercentBees(.07);
			}else if(randPred == 3){//Mice--only in winter?
				physicalResources.getWax().subPercent(.10);
				physicalResources.getHoney().subPercent(.10);
				physicalResources.getPollen().subPercent(.10);
				physicalResources.getNectar().subPercent(.10);
			}else if(randPred == 3){//Bears--Make less common? More of 
				physicalResources.getWax().subPercent(.10);
				physicalResources.getHoney().subPercent(.10);
				physicalResources.getPollen().subPercent(.10);
				physicalResources.getNectar().subPercent(.10);
			}
		}
	}
	private void addSituations(){
		//Generate a random number from 0-99
		int rand = (int)(Math.random() * 100);
		if(rand < 10){//Bad foraging weather (stormy?)
			jobInfo.getForagerNectar().addProdMod(20, .1);
			jobInfo.getForagerPollen().addProdMod(20, .1);
		}	
	}
	private void updateSituationTimers(){
		for(int i = 0; i < jobInfo.getJobs().size(); i++){
			jobInfo.getJobs().get(i).updateMods();
		}
	}
	
	//Some Helpful Methods
	private void addBees(int beesToAdd, Department destination){
		destination.addBees(beesToAdd);
	}
	private void killBees(int beesToKill){
		int total = getTotalBees();
		double percent = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			percent = departmentInfo.getDepartments().get(i).getNumBees() / (double)total;
			departmentInfo.getDepartments().get(i).subBees((int)(percent * beesToKill));
		}
		//If you're out of bees, your hive is dead and you lose the game (womp womp womp)
	}
	private void killPercentBees(double percentToKill){
		int total = getTotalBees();
		int beesToKill = (int) (total*percentToKill);
		double percent = 0;
		for(int i = 0; i < departmentInfo.getDepartments().size(); i++){
			percent = departmentInfo.getDepartments().get(i).getNumBees() / (double)total;
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
}