package ro.uaic.info.exception;

public class GeneratorArtistsNotGenerated extends Exception {
    public GeneratorArtistsNotGenerated(){
        super("Artists not generated. Generate Artists First");
    }
}
