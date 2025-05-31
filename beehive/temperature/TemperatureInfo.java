package beehive.temperature;

//All the temperature-related variables
public class TemperatureInfo {
	private double hiveTemp;
	private double heatTempConst; //constant to scale heat to normal temperatures
	private double tempFoodConst; //constant to scale temperature regulation to food cost
	private TemperatureRegulationRanges temperatureRegulationRanges;
	private double tempRegulationStep;
	private double tempRegulationFoodCostConstant;
	
	//The constructor
	public TemperatureInfo(double hiveTemp, double heatTempConst, double tempFoodConst, double tempRegulationStep, double tempRegulationFoodCostConstant){
		this.hiveTemp = hiveTemp;
		this.heatTempConst = heatTempConst;
		this.tempFoodConst = tempFoodConst;
		this.temperatureRegulationRanges = TemperatureRegulationRanges.BROOD;
		this.tempRegulationStep = tempRegulationStep;
		this.tempRegulationFoodCostConstant = tempRegulationFoodCostConstant;
	}

	//Useful methods
	public void changeHiveTemp(double ht) { hiveTemp += ht; }
	public void subHiveTemp(double ht) { hiveTemp -= ht; }
	public void incHiveTemp(double ht) { hiveTemp *= ht; }
	public void decHiveTemp(double ht) { hiveTemp /= ht; }

//	public double minTempRegulation(){
//		switch(temperatureRegulationRanges){
//			case BROOD:
//				return 91.0;
//			case GENERAL:
//				return 57.0;
//			case OFF:
//				return -1000.0;
//		}
//		return -1000.0;
//	}
//	public double maxTempRegulation(){
//		switch(temperatureRegulationRanges){
//			case BROOD:
//				return 97.0;
//			case GENERAL:
//				return 113.0;
//			case OFF:
//				return 1000.0;
//		}
//		return 1000.0;
//	}
	
	//The getters and setters
	public double getHiveTemp() { return hiveTemp; }
	public void setHiveTemp(double hiveTemp) { this.hiveTemp = hiveTemp; }
	public double getHeatTempConst() { return heatTempConst; }
	public void setHeatTempConst(double heatTempConst) { this.heatTempConst = heatTempConst; }
	public double getTempFoodConst() { return tempFoodConst; }
	public void setTempFoodConst(double tempFoodConst) { this.tempFoodConst = tempFoodConst; }
	public TemperatureRegulationRanges getTemperatureRegulationRanges() { return temperatureRegulationRanges; }
	public double getTempRegulationStep() { return tempRegulationStep; }
	public double getTempRegulationFoodCostConstant() { return tempRegulationFoodCostConstant; }

}
