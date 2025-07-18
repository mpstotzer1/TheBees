package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class Pesticide extends Strategy {

    public void doOnce(int duration){
        ModuleGateway.getDepartmentInfo().killPercentBees(5.0);
        ModuleGateway.getJobInfo().getForagerNectar().getProdMod().addMod(duration, .5);
        ModuleGateway.getJobInfo().getForagerPollen().getProdMod().addMod(duration, .5);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().getProdMod().addMod(duration, .8);

    }
    public void doContinuous(){
        ModuleGateway.getDepartmentInfo().killPercentBees(1.0);
    }
}
