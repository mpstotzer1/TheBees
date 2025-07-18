package beehive.event.situationStrategies;

import beehive.HiveModuleContainer;

public class MatingSeason implements Strategy{

    public void doOnce(int duration, HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getJobInfo().getDroneXP().getProdMod().addMod(duration, 2.0);
        hiveModuleContainer.getJobInfo().getDroneXP().getFoodMod().addMod(duration, 2.0);
        hiveModuleContainer.getJobInfo().getDroneXP().getHeatMod().addMod(duration, 2.0);
    }
    public void doContinuous(HiveModuleContainer hiveModuleContainer){
        hiveModuleContainer.getResources().xp().addPercent(1.0);
    }
}
