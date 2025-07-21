package beehive.logger;

public class Logger {

    public static void basicLog(String logInfo){
        OutputDevice outputDevice = new PrintlnOutput();
        outputDevice.output(logInfo);
    }

    public static void logTemperatureDebugging(String logInfo){
        OutputDevice outputDevice = new NullOutput();
        outputDevice.output(logInfo);
    }

    public static void productionDebugging(String logInfo){
        OutputDevice outputDevice = new PrintlnOutput();
        outputDevice.output(logInfo);
    }
}
