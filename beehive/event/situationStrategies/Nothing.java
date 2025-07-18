package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class Nothing implements Strategy {

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        //Do nothing
    }

    public void doContinuous(HiveModuleContainer hiveModuleContainer) {
        //Do nothing
    }
}
