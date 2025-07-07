package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class Disease implements Strategy{

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getForagerNectar().addProdMod(duration, .8);
        ModuleGateway.getJobInfo().getForagerPollen().addProdMod(duration, .8);
        ModuleGateway.getJobInfo().getBeeCreator().addProdMod(duration, .8);
        ModuleGateway.getJobInfo().getGuardStrength().addProdMod(duration, .8);
    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getDepartmentInfo().killPercentBees(.75);
    }
}
