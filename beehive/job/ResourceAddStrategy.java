package beehive.job;

public class ResourceAddStrategy implements ResourceAdjustStrategy {

    public ResourceAddStrategy(){}

    public void execute(Job job, int production){
        job.getResource().add(production);
    }
}
