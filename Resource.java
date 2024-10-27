//Resources that belong to the hive
public class Resource {
	private int amount;
	private int potency;
	
	public Resource(int p){
		amount = 0;
		potency = p;
	}
	public Resource(int a, int p){
		amount = a;
		potency = p;
	}
	
	//Useful Methods
	public void add(int a){ amount += a; }
	public void sub(int a){ amount -= a; }
	public int getFoodValue(){ return amount*potency; }
	
	//Getters
	public int getAmount(){ return amount; }
	public int getPotency(){ return potency; }
	//Setters
	public void setAmount(int a){ amount = a; }
	public void setPotency(int p){ potency = p; }	
}
