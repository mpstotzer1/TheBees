package beehive.department;

public class Department {
	private int numBees;

	public Department(){
		numBees=0;
	}

	public int getNumBees(){ return numBees; }
	public void adjustBees(int n){
		if((numBees + n) < 0){
			numBees = 0;
		}else{
			numBees += n;
		}
	}
	public void killAllBees(){
		numBees = 0;
	}
}