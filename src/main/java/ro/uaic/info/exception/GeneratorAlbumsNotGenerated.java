package ro.uaic.info.exception;

public class GeneratorAlbumsNotGenerated extends Exception {
    public GeneratorAlbumsNotGenerated(){
        super("Albums not generated. Generate albums first");
    }
}
