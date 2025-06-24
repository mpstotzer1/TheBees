package beehive.event.situationStrategies;

import beehive.Hive;

public class MatingSeason implements Strategy{

    public void doOnce(int duration, Hive hive){
        hive.getJobInfo().getDroneXP().addProdMod(duration, 2.0);
        hive.getJobInfo().getDroneXP().addFoodMod(duration, 2.0);
        hive.getJobInfo().getDroneXP().addHeatMod(duration, 2.0);
    }
    public void doContinuous(Hive hive){
        hive.getAbstractResources().getXp().addPercent(1.0);
    }
}
