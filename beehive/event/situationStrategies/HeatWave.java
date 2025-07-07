package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class HeatWave implements Strategy{

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getFannerHoney().addProdMod(duration,1.3);
        ModuleGateway.getJobInfo().getBeeCreator().addProdMod(duration, .9);
        ModuleGateway.getJobInfo().getGuardStrength().addProdMod(duration, .85);
    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getTemperatureInfo().changeHiveTemp(0.3);
    }
}
