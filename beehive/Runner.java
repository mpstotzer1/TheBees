package beehive;
//Runs the game
public class Runner{
	
	public static void main(String[] args){

    	Initializer initializer = new Initializer();
    	Hive hive = initializer.createHive();

		boolean flag = true;
		while(flag){//!(hive.getGameLost()) ){
    		//update the hive
    		//redraw the graphics
			flag = hive.getGameLost();
        }
		System.out.println("Game state manually set to \"Game Over\"");
    }
	
}