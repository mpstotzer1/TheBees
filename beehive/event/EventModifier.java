package beehive.event;
//Situations are periods of time that change the hive's variables (like faster wax production, or decreased strength)
public class EventModifier {
	private int countdown;
	private double modifier;

	//Constructor
	//"permanent" is false by default
	public EventModifier(int c, double m){
		countdown = c;
		modifier = m;
	}
	
	//Useful methods
	public void decrement(){
		countdown-=1;
	}
	
	//Getters and Setters
	public int getCountdown(){ return countdown; }
	public double getModifier(){ return modifier; }
	public void setCountdown(int c){ countdown = c; }
	public void setModifier(double m){ modifier = m; }	
}