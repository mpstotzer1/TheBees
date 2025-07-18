package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class Varroa implements Strategy {

    public void doOnce(int duration){
        ModuleGateway.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .5);
        ModuleGateway.getJobInfo().getNurseQueenHealth().getProdMod().addMod(duration, .75);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().getFoodMod().addMod(duration, 1.5);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().getHeatMod().addMod(duration, 1.5);
    }
    public void doContinuous(){
        ModuleGateway.getDepartmentInfo().killPercentBees(2.0);
    }
}
