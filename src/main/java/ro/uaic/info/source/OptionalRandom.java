package ro.uaic.info.source;


import ro.uaic.info.generation.Generator;

public class OptionalRandom {
    public static void main(String[] args) {
        Generator generator = new Generator.Builder()
                .build();
        try {
            generator.clearDatabase();
            generator.generateArtists();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
