package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class HeatWave implements Strategy{

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getFannerHoney().getProdMod().addMod(duration,1.3);
        hiveModuleContainer.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .9);
        hiveModuleContainer.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .85);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getTemperatureInfo().changeHiveTemp(0.3);
    }
}
