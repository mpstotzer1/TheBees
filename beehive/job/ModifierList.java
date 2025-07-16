package beehive.job;

import java.util.ArrayList;
import java.util.Iterator;

public class ModifierList {
    private double constant = 0.0;
    private ArrayList<TimedModifier> timedModifiers = new ArrayList<>();

    public ModifierList(double constant){
        this.constant = constant;
    }
    public void setConstant(double constant) {this.constant = constant; }

    public void update(){
        Iterator<TimedModifier> iterator = timedModifiers.iterator();

        while(iterator.hasNext()){
            TimedModifier mod = iterator.next();
            mod.decrement();
            if (mod.getCountdown() <= 0) { iterator.remove(); }
        }
    }

    public double calcMultiplier(){ return calcListMultiplier() * constant; }
    private double calcListMultiplier(){
        double multiplier = 1;
        for(int i = 0; i < timedModifiers.size(); i++){
            multiplier *= timedModifiers.get(i).getModifier();
        }
        return multiplier;
    }

    public void addMod(int duration, double modifier){
        TimedModifier eventModifier = new TimedModifier(duration, modifier);
        timedModifiers.add(eventModifier);
    }
    public void clearMods(){
        timedModifiers.clear();
    }
}
