package beehive.job;

import beehive.Hive;
import beehive.temperature.TemperatureRegulationRanges;

public class HiveCreateBeesCommand implements HiveCommand {
    private Hive hive;
    private double multiplier;

    public HiveCreateBeesCommand(){}

    public void execute(Hive hive, double multiplier){
        this.hive = hive;
        this.multiplier = multiplier;

        updateNursery();
    }

    private void updateNursery(){
        if(insideBroodTempRange()){
            hive.addBeesToCluster(calcNumBeesToAdd());
            hive.getPhysicalResources().getPollen().setAmount(0);
        }
    }
    private boolean insideBroodTempRange(){
        double hiveTemp = hive.getTemperatureInfo().getHiveTemp();
        double minBroodTemp = TemperatureRegulationRanges.BROOD.getMinTemperature();
        double maxBroodTemp = TemperatureRegulationRanges.BROOD.getMaxTemperature();

        if(minBroodTemp <= hiveTemp && hiveTemp <= maxBroodTemp){
            return true;
        }else{
            return false;
        }
    }
    private int calcNumBeesToAdd(){
        double pollen = (double)(hive.getPhysicalResources().getPollen().getAmount());

        pollen *= multiplier;
        pollen /= hive.getTotalBees();

        return (int)(pollen);
    }
}
