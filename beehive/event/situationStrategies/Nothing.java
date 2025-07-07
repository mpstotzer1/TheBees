package beehive.event.situationStrategies;

import beehive.GameLogic;

public class Nothing implements Strategy {

    public void doOnce(int duration, GameLogic gameLogic) {
        //Do nothing
    }

    public void doContinuous(GameLogic gameLogic) {
        //Do nothing
    }
}
