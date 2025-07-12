package beehive;

import beehive.logger.Logger;

import java.util.concurrent.TimeUnit;

//Runs the game
public class Runner{
	
	public static void main(String[] args) throws InterruptedException {

    	Initializer initializer = new Initializer();
    	initializer.initializeModuleGateway();
		GameLogic gameLogic = new GameLogic();
		ModuleGateway.getDepartmentInfo().adjustBeesEverywhere(400);


		int debugCounter = 0;

		while(!(gameLogic.getGameLost()) && debugCounter < 482 ){
			gameLogic.update();

			Logger.logTemperatureDebugging("Current Hive temp: " + ModuleGateway.getTemperatureInfo().getHiveTemp());
			Logger.logBeePopulation("Total Bees: " + ModuleGateway.getDepartmentInfo().getTotalBees());
			Logger.logLogicUpdate("-------------Game Logic Updated-------------");

			TimeUnit.MILLISECONDS.sleep(10);
			debugCounter++;
		}
		System.out.println("Number of Ticks: " + debugCounter);
	}
}