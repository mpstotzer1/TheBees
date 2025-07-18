package beehive.event;

import beehive.GameLogic;
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

    public final void update(GameLogic gameLogic){
        if(duration > 0){
            if(!doneOnce){
                strategy.doOnce(duration);
                doneOnce = true;
            }else{
                strategy.doContinuous();
                duration--;
            }
        }
    }

}
