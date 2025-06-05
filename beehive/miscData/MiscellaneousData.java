package beehive.miscData;

public class MiscellaneousData {
    private int situationCounter = 0;

    public MiscellaneousData(){
        situationCounter = 0;
    }

    public int getSituationCounter() { return situationCounter; }
    public void incrementSituationCounter() { situationCounter++; }
    public void resetSituationCounter() { situationCounter = 0; }
}
