package beehive.job;

import beehive.resource.Resource;

public class ResourceAddStrategy implements ResourceAdjustStrategy {

    public ResourceAddStrategy(){}

    public void execute(Resource resource, int amount){
        resource.add(amount);
    }
}
