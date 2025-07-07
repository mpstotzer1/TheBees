package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class Superbloom implements Strategy{

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getForagerNectar().addProdMod(duration, 2.0);
        ModuleGateway.getJobInfo().getForagerPollen().addProdMod(duration, 2.0);
    }
    public void doContinuous(GameLogic gameLogic){
        //Nothing
    }
}
