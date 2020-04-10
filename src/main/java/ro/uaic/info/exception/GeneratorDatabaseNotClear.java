package ro.uaic.info.exception;

public class GeneratorDatabaseNotClear extends Exception {
    public GeneratorDatabaseNotClear(){
        super("Clear the database first");
    }
}
