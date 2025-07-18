package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class ColdSnap implements Strategy{

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getFannerHoney().getProdMod().addMod(duration,.65);
        hiveModuleContainer.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .85);
        hiveModuleContainer.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .85);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getTemperatureInfo().changeHiveTemp(-0.3);
    }
}
