package beehive.job;

public abstract class Job{
    protected ModifierList prodMods;
    protected ModifierList foodMods;
    protected ModifierList heatMods;

    protected Job(double productionConstant, double foodCostConstant, double heatConstant){
        prodMods = new ModifierList(productionConstant);
        foodMods = new ModifierList(foodCostConstant);
        heatMods = new ModifierList(heatConstant);
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