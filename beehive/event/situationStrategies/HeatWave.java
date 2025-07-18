package beehive.event.situationStrategies;

import beehive.ModuleGateway;

public class HeatWave implements Strategy{

    public void doOnce(int duration){
        ModuleGateway.getJobInfo().getFannerHoney().getProdMod().addMod(duration,1.3);
        ModuleGateway.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .9);
        ModuleGateway.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .85);
    }
    public void doContinuous(){
        ModuleGateway.getTemperatureInfo().changeHiveTemp(0.3);
    }
}
