package beehive.job;

import beehive.resource.Resource;

//These jobs set their specific resource's value equal to whatever they produce
public class SettingJob extends Job {
	private double optionalAdjuster;
	
	//Some constructors
	public SettingJob(Resource resource){
		this.resource = resource;
		this.optionalAdjuster = 1.0;
	}
	public SettingJob(Resource resource, int optionalAdjuster){
		this.resource = resource;
		this.optionalAdjuster = optionalAdjuster;
	}
	
	//The produce() methods
	public void produce(int numBees){
		double multiplier = 1;
		for(int i = 0; i < prodMod.size(); i++){
			multiplier *= prodMod.get(i).getModifier();
		}
		resource.setAmount( (int)(numBees * multiplier * optionalAdjuster) );
	}
	public int willProduce(int numBees){
		double multiplier = 1;
		for(int i = 0; i < prodMod.size(); i++){
			multiplier *= prodMod.get(i).getModifier();
		}
		return (int)(numBees * multiplier * optionalAdjuster);
	}
	
	//Getters and Setters
	public double getOptionalAdjuster() { return optionalAdjuster; }
	public void setOptionalAdjuster(double optionalAdjuster) { this.optionalAdjuster = optionalAdjuster; }
}