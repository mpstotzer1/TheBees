package beehive.department;

import beehive.logger.Logger;

public class Department {
	private int numBees;

	public Department(){
		numBees=0;
	}

	public int getNumBees(){ return numBees; }
	public void addBees(int beesToAdd){
		numBees += beesToAdd;
	}
	public int attemptSubBees(int beesToSub){ //returns number of bees successfully subtracted
		if(beesToSub > numBees){
			Logger.warning("Tried to subtract more bees than able");
			int oldNumBees = numBees;
			numBees = 0;

			return (oldNumBees);
		}else{
			numBees -= beesToSub;
			return beesToSub;
		}

	}
	public void killAllBees(){
		numBees = 0;
	}
}