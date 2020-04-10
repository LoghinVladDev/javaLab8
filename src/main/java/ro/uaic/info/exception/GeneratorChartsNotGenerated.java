package ro.uaic.info.exception;

public class GeneratorChartsNotGenerated extends Exception{
    public GeneratorChartsNotGenerated(){
        super("Charts not generated. Generate charts first");
    }
}
