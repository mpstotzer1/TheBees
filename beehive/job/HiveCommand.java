package beehive.job;

import beehive.Hive;

public interface HiveCommand {
    public void execute(Hive hive, double multiplier);
}
