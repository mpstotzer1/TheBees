package beehive.resource;

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
		if(amount < 0){ amount = 0; }
	}
	public void addPercent(double p){ amount += amount * p; }
	public void subPercent(double p){
		amount -= amount * p;
		if(amount < 0){ amount = 0; }
	}
	
	public int getAmount(){ return amount; }
	public void setAmount(int a){ amount = a; }
}