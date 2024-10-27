//A Nursery holds Batches of baby (larva, pupa) bees
import java.util.ArrayList;

public class Nursery {
	private ArrayList<Batch> batches;
	
	//Useful methods
	public void shutdown(){
		for(int i=0; i< batches.size(); i++){
			batches.remove(i);
			//Throw "Nursery Shut Down!" warning
		}
	}
	//update() calls each Batch's update method and removes it from the list once it hatches
	public void update(){
		for(int i=batches.size() - 1; i>=0; i--){
			if(batches.get(i).update() <= 0){
				batches.remove(i);
			}
		}
	}
}