package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class Pesticide implements Strategy {

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getDepartmentInfo().killPercentBees(5.0);
        hiveModuleContainer.getJobInfo().getForagerNectar().getProdMod().addMod(duration, .5);
        hiveModuleContainer.getJobInfo().getForagerPollen().getProdMod().addMod(duration, .5);
        hiveModuleContainer.getJobInfo().getHouseBeeHygiene().getProdMod().addMod(duration, .8);

    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getDepartmentInfo().killPercentBees(1.0);
    }
}
