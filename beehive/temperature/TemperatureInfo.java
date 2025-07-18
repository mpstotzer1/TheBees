package beehive.temperature;

public class TemperatureInfo {
	private double hiveTemp;
	private TemperatureRegulationRanges temperatureRegulationRanges;
	private double tempRegulationStep;

	//The constructor
	public TemperatureInfo(double tempRegulationStep){
		this.tempRegulationStep = tempRegulationStep;
		this.hiveTemp = 94.0;
		this.temperatureRegulationRanges = TemperatureRegulationRanges.BROOD;
	}

	//Useful methods
	public void changeHiveTemp(double ht) { hiveTemp += ht; }

	//The getters and setters
	public double getHiveTemp() { return hiveTemp; }
	public TemperatureRegulationRanges getTemperatureRegulationRanges() { return temperatureRegulationRanges; }
	public double getTempRegulationStep() { return tempRegulationStep; }
}
