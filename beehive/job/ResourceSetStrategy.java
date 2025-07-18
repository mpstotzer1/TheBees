package beehive.job;

import beehive.resource.Resource;

public class ResourceSetStrategy implements ResourceAdjustStrategy {

    public ResourceSetStrategy(){}

    public void execute(Resource resource, int amount){
        resource.setAmount(amount);
    }
}
