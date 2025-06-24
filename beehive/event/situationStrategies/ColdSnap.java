package beehive.event.situationStrategies;

import beehive.Hive;

public class ColdSnap implements Strategy{

    public void doOnce(int duration, Hive hive){
        hive.getJobInfo().getFannerHoney().addProdMod(duration,.65);
        hive.getHiveJobInfo().getBeeCreator().addProdMod(duration, .85);
        hive.getJobInfo().getGuardStrength().addProdMod(duration, .85);
    }
    public void doContinuous(Hive hive){
        hive.getTemperatureInfo().changeHiveTemp(-0.3);
    }
}
