package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class Superbloom implements Strategy{

    public void doOnce(int duration){
        ModuleGateway.getJobInfo().getForagerNectar().getProdMod().addMod(duration, 2.0);
        ModuleGateway.getJobInfo().getForagerPollen().getProdMod().addMod(duration, 2.0);
    }
    public void doContinuous(){
        //Nothing
    }
}
