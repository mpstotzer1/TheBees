//A Batch of bees has the number of bees, how long it takes them to hatch, and a hatch() method
public abstract class Batch {
	protected int countdown;
	protected int numBees;
	protected Department department;
	
	//Constructor
	public Batch(int num, int count, Department dept){
		numBees = num;
		countdown = count;
		department = dept;
	}
	
	//Useful methods
	//update() decrements the countdown by one and moves matured bees into their proper default department
	public int update(){
		countdown -= 1;
		if(countdown <= 0){
			department.addBees(numBees);
		}
		return countdown;
	}
}