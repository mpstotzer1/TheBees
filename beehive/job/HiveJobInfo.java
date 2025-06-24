package beehive.job;

import java.util.ArrayList;

public class HiveJobInfo{


    private HiveTemperatureRegulator hiveTemperatureRegulator;
    private BeeCreator beeCreator;
    private ArrayList<Modifiable> hiveJobs = new ArrayList<Modifiable>();

    public HiveJobInfo(HiveTemperatureRegulator hiveTemperatureRegulator, BeeCreator beeCreator){
        this.hiveTemperatureRegulator = hiveTemperatureRegulator;
        this.beeCreator = beeCreator;

        this.hiveJobs.add(hiveTemperatureRegulator);
        this.hiveJobs.add(beeCreator);
    }

    public HiveTemperatureRegulator getHiveTemperatureRegulator(){ return hiveTemperatureRegulator; }
    public BeeCreator getBeeCreator(){ return beeCreator; }
    public ArrayList<Modifiable> getHiveJobs(){ return hiveJobs; }
}
