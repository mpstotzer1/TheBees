//Runs the game
public class Runner{
	
	public static void main(String[] args){
    	Initializer initializer = new Initializer();
    	Hive hive = initializer.createHive();

    	while( !(hive.getGameLost()) ){
    		//update the hive
    		//redraw the graphics
    	}
    }
	
}