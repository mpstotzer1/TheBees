package beehive.job;

import beehive.resource.Resource;

public interface ResourceAdjustStrategy {
    public void execute(Resource resource, int amount);
}
