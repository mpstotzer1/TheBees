package beehive;

import beehive.department.Department;

import java.util.concurrent.TimeUnit;

//Runs the game
public class Runner{
	
	public static void main(String[] args) throws InterruptedException {

    	Initializer initializer = new Initializer();
    	initializer.initializeModuleGateway();
		GameLogic gameLogic = new GameLogic();
		ModuleGateway.getDepartmentInfo().adjustBeesEverywhere(400);


		int debugCounter = 0;

		while(!(gameLogic.getGameLost()) && debugCounter < 360 ){
			gameLogic.update();

			Logger.log("Current Hive temp: " + ModuleGateway.getTemperatureInfo().getHiveTemp());
			Logger.log("-------------Hive updated-------------");

			//gameLogic.toString();

			TimeUnit.MILLISECONDS.sleep(10);
			debugCounter++;
		}
		System.out.println("Number of Ticks: " + debugCounter);
	}
}