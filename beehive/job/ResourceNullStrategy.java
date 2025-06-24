package beehive.job;

import beehive.resource.Resource;

public class ResourceNullStrategy implements ResourceAdjustStrategy {

    public ResourceNullStrategy(){}

    public void execute(Resource resource, int production){
        //Do nothing
    }
}
