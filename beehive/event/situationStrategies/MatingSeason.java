package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class MatingSeason implements Strategy{

    public void doOnce(int duration){
        ModuleGateway.getJobInfo().getDroneXP().getProdMod().addMod(duration, 2.0);
        ModuleGateway.getJobInfo().getDroneXP().getFoodMod().addMod(duration, 2.0);
        ModuleGateway.getJobInfo().getDroneXP().getHeatMod().addMod(duration, 2.0);
    }
    public void doContinuous(){
        ModuleGateway.getResources().xp().addPercent(1.0);
    }
}
