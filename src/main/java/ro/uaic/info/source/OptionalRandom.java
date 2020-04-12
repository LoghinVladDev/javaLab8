package ro.uaic.info.source;


import ro.uaic.info.generation.Generator;

public class OptionalRandom {
    public static void main(String[] args) {

        /*
         * Warning! takes around 20 minutes !!! Unoptimised
         *
         * Change count limits for faster runtime
         */
        Generator generator = new Generator.Builder()
                .withArtistsCount(1000)
                .withAlbumsCount(10000)
                .withChartsCount(500)
                .build();
        try {
            generator.clearDatabase();
            generator.generateArtists();
            generator.generateAlbums();
            generator.generateCharts();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
