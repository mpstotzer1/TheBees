package beehive.job;

public abstract class Modifiable{
    protected Modifiers modifiers;

    public final Modifiers getModifiers(){
        return modifiers;
    };
    public void addProdMod(int duration, double modifier){
        modifiers.addProdMod(duration, modifier);
    };
    public void addFoodMod(int duration, double modifier){
        modifiers.addFoodMod(duration, modifier);
    };
    public void addHeatMod(int duration, double modifier){
        modifiers.addHeatMod(duration, modifier);
    };
}
