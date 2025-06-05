package beehive.resource;

//All the abstract resources
public class AbstractResources {
	private Resource hygiene;
	private int lowHygiene; //This is the threshold for hygiene; below it and you're officially diseased.
	private Resource queenHealth;
	private int lowQueenHealth; //Below this, the beehive.queen is officially infirm
	private Resource strength;
	private Resource xp;
	private Resource nullResource;
	
	//The constructor
	public AbstractResources(int hygiene, int lowHygiene, int queenHealth, int lowQueenHealth, int strength, int xp){
		this.hygiene = new Resource(hygiene);
		this.lowHygiene = lowHygiene; //This is the threshold for hygiene; below it and you're officially diseased.
		this.queenHealth = new Resource(queenHealth);
		this.lowQueenHealth = lowQueenHealth;//Below this, the beehive.queen is officially infirm
		this.strength = new Resource(strength);
		this.xp = new Resource(xp);
		nullResource = new Resource();
	}
	
	//Getters and Setters (ARE THE SETTERS SUPPOSED TO ACT THIS WAY?)
	public Resource getHygiene() { return hygiene; }
	public void setHygiene(int hygiene) { this.hygiene.setAmount(hygiene); }
	public int getLowHygiene() { return lowHygiene; }
	public void setLowHygiene(int lowHygiene) { this.lowHygiene = lowHygiene; }
	public Resource getQueenHealth(){ return queenHealth; }
	public int getLowQueenHealth() { return lowQueenHealth; }
	public void setLowQueenHealth(int lowQueenHealth) { this.lowQueenHealth = lowQueenHealth; }
	public Resource getStrength() { return strength; }
	public void setStrength(int strength) { this.strength.setAmount(strength); }
	public Resource getXp() {return xp;}
	public void setXp(int xp) {this.xp.setAmount(xp); }
	public Resource getNullResource(){ return nullResource; }
}