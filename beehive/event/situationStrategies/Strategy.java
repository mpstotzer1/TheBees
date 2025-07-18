package beehive.event.situationStrategies;

import beehive.LogicModuleContainer;

public abstract class Strategy {
    static protected LogicModuleContainer logicModuleContainer_;

    public static void setLogicModuleContainer(LogicModuleContainer logicModuleContainer){
        logicModuleContainer_ = logicModuleContainer;
    }

    public abstract void doOnce(int duration);
    public abstract void doContinuous();
}
