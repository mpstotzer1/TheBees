package beehive;

public class DormantHive implements HiveState {
    public void update(Hive hive){
            //Should have an on-track-to-survive-winter flag for the player the rest of the year

            //Kill all the drones (drones shouldn't even be a department job--should be a hive job)

            //Don't clusterAllBees! Leave them "bee" and winter update, then if survived, all bees are back in their places
            //Empty situationData.getCurrentSituations()
            //Empty all modifiers
            //Essentially give the beehive a clean slate

            //Update method for winter
            //No events happen!
            //World, hive temperatures irrelevant
            //Drain all resources for seasonLength accordingly
            //Speed up time (10-15x speed?)
            //Calculate foodCost for each tick
            //The only foodCost comes from the cluster
            //No autoregulation jobs, no department jobs
            //Temp regulation rolled into foodCost for cluster "job"
            //World temperature irrelevant
            //subFood() for each tick

            //Change state to SPRING

    }
        //if(getTotalBees() <= 0){ gameLost = true; }


    public void display(){
        //code here
    }
}
