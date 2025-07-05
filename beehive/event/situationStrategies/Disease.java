package beehive.event.situationStrategies;

import beehive.Hive;

public class Disease implements Strategy{

    public void doOnce(int duration, Hive hive){
        hive.getJobInfo().getForagerNectar().addProdMod(duration, .8);
        hive.getJobInfo().getForagerPollen().addProdMod(duration, .8);
        hive.getJobInfo().getBeeCreator().addProdMod(duration, .8);
        hive.getJobInfo().getGuardStrength().addProdMod(duration, .8);
    }
    public void doContinuous(Hive hive){
        hive.getDepartmentInfo().killPercentBees(.75);
    }
}
