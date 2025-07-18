package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class Varroa implements Strategy {

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .5);
        hiveModuleContainer.getJobInfo().getNurseQueenHealth().getProdMod().addMod(duration, .75);
        hiveModuleContainer.getJobInfo().getHouseBeeHygiene().getFoodMod().addMod(duration, 1.5);
        hiveModuleContainer.getJobInfo().getHouseBeeHygiene().getHeatMod().addMod(duration, 1.5);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getDepartmentInfo().killPercentBees(2.0);
    }
}
