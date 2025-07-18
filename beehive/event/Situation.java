package beehive.event;

import beehive.HiveModuleContainer;
import beehive.event.situationStrategies.Strategy;

public class Situation {
    private boolean doneOnce;
    private int duration;
    private Strategy strategy;

    public Situation(int duration, Strategy strategy){
        this.duration = duration;
        this.strategy = strategy;

        doneOnce = false;
    }

    public final void update(HiveModuleContainer hiveModuleContainer){
        if(duration > 0){
            if(!doneOnce){
                strategy.doOnce(duration, hiveModuleContainer);
                doneOnce = true;
            }else{
                strategy.doContinuous(hiveModuleContainer);
                duration--;
            }
        }
    }

}
