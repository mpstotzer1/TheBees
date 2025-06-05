package beehive.job;

public interface ResourceAdjustStrategy {
    public void execute(Job job, int production);
}
