package beehive.event.situationStrategies;

public class ColdSnap extends Strategy{

    public void doOnce(int duration){
        logicModuleContainer_.getJobInfo().getFannerHoney().getProdMod().addMod(duration,.65);
        logicModuleContainer_.getJobInfo().getBeeCreator().getProdMod().addMod(duration, .85);
        logicModuleContainer_.getJobInfo().getGuardStrength().getProdMod().addMod(duration, .85);
    }
    public void doContinuous(){
        logicModuleContainer_.getTemperatureInfo().changeHiveTemp(-0.3);
    }
}
