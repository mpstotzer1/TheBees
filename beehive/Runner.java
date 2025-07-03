package beehive;

import beehive.department.Department;

import java.util.concurrent.TimeUnit;

//Runs the game
public class Runner{
	
	public static void main(String[] args) throws InterruptedException {

    	Initializer initializer = new Initializer();
    	Hive hive = initializer.createHive();

		int debugCounter = 0;

		while(!(hive.getGameLost()) && debugCounter < 360 ){
			hive.update();

			Logger.log("-------------Hive updated-------------");

			TimeUnit.MILLISECONDS.sleep(10);
			debugCounter++;
		}
		System.out.println("Number of Ticks: " + debugCounter);
	}
}