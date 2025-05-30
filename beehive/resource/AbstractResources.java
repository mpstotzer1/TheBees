package beehive.resource;

//All the abstract resources
public class AbstractResources {
	private Resource hygiene;
	private Resource lowHygiene; //This is the threshold for hygiene; below it and you're officially diseased.
	private Resource lowQueenHealth; //Below this, the beehive.queen is officially infirm
	private Resource strength;
	private Resource xp;
	
	//The constructor
	public AbstractResources(int hygiene, int lowHygiene, int lowQueenHealth, int strength, int xp){
		this.hygiene = new Resource(hygiene);
		this.lowHygiene = new Resource(lowHygiene); //This is the threshold for hygiene; below it and you're officially diseased.
		this.lowQueenHealth = new Resource(lowQueenHealth);//Below this, the beehive.queen is officially infirm
		this.strength = new Resource(strength);
		this.xp = new Resource(xp);
	}
	
	//Getters and Setters (ARE THE SETTERS SUPPOSED TO ACT THIS WAY?)
	public Resource getHygiene() { return hygiene; }
	public void setHygiene(int hygiene) { this.hygiene.setAmount(hygiene); }
	public Resource getLowHygiene() { return lowHygiene; }
	public void setLowHygiene(int lowHygiene) { this.lowHygiene.setAmount(lowHygiene); }
	public Resource getLowQueenHealth() { return lowQueenHealth; }
	public void setLowQueenHealth(int lowQueenHealth) { this.lowQueenHealth.setAmount(lowQueenHealth); }
	public Resource getStrength() { return strength; }
	public void setStrength(int strength) { this.strength.setAmount(strength); }
	public Resource getXp() {return xp;}
	public void setXp(int xp) {this.xp.setAmount(xp); }
}