package beehive.event.situationStrategies;

import beehive.Hive;

public class HeatWave implements Strategy{

    public void doOnce(int duration, Hive hive){
        hive.getJobInfo().getFannerHoney().addProdMod(duration,1.3);
        hive.getJobInfo().getBeeCreator().addProdMod(duration, .9);
        hive.getJobInfo().getGuardStrength().addProdMod(duration, .85);
    }
    public void doContinuous(Hive hive){
        hive.getTemperatureInfo().changeHiveTemp(0.3);
    }
}
