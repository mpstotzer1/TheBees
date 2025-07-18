package beehive;

import beehive.department.DepartmentInfo;
import beehive.event.SituationData;
import beehive.job.JobInfo;
import beehive.resource.ResourceData;
import beehive.resource.Resources;
import beehive.temperature.TemperatureInfo;
import beehive.world.WorldInfo;


public class HiveModuleContainer {
    private Resources resources;
    private ResourceData resourceData;
    private TemperatureInfo temperatureInfo;
    private WorldInfo worldInfo;
    private DepartmentInfo departmentInfo;
    private JobInfo jobInfo;
    private SituationData situationData;
    private Upgrades upgrades;
    private MiscData miscData;

    public HiveModuleContainer(Resources resources,
                               ResourceData resourceData,
                               TemperatureInfo temperatureInfo,
                               WorldInfo worldInfo,
                               DepartmentInfo departmentInfo,
                               JobInfo jobInfo,
                               SituationData situationData,
                               Upgrades upgrades,
                               MiscData miscData){
        this.resources = resources;
        this.resourceData = resourceData;
        this.temperatureInfo = temperatureInfo;
        this.worldInfo = worldInfo;
        this.departmentInfo = departmentInfo;
        this.jobInfo = jobInfo;
        this.situationData = situationData;
        this.upgrades = upgrades;
        this.miscData = miscData;
    }

    public JobInfo getJobInfo(){ return jobInfo; }
    public Resources getResources(){ return resources; }
    public TemperatureInfo getTemperatureInfo(){ return temperatureInfo; }
    public DepartmentInfo getDepartmentInfo(){ return departmentInfo; }
    public WorldInfo getWorldInfo(){ return worldInfo; }
    public Upgrades getUpgrades(){ return upgrades; }
    public SituationData getSituationData(){ return situationData; }
    public ResourceData getResourceData(){ return resourceData; }
    public MiscData getMiscData(){ return miscData; }
}
