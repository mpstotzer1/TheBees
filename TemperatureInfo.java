//All the temperature-related variables
public class TemperatureInfo {
	private double hiveTemp;
	private int heatTempConst; //some constant to scale heat to normal temperatures
	private int tempFoodConst; //some constant to scale temperature regulation to food cost
	private int bigTempDiff; //significant temperature difference constant
	
	//The constructor
	public TemperatureInfo(double hiveTemp, int heatTempConst, int tempFoodConst, int bigTempDiff){
		this.hiveTemp = hiveTemp;
		this.heatTempConst = heatTempConst;
		this.tempFoodConst = tempFoodConst;
		this.bigTempDiff = bigTempDiff;
	}

	//Useful methods
	public void addHiveTemp(double ht) { hiveTemp += ht; }
	public void subHiveTemp(double ht) { hiveTemp -= ht; }
	public void incHiveTemp(double ht) { hiveTemp *= ht; }
	public void decHiveTemp(double ht) { hiveTemp /= ht; }
	
	//The getters and setters
	public double getHiveTemp() { return hiveTemp; }
	public void setHiveTemp(double hiveTemp) { this.hiveTemp = hiveTemp; }
	public int getHeatTempConst() { return heatTempConst; }
	public void setHeatTempConst(int heatTempConst) { this.heatTempConst = heatTempConst; }
	public int getTempFoodConst() { return tempFoodConst; }
	public void setTempFoodConst(int tempFoodConst) { this.tempFoodConst = tempFoodConst; }
	public int getBigTempDiff() { return bigTempDiff; }
	public void setBigTempDiff(int bigTempDiff) { this.bigTempDiff = bigTempDiff; }
}
