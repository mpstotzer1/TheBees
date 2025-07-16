package beehive.job;

public abstract class Job{
    protected ModifierList prodMods;
    protected ModifierList foodMods;
    protected ModifierList heatMods;

    public void initializeModifiers(double prodCnst, double foodCnst, double heatCnst){
        prodMods.setConstant(prodCnst);
        foodMods.setConstant(foodCnst);
        heatMods.setConstant(heatCnst);
    }
    public void work(){
        workOverride();
        prodMods.update();
        foodMods.update();
        heatMods.update();
    }
    protected abstract void workOverride();
    public abstract int calcFoodCost();
    public abstract double calcHeat();

    public void clearAllModifierLists(){
        prodMods.clearMods();
        foodMods.clearMods();
        heatMods.clearMods();
    }

    public ModifierList getProdMod(){ return prodMods; }
    public ModifierList getFoodMod(){ return foodMods; }
    public ModifierList getHeatMod(){ return heatMods; }
}