//package beehive.hivestate;
//
////Consider: use for service locator?
//
//import beehive.Hive;
//import beehive.Logger;
//import beehive.department.Department;
//import beehive.event.Situation;
//import beehive.job.Modifiable;
//import beehive.resource.Resource;
//import beehive.world.SeasonType;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class ActiveHive implements HiveState{
//    private Hive hive;
//
//    public void update(Hive hive){
//        this.hive = hive;
//
//        updateWorld();
//        updateDepartmentJobs();
//        updateHiveJobs();
//        updateOccurrences();
//        //Maybe throw warnings/memos?
//
//
//        if(hive.getWorldInfo().getSeason().getSeasonType() == SeasonType.WINTER){
//            hive.getHiveStateInfo().setHiveState(hive.getHiveStateInfo().getDormantState());
//        }
//
//        if(hive.getTotalBees() <= 0){
//            hive.setGameLost(true);
//        }
//    }
//
//    private void updateWorld(){
//        hive.getWorldInfo().update();
//        hive.getTemperatureInfo().changeHiveTemp(calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp());
//    }
//    private double calcHowMuchHiveTempChangesInResponseToCurrentWorldTemp(){
//        double deltaTemp = hive.getWorldInfo().getWorldTemp() - hive.getTemperatureInfo().getHiveTemp();
//        deltaTemp *= .1;
//        return deltaTemp;
//    }
//
//    private void updateDepartmentJobs(){
//        for(int i = 0; i < hive.getDepartmentInfo().getDepartments().size(); i++){
//            Department dept = hive.getDepartmentInfo().getDepartments().get(i);
//
//            dept.work();
//            hive.getTemperatureInfo().changeHiveTemp(dept.getTotalHeat());
//            hive.subFood(dept.getTotalFoodCost());
//        }
//        //Honey is converted, not produced: it must be managed manually here instead of through a department
//        updateHoney();
//    }
//    private void updateHoney(){
//        int nectarUsedByFanners = calcFannerProduction();
//        int amountNectar = hive.getResources().nectar().getAmount();
//        Resource honey = hive.getResources().honey();
//        Resource nectar = hive.getResources().nectar();
//        int honeyPotency = hive.getResources().honey().getPotency();
//
//        if( amountNectar > nectarUsedByFanners){
//            honey.add(nectarUsedByFanners / honeyPotency);
//            nectar.sub(nectarUsedByFanners);
//        }else if(amountNectar > 0){
//            honey.add(amountNectar / honeyPotency);
//            nectar.setAmount(0);
//            //Throw "Unused Fanners!" warning
//        }
//    }
//    private int calcFannerProduction(){
//        int numFanners = hive.getDepartmentInfo().getFanner().getNumBees();
//
//        return hive.getJobInfo().getFannerHoney().calcProduction(numFanners);
//    }
//
//    private void updateHiveJobs(){
//        hive.getHiveJobInfo().getHiveTemperatureRegulator().update(hive);
//        hive.getHiveJobInfo().getBeeCreator().update(hive);
//    }
//
//    private void updateOccurrences(){
//        handleStates(); //State handling is based on the immediate state of the hive
//        handleEvents(); //Events only last for one tick and are influenced by both hive state and random chance
//        handleSituations(); //Situations endure for a time and adjust the food, heat, and production of jobs
//        //Maybe throw warnings/memos?
//    }
//    private void handleStates(){
//        checkStarvation();
//        checkHygiene();
//        checkQueenHealth();
//        checkTemperature();
//    }
//    private void checkStarvation(){
//        int amountNectar = hive.getResources().nectar().getAmount();
//        int amountHoney = hive.getResources().honey().getAmount();
//
//        if(amountNectar <= 0 && amountHoney <= 0){//Starving
//            //Throw starvation warning
//        }
//    }
//    private void checkHygiene(){
//        int amountHygiene = hive.getResources().hygiene().getAmount();
//        int lowHygieneLimit = hive.getResourceData().lowHygiene();
//
//        if(amountHygiene < lowHygieneLimit){
//            int beesToKill = lowHygieneLimit - amountHygiene;
//            hive.killBees(beesToKill);
//
//            Logger.log(beesToKill + " bees killed via hygiene");
//            //The number of bees killed by this is NOT tested or fine-tuned. Fix this after testing
//        }
//    }
//    private void checkQueenHealth(){
//        int queenHealth = hive.getResources().queenHealth().getAmount();
//        int lowQueenHealth = hive.getResourceData().lowQueenHealth();
//
//        if(queenHealth <= lowQueenHealth){
//            addProdModToAllJobs(1, 0.85);
//        }
//    }
//    private void checkTemperature(){
//        double currentHiveTemp = hive.getTemperatureInfo().getHiveTemp();
//
//        if(currentHiveTemp < 55 || currentHiveTemp > 113){
//            hive.killPercentBees(3.0);
//
//            Logger.log((hive.getTotalBees() * 3.0) + " bees killed via temperature");
//        }
//    }
//    private void addProdModToAllJobs(int duration, double modifier){
//        ArrayList<Modifiable> allModifiableJobs = new ArrayList<Modifiable>();
//        allModifiableJobs.addAll(hive.getJobInfo().getJobs());
//        allModifiableJobs.addAll(hive.getHiveJobInfo().getHiveJobs());
//
//        for(Modifiable job: allModifiableJobs){
//            job.addProdMod(duration, modifier);
//        }
//    }
//
//    private void handleEvents(){
//        //Generate a random number from 0-99
//        Random rand = new Random();
//        int chanceAttacked = calcChanceAttacked();
//
//        //Predators
//        if(chanceAttacked >= rand.nextInt(0, 100)){
//            //Predators
//            int randPredator = rand.nextInt(0, 5);
//
//            if(randPredator == 0){//Robber Bees
//                hive.getResources().honey().setAmount(0);
//                hive.killBees(hive.getDepartmentInfo().getGuard().getNumBees());
//            }else if(randPredator == 1){//Wasps--only in summer?
//                hive.getResources().honey().setAmount( (int)(hive.getResources().honey().getAmount() / 2) );
//                hive.killPercentBees(2);
//                hive.getHiveJobInfo().getBeeCreator().addProdMod(40, .5);
//            }else if(randPredator == 2){//Hornets
//                hive.killPercentBees(7);
//            }else if(randPredator == 3){//Mice--only in winter?
//                hive.getResources().wax().subPercent(.10);
//                hive.getResources().honey().subPercent(.10);
//                hive.getResources().pollen().subPercent(.10);
//                hive.getResources().nectar().subPercent(.10);
//            }else if(randPredator == 4){//Bears--Make less common? More of
//                hive.getResources().wax().subPercent(.10);
//                hive.getResources().honey().subPercent(.10);
//                hive.getResources().pollen().subPercent(.10);
//                hive.getResources().nectar().subPercent(.10);
//            }
//
//            Logger.log("Predator attacked");
//        }
//    }
//    private int calcChanceAttacked(){
//        //chanceAttacked is a linear equation; more strength and higher guardBees-to-totalBees = less chance of attack
//        double percentGuard = hive.getDepartmentInfo().getGuard().getNumBees() / hive.getTotalBees();
//        int amountStrength = hive.getResources().strength().getAmount();
//        double strengthMult = hive.getUpgrades().get("strengthMult");
//
//        return (int)(-1 * percentGuard * (amountStrength * strengthMult + 100));
//    }
//    //		//chancePest is a linear equation; more hygiene = lower chance of pests
////		int chancePest = (int)(-1*(departmentInfo.getGuard().getNumBees() / getTotalBees())*resources.strength().getAmount() + 100);
////		if( chancePest >= (int)(Math.random()*100) ){
////			killPercentBees(.1);
////		}
//    private void handleSituations(){
//        createSituations();
//        updateSituations();
//    }
//    private void createSituations(){
//        if(situationHappened()){
//            Random rand = new Random();
//            int randIndex = rand.nextInt(hive.getSituationData().getAllSituations().size());
//
//            Situation temp = hive.getSituationData().getAllSituations().get(randIndex);
//            hive.getSituationData().getCurrentSituations().add(temp);
//
//            Logger.log("Situation Happened");
//        }
//    }
//    private boolean situationHappened(){
//        Random rand = new Random();
//        int counter = hive.getSituationData().getSituationCounter();
//
//        double randNum = rand.nextDouble(0, 1.0);
//        double chance = counter / 60.0;
//
//        if(randNum <= chance){
//            hive.getSituationData().incrementSituationCounter();
//            return true;
//        }else{
//            hive.getSituationData().resetSituationCounter();
//            return false;
//        }
//    }
//    private void updateSituations(){
//        for(Situation situation: hive.getSituationData().getCurrentSituations()){
//            situation.update(hive);
//        }
//    }
//}