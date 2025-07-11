package beehive.logger;

public class PrintlnOutput implements OutputDevice{
    @Override
    public void output(String output) {
        System.out.println(output);
    }
}
