//Resources that belong to the hive
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
	public void sub(int a){ amount -= a; }
	public void addPercent(double p){ amount += amount*p; }
	public void subPercent(double p){ amount -= amount*p; }
	
	//Getters
	public int getAmount(){ return amount; }
	//Setters
	public void setAmount(int a){ amount = a; }
}