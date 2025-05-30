package beehive.situation;
//Situations are periods of time that change the hive's variables (like faster wax production, or decreased strength)
public class Situation {
	private int countdown;
	private double modifier;
	private boolean permanent;
	
	//Constructor
	//"permanent" is false by default
	public Situation(int c, double m){
		countdown = c;
		modifier = m;
		permanent = false;
	}
	public Situation(int c, double m, boolean p){
		countdown = c;
		modifier = m;
		permanent = p;
	}
	
	//Useful methods
	public void decrement(){
		if(!permanent){
			countdown-=1;
		}
	}
	
	//Getters and Setters
	public int getCountdown(){ return countdown; }
	public double getModifier(){ return modifier; }
	public void setCountdown(int c){ countdown = c; }
	public void setModifier(double m){ modifier = m; }	
}