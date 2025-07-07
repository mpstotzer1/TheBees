package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class MatingSeason implements Strategy{

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getDroneXP().addProdMod(duration, 2.0);
        ModuleGateway.getJobInfo().getDroneXP().addFoodMod(duration, 2.0);
        ModuleGateway.getJobInfo().getDroneXP().addHeatMod(duration, 2.0);
    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getResources().xp().addPercent(1.0);
    }
}
