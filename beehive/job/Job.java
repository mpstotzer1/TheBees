//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
//Its output can be controlled by modifiers
package beehive.job;

import java.util.ArrayList;
import java.util.Iterator;

import beehive.event.EventModifier;

abstract class Job {
	protected double foodCostConstant;
	protected double heatCostConstant;
	protected ArrayList<EventModifier> prodMod = new ArrayList<EventModifier>();
	protected ArrayList<EventModifier> foodMod = new ArrayList<EventModifier>();
	protected ArrayList<EventModifier> heatMod = new ArrayList<EventModifier>();

	//Useful Methods
//	abstract int getFoodCost(int numBees);
//	abstract int getHeat(int numBees);
//	abstract void produce(int numBees);
	protected double calcListMultiplier(ArrayList<EventModifier> modList){
		double multiplier = 1;
		for(int i = 0; i < modList.size(); i++){
			multiplier *= modList.get(i).getModifier();
		}
		return multiplier;
	}

	//Update the modifier countdown timers
	public void updateMods(){
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
	
	//kind of Setters (adding modifiers to the ArrayLists)
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
}
