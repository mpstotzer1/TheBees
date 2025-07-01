package beehive.job;

public abstract class Job{
    protected Modifiers modifiers;

    public final Modifiers getModifiers(){
        return modifiers;
    };

    public void work(){
        workOverride();
        modifiers.update();
    }
    protected abstract void workOverride();
    //public abstract int calcProduction();
    public abstract int calcFoodCost();
    public abstract double calcHeat();
}
