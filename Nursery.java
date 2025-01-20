//A Nursery holds Batches of baby (larva, pupa) bees
import java.util.ArrayList;
import java.lang.Math;

public class Nursery {
	private boolean nurseryOn;
	private ArrayList<Brood> batches;
	
	//The constructor
	public Nursery(){
		nurseryOn = true;
	}
	
	//Useful methods
	public void destroyBrood(){
		for(int i=0; i< batches.size(); i++){
			batches.remove(i);
			//Throw "Nursery Shut Down!" warning
		}
	}
	public void destroyOneBrood(){
		int rand = (int)( Math.random() * batches.size() );
		batches.remove(rand);
	}
	
	//update() calls each Batch's update method and removes it from the list once it hatches
	public void update(){
		for(int i=batches.size() - 1; i>=0; i--){
			if(batches.get(i).update() <= 0){
				batches.remove(i);
			}
		}
	}
	
	public void shutdown(){
		nurseryOn = false;
	}
	public void reopen(){
		nurseryOn = true;
	}
	
}