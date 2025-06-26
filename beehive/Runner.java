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
			display2(hive);
			hive.update();

			Logger.log("-------------Hive updated-------------");

			TimeUnit.MILLISECONDS.sleep(10);
			debugCounter++;
		}
		System.out.println("Number of Ticks: " + debugCounter);
	}

	public static void display1(Hive hive){
		System.out.println("-----------------------------------------------------------");

		System.out.println("Total Bees: " + hive.getTotalBees());

//		System.out.println("------------Departments------------");
//		System.out.println("Number Bees in Wax Department: " + hive.getDepartmentInfo().getWaxMason().getNumBees());
//		System.out.println("Number Bees in Honey Department: " + hive.getDepartmentInfo().getFanner().getNumBees());
//		System.out.println("Number Bees in Nectar Department: " + hive.getDepartmentInfo().getForager().getNumBees());
//		System.out.println("Number Bees in XP Department: " + hive.getDepartmentInfo().getDrone().getNumBees());
//		System.out.println("Number Bees in Pollen Department: " + hive.getDepartmentInfo().getForager().getNumBees());
//		System.out.println("Number Bees in Hygiene Department: " + hive.getDepartmentInfo().getHouseBee().getNumBees());
//		System.out.println("Number Bees in queenHealth Department: " + hive.getDepartmentInfo().getNurse().getNumBees());
//		System.out.println("Number Bees in Strength Department: " + hive.getDepartmentInfo().getGuard().getNumBees());


		System.out.println("------------Resources------------");
		System.out.println("Wax: " + hive.getResources().wax().getAmount());
		System.out.println("Honey: " + hive.getResources().honey().getAmount());
		System.out.println("Nectar: " + hive.getResources().nectar().getAmount());
		System.out.println("XP: " + hive.getResources().xp().getAmount());
		System.out.println("Pollen: " + hive.getResources().pollen().getAmount());
		System.out.println("Hygiene: " + hive.getResources().hygiene().getAmount());
		System.out.println("Queen Health: " + hive.getResources().queenHealth().getAmount());
		System.out.println("Strength: " + hive.getResources().strength().getAmount());

		System.out.println("------------Production------------");
		System.out.println("Wax to be Produced: " + hive.getJobInfo().getWaxMasonWax().calcProduction(hive.getDepartmentInfo().getWaxMason().getNumBees()));
		System.out.println("Honey to be Produced: " + hive.getJobInfo().getFannerHoney().calcProduction(hive.getDepartmentInfo().getFanner().getNumBees()));
		System.out.println("Nectar to be Produced: " + hive.getJobInfo().getForagerNectar().calcProduction(hive.getDepartmentInfo().getForager().getNumBees()));
		System.out.println("XP to be Produced: " + hive.getJobInfo().getDroneXP().calcProduction(hive.getDepartmentInfo().getDrone().getNumBees()));
		System.out.println("Pollen to be Produced: " + hive.getJobInfo().getForagerPollen().calcProduction(hive.getDepartmentInfo().getForager().getNumBees()));
		System.out.println("Hygiene to be Produced: " + hive.getJobInfo().getHouseBeeHygiene().calcProduction(hive.getDepartmentInfo().getHouseBee().getNumBees()));
		System.out.println("Queen Health to be Produced: " + hive.getJobInfo().getNurseQueenHealth().calcProduction(hive.getDepartmentInfo().getNurse().getNumBees()));
		System.out.println("Strength to be Produced: " + hive.getJobInfo().getGuardStrength().calcProduction(hive.getDepartmentInfo().getGuard().getNumBees()));
		//System.out.println("Bees to be Produced: " + hive.getHiveJobInfo().getBeeCreator().calcNumBeesToAddDebug());

		System.out.println("------------Food Costs------------");
		int deptFoodCost = 0;
		for(Department dept: hive.getDepartmentInfo().getDepartments()){
			deptFoodCost += dept.getTotalFoodCost();
		}
		System.out.println("FoodCost for Dept. Jobs: " + deptFoodCost);
//		int hiveFoodCost = 0;
//		hiveFoodCost += hive.getHiveJobInfo().getBeeCreator().up
//		System.out.println("FoodCost for Hive Jobs: " + deptFoodCost);



		System.out.println("------------World------------");
		System.out.println("Season: " + hive.getWorldInfo().getSeason().getSeasonType().name());
		System.out.println("World Temp: " + hive.getWorldInfo().getWorldTemp());
		System.out.println("Hive Temp: " + hive.getTemperatureInfo().getHiveTemp());

		System.out.println("-----------------------------------------------------------");
		System.out.println("-----------------------------------------------------------");
	}
	public static void display2(Hive hive){
		System.out.println("Total Bees: " + hive.getTotalBees() + "\t");
	}
}