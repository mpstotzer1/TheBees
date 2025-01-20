//Basic information about nursery-related variables
public class NurseryInfo {
	//private int queenCost;
	//private int droneCost;
	//private int workerCost;
	private Nursery nursery;
	
	//The constructor
	public NurseryInfo(){//(int queenCost, int droneCost, int workerCost){
		this.nursery = new Nursery();
		//this.queenCost = queenCost;
		//this.droneCost = droneCost;
		//this.workerCost = workerCost;
	}
	
	//Getters and Setters
	public Nursery getNursery() { return nursery; }
	public void setNursery(Nursery nursery) { this.nursery = nursery; }
	/*
	public int getQueenCost() { return queenCost; }
	public void setQueenCost(int queenCost) { this.queenCost = queenCost; }
	public int getDroneCost() { return droneCost; }
	public void setDroneCost(int droneCost) { this.droneCost = droneCost; }
	public int getWorkerCost() { return workerCost; }
	public void setWorkerCost(int workerCost) { this.workerCost = workerCost; }
	*/
}