package beehive.event.situationStrategies;

import beehive.Hive;

public class Superbloom implements Strategy{

    public void doOnce(int duration, Hive hive){
        hive.getJobInfo().getForagerNectar().addProdMod(duration, 2.0);
        hive.getJobInfo().getForagerPollen().addProdMod(duration, 2.0);
    }
    public void doContinuous(Hive hive){
        //Nothing
    }
}
