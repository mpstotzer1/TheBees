package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class Superbloom implements Strategy{

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getForagerNectar().getProdMod().addMod(duration, 2.0);
        hiveModuleContainer.getJobInfo().getForagerPollen().getProdMod().addMod(duration, 2.0);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        //Nothing
    }
}
