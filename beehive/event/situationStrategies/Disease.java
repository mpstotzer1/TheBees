package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class Disease implements Strategy{

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getForagerNectar().getProdMod().addMod(duration, .8);
        hiveModuleContainer.getJobInfo().getForagerPollen().getProdMod().addMod(duration, .8);
        hiveModuleContainer.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .8);
        hiveModuleContainer.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .8);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getDepartmentInfo().killPercentBees(.75);
    }
}
