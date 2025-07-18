package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public interface Strategy {
    public abstract void doOnce(int duration, HiveModuleContainer hiveModuleContainer);
    public abstract void doContinuous(HiveModuleContainer hiveModuleContainer);
}
