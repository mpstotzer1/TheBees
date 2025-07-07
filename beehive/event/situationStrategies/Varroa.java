package beehive.event.situationStrategies;

import beehive.GameLogic;
import beehive.ModuleGateway;

public class Varroa implements Strategy {

    public void doOnce(int duration, GameLogic gameLogic){
        ModuleGateway.getJobInfo().getBeeCreator().addProdMod(duration, .5);
        ModuleGateway.getJobInfo().getNurseQueenHealth().addProdMod(duration, .75);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().addFoodMod(duration, 1.5);
        ModuleGateway.getJobInfo().getHouseBeeHygiene().addHeatMod(duration, 1.5);
    }
    public void doContinuous(GameLogic gameLogic){
        ModuleGateway.getDepartmentInfo().killPercentBees(2.0);
    }
}
