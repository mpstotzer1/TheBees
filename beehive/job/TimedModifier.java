package beehive.job;
//Situations are periods of time that change the hive's variables (like faster wax production, or decreased strength)
public class TimedModifier {
	private int countdown;
	private double modifier;

	public TimedModifier(int c, double m){
		countdown = c;
		modifier = m;
	}
	
	public void decrement(){
		countdown-=1;
	}
	
	public int getCountdown(){ return countdown; }
	public double getModifier(){ return modifier; }
}