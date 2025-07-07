package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class Pesticide implements Strategy {

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getDepartmentInfo().killPercentBees(5.0);
        ModuleGateway.getJobInfo().getForagerNectar().addProdMod(duration, .5);
        ModuleGateway.getJobInfo().getForagerPollen().addProdMod(duration, .5);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().addProdMod(duration, .8);

    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getDepartmentInfo().killPercentBees(1.0);
    }
}
