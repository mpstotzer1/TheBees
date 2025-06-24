package beehive.event.situationStrategies;

import beehive.Hive;

public interface Strategy {
    public void doOnce(int duration, Hive hive);
    public void doContinuous(Hive hive);
}
