package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class ColdSnap implements Strategy{

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getFannerHoney().addProdMod(duration,.65);
        ModuleGateway.getJobInfo().getBeeCreator().addProdMod(duration, .85);
        ModuleGateway.getJobInfo().getGuardStrength().addProdMod(duration, .85);
    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getTemperatureInfo().changeHiveTemp(-0.3);
    }
}
