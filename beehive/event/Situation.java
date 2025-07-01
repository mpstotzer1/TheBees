package beehive.event;

import beehive.Hive;
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

    public final void update(Hive hive){
        if(duration > 0){
            if(!doneOnce){
                strategy.doOnce(duration, hive);
                doneOnce = true;
            }else{
                strategy.doContinuous(hive);
                duration--;
            }
        }
    }

}
