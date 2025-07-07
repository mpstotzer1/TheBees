package beehive;

import beehive.department.DepartmentInfo;
import beehive.event.SituationData;
import beehive.job.JobInfo;
import beehive.resource.ResourceData;
import beehive.resource.Resources;
import beehive.temperature.TemperatureInfo;
import beehive.world.WorldInfo;

import java.util.HashMap;

public class ModuleGateway{
    private static Resources resources_;
    private static ResourceData resourceData_;
    private static TemperatureInfo temperatureInfo_;
    private static WorldInfo worldInfo_;
    private static DepartmentInfo departmentInfo_;
    private static JobInfo jobInfo_;
    private static SituationData situationData_;
    private static HashMap<String, Double> upgrades_;
    private static MiscData miscData_;

    private ModuleGateway(){
        //Do nothing
    }

    public static void initialize(Resources resources,
                ResourceData resourceData,
                TemperatureInfo temperatureInfo,
                WorldInfo worldInfo,
                DepartmentInfo departmentInfo,
                JobInfo jobInfo,
                SituationData situationData,
                HashMap<String, Double> upgrades,
                MiscData miscData){
        resources_ = resources;
        resourceData_ = resourceData;
        temperatureInfo_ = temperatureInfo;
        worldInfo_ = worldInfo;
        departmentInfo_ = departmentInfo;
        jobInfo_ = jobInfo;
        situationData_ = situationData;
        upgrades_ = upgrades;
        miscData_ = miscData;
    }

    public static JobInfo getJobInfo(){ return jobInfo_; }
    public static Resources getResources(){ return resources_; }
    public static TemperatureInfo getTemperatureInfo(){ return temperatureInfo_; }
    public static DepartmentInfo getDepartmentInfo(){ return departmentInfo_; }
    public static WorldInfo getWorldInfo(){ return worldInfo_; }
    public static HashMap<String, Double> getUpgrades(){ return upgrades_; }
    public static SituationData getSituationData(){ return situationData_; }
    public static ResourceData getResourceData(){ return resourceData_; }
    public static MiscData getMiscData(){ return miscData_; }
}
