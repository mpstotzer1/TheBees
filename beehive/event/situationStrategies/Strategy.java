package beehive.event.situationStrategies;

import beehive.GameLogic;

public interface Strategy {
    public void doOnce(int duration, GameLogic gameLogic);
    public void doContinuous(GameLogic gameLogic);
}
