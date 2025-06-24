package beehive.event.situationStrategies;

import beehive.Hive;

public class Nothing implements Strategy {

    public void doOnce(int duration, Hive hive) {
        //Do nothing
    }

    public void doContinuous(Hive hive) {
        //Do nothing
    }
}
