package beehive.event.situationStrategies;

import beehive.Hive;

public class Pesticide implements Strategy {

    public void doOnce(int duration, Hive hive){
        hive.killPercentBees(5.0);
        hive.getJobInfo().getForagerNectar().addProdMod(duration, .5);
        hive.getJobInfo().getForagerPollen().addProdMod(duration, .5);
        hive.getJobInfo().getHouseBeeHygiene().addProdMod(duration, .8);

    }
    public void doContinuous(Hive hive){
        hive.killPercentBees(1.0);
    }
}
