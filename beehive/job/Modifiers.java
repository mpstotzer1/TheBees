package beehive.job;

import beehive.event.EventModifier;

import java.util.ArrayList;
import java.util.Iterator;

public class Modifiers {
    private double productionConstant;
    private double foodCostConstant;
    private double heatConstant;
    private ArrayList<EventModifier> prodMod = new ArrayList<EventModifier>();
    private ArrayList<EventModifier> foodMod = new ArrayList<EventModifier>();
    private ArrayList<EventModifier> heatMod = new ArrayList<EventModifier>();

    public Modifiers(double foodCostConstant, double heatConstant, double productionConstant){
        this.foodCostConstant = foodCostConstant;
        this.heatConstant = heatConstant;
        this.productionConstant = productionConstant;
    }

    public void update(){
        updateMod(prodMod);
        updateMod(foodMod);
        updateMod(heatMod);
    }
    private void updateMod(ArrayList<EventModifier> modList){
        Iterator<EventModifier> iterator = modList.iterator();

        while(iterator.hasNext()){
            EventModifier mod = iterator.next();
            mod.decrement();
            if (mod.getCountdown() <= 0) { iterator.remove(); }
        }
    }

    public double calcProdMultiplier(){ return calcListMultiplier(prodMod) * productionConstant; }
    public double calcFoodMultiplier(){ return calcListMultiplier(foodMod) * foodCostConstant; }
    public double calcHeatMultiplier(){ return calcListMultiplier(heatMod) * heatConstant; }

    private double calcListMultiplier(ArrayList<EventModifier> modList){
        double multiplier = 1;
        for(int i = 0; i < modList.size(); i++){
            multiplier *= modList.get(i).getModifier();
        }
        return multiplier;
    }

    public void addProdMod(int duration, double modifier){
        addMod(duration, modifier, prodMod);
    }
    public void addFoodMod(int duration, double modifier){
        addMod(duration, modifier, foodMod);
    }
    public void addHeatMod(int duration, double modifier){
        addMod(duration, modifier, heatMod);
    }
    private void addMod(int duration, double modifier, ArrayList<EventModifier> modList){
        EventModifier eventModifier = new EventModifier(duration, modifier);
        modList.add(eventModifier);
    }
    public void clearAllMods(){
        prodMod.clear();
        foodMod.clear();
        heatMod.clear();
    }
}
