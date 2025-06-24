package beehive.event.situationStrategies;

import beehive.Hive;

public class Varroa implements Strategy {

    public void doOnce(int duration, Hive hive){
        hive.getHiveJobInfo().getBeeCreator().addProdMod(duration, .5);
        hive.getJobInfo().getNurseQueenHealth().addProdMod(duration, .75);
        hive.getJobInfo().getHouseBeeHygiene().addFoodMod(duration, 1.5);
        hive.getJobInfo().getHouseBeeHygiene().addHeatMod(duration, 1.5);
    }
    public void doContinuous(Hive hive){
        hive.killPercentBees(2.0);
    }
}
