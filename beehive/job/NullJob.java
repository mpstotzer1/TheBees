package beehive.job;

//This pseudo-job will not produce anything
//Example: bees "busy" clustering
//		   resource manipulation to unusual to handle in the Job instance (Honey, QueenHealth)
public class NullJob extends Job {

	//A constructor
	public NullJob(){
		//Does NOTHING!
	}
	
	public void produce(int numBees){
		//Produce NOTHING!
	}
	public int willProduce(int numBees) {
		return 0;
	}
}