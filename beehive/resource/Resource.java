package beehive.resource;

import beehive.logger.Logger;

public class Resource {
	protected int amount;
	
	public Resource(){
		amount = 0;
	}
	public Resource(int a){
		amount = a;
	}
	
	//Useful Methods
	public void add(int a){ amount += a; }
	public void sub(int a){
		amount -= a;
		if(amount < 0){
			Logger.warning("Attempted to subtract more from resource than able");
			amount = 0;
		}
	}
	public void addPercent(double p){
		add(calcPercent(p));
	}
	public void subPercent(double p){
		sub(calcPercent(p));
	}
	private int calcPercent(double p){
		return (int)(amount * (p / 100));
	}
	
	public int getAmount(){ return amount; }
	public void setAmount(int a){ amount = a; }
}