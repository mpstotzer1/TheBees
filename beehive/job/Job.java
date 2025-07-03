package beehive.job;

public abstract class Job{
    protected Modifiers modifiers;

    public final Modifiers getModifiers(){
        return modifiers;
    };
    public void addProdMod(int duration, double modifier){ modifiers.addProdMod(duration, modifier); }
    public void addFoodMod(int duration, double modifier){ modifiers.addFoodMod(duration, modifier); }
    public void addHeatMod(int duration, double modifier){ modifiers.addHeatMod(duration, modifier); }


    public void work(){
        workOverride();
        modifiers.update();
    }
    protected abstract void workOverride();
    //public abstract int calcProduction();
    public abstract int calcFoodCost();
    public abstract double calcHeat();
}
