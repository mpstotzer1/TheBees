package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class Disease implements Strategy{

    public void doOnce(int duration){
        ModuleGateway.getJobInfo().getForagerNectar().getProdMod().addMod(duration, .8);
        ModuleGateway.getJobInfo().getForagerPollen().getProdMod().addMod(duration, .8);
        ModuleGateway.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .8);
        ModuleGateway.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .8);
    }
    public void doContinuous(){
        ModuleGateway.getDepartmentInfo().killPercentBees(.75);
    }
}
