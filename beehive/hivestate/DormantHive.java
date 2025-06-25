package beehive.hivestate;

import beehive.Hive;
import beehive.job.Modifiable;
import beehive.world.SeasonType;

public class DormantHive implements HiveState {
    private Hive hive;

    public void update(Hive hive){
        this.hive = hive;

        //Speed up time (10-15x speed?)
        //Should have an on-track-to-survive-winter flag for the player the rest of the year

        //Kill all the drones
        hive.getDepartmentInfo().getDrone().killAllBees();
        //Don't clusterAllBees! Leave them "bee" and winter update, then if survived, all bees are back in their places
        //Empty situationData.getCurrentSituations()
        hive.getSituationData().getCurrentSituations().clear();
        //Clear all modifiers
        for(Modifiable job: hive.getAllJobs()){
            job.getModifiers().clearAllMods();
        }
        //Essentially give the beehive a clean slate

        //Update method for winter
        //No events happen!
        //World, hive temperatures irrelevant
        //Drain all resources for seasonLength accordingly
        int durationWinter = hive.getWorldInfo().getSeason().getDuration();
        //calculate foodCost of wintering hive (just be totalBees * someSmallConstant)
        double winterFoodCostCnst = hive.getHiveStateInfo().getHiveStateData().winterFoodCostConstant();
        int foodCost = (int)(hive.getTotalBees() * winterFoodCostCnst);

        //subFood() for each tick
        hive.subFood(foodCost);

        durationWinter--;

        //Change state to Active and season to SPRING
        if(durationWinter <= 0){
            hive.getWorldInfo().getSeason().setSeasonType(SeasonType.SPRING);
            hive.getHiveStateInfo().setHiveState(hive.getHiveStateInfo().getActiveState());
        }


        if(hive.getTotalBees() <= 0){
            hive.setGameLost(true);
        }
    }

}
