package beehive.job;

import beehive.resource.Resource;

public class ResourceAddStrategy implements ResourceAdjustStrategy {

    public ResourceAddStrategy(){}

    public void execute(Resource resource, int production){
        resource.add(production);
    }
}
