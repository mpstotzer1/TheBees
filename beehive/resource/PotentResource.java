package beehive.resource;
//Resources (food) with a potency
public class PotentResource extends Resource {
	private int potency;
	
	public PotentResource(int p){
		amount = 0;
		potency = p;
	}
	public PotentResource(int a, int p){
		amount = a;
		potency = p;
	}
	
	//More Useful Methods
	public int getFoodValue(){ return amount*potency; }
	
	//getters and setters
	public int getPotency(){ return potency; }
	public void setPotency(int p){ potency = p; }
}
