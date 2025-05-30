//A job produces EXACTLY one RESOURCE, generates a FOODCOST, and generates HEAT
//Its output can be controlled by modifiers
package beehive.job;

import beehive.resource.Resource;
import beehive.situation.Situation;

import java.util.ArrayList;

public abstract class Job {
	protected Resource resource;
	protected ArrayList<Situation> prodMod = new ArrayList<Situation>();
	protected ArrayList<Situation> foodMod = new ArrayList<Situation>();
	protected ArrayList<Situation> heatMod = new ArrayList<Situation>();
	
	//Useful Methods
	public int getFoodCost(int numBees){//Get the food cost
		double multiplier = 1;
		for(int i = 0; i < foodMod.size(); i++){
			multiplier *= foodMod.get(i).getModifier();
		}
		return (int)(numBees * multiplier);
	}
	public int getHeat(int numBees){//Get the head generated
		double multiplier = 1;
		for(int i = 0; i < heatMod.size(); i++){
			multiplier *= heatMod.get(i).getModifier();
		}
		return (int)(numBees * multiplier);//Adjust appropriate resources appropriately
	}
	public abstract void produce(int numBees);
	public abstract int willProduce(int numBees);

	//Update the modifier countdown timers
	public void updateMods(){
		for(int i = 0; i < prodMod.size(); i++){ prodMod.get(i).decrement(); }
		for(int i = 0; i < foodMod.size(); i++){ foodMod.get(i).decrement(); }
		for(int i = 0; i < heatMod.size(); i++){ heatMod.get(i).decrement(); }
	}
	
	//kind of Setters (adding modifiers to the ArrayLists)
	public void addProdMod(int c, double m){
		Situation situation = new Situation(c, m);
		prodMod.add(situation);
	}
	public void addProdMod(int c, double m, boolean p){
		Situation situation = new Situation(c, m, p);
		prodMod.add(situation);
	}
	public void addFoodMod(int c, double m){
		Situation situation = new Situation(c, m);
		prodMod.add(situation);
	}
	public void addHeatMod(int c, double m){
		Situation situation = new Situation(c, m);
		prodMod.add(situation);
	}
}
