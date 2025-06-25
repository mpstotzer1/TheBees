package beehive.hivestate;

public class HiveStateInfo {
    private HiveState hiveState;
    private ActiveHive activeHive;
    private DormantHive dormantHive;
    private HiveStateData hiveStateData;

    public HiveStateInfo(){
        activeHive = new ActiveHive();
        dormantHive = new DormantHive();
        hiveState = activeHive;
        hiveStateData = new HiveStateData(.45);
    }

    public HiveState getHiveState(){ return hiveState; }
    public void setHiveState(HiveState hiveState){ this.hiveState = hiveState; }
    public HiveState getActiveState(){ return activeHive; }
    public HiveState getDormantState(){ return dormantHive; }
    public HiveStateData getHiveStateData(){ return hiveStateData; }
}
