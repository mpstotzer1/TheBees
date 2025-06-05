package beehive.event;

import beehive.Hive;
import beehive.job.JobInfo;

public class DiseaseSituation extends Situation {
    private Hive hive;

    public DiseaseSituation(Hive hive, int duration){
        this.hive = hive;
        this.duration = duration;
    }

    public void doOnce(){
        JobInfo jobs = hive.getJobInfo();

        jobs.getForagerNectar().addProdMod(duration, .8);
        jobs.getForagerPollen().addProdMod(duration, .8);
        // Brood production decreases
        jobs.getGuardStrength().addProdMod(duration, .8);
    }
    public void doContinuously(){
        hive.killPercentBees(0.75);
    }
}
