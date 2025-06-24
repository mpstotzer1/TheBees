package beehive.job;

import beehive.Hive;

public class DroneXP extends Modifiable{
    private Hive hive;

    public DroneXP(double foodCostConstant, double heatConstant, double productionConstant){
        modifiers = new Modifiers(foodCostConstant, heatConstant, productionConstant);
    }

    public void update(Hive hive){
        this.hive = hive;


    }

}
