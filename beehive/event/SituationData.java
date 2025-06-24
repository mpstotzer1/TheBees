package beehive.event;

import java.util.ArrayList;

public class SituationData{
    private int situationCounter = 0;
    private ArrayList<Situation> allSituations = new ArrayList<Situation>();
    private ArrayList<Situation> currentSituations = new ArrayList<Situation>();

    public SituationData(ArrayList<Situation> allSituations){
        situationCounter = 0;
        this.allSituations.addAll(allSituations);
    }

    public void incrementSituationCounter(){
        situationCounter++;
    }
    public void resetSituationCounter(){ situationCounter = 0; }

    public int getSituationCounter(){ return situationCounter; }
    public ArrayList<Situation> getAllSituations(){ return allSituations; }
    public ArrayList<Situation> getCurrentSituations(){ return currentSituations; }
}
