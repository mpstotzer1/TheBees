package beehive.job;

public class ResourceSetStrategy implements ResourceAdjustStrategy {

    public ResourceSetStrategy(){}

    public void execute(Job job, int production){
        job.getResource().setAmount(production);
    }
}
