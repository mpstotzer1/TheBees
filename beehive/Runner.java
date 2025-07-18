package beehive;

import beehive.logger.Logger;

import java.util.concurrent.TimeUnit;

//Runs the game
public class Runner{
	
	public static void main(String[] args) throws InterruptedException {

    	Initializer initializer = new Initializer();
    	Hive hive = initializer.initializeHive();

		int debugCounter = 0;

		while(!(hive.getGameLost()) && debugCounter < 160 ){
			hive.update();

			Logger.basicLog("--------------------Hive Updated--------------------");

			TimeUnit.MILLISECONDS.sleep(10);
			debugCounter++;
		}
		System.out.println("Number of Ticks: " + debugCounter);
	}
}