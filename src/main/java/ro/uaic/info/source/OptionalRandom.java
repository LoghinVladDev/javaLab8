package ro.uaic.info.source;


import org.w3c.dom.ls.LSOutput;
import ro.uaic.info.data.Chart;
import ro.uaic.info.data.ChartController;
import ro.uaic.info.generation.Generator;

import java.util.List;

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


        try {
            List<Chart> charts = ChartController.getAllCharts();
            charts.forEach(System.out::println);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
