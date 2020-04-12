package ro.uaic.info.generation;

import com.github.javafaker.Faker;
import ro.uaic.info.data.*;
import ro.uaic.info.exception.ControllerException;
import ro.uaic.info.exception.GeneratorAlbumsNotGenerated;
import ro.uaic.info.exception.GeneratorArtistsNotGenerated;
import ro.uaic.info.exception.GeneratorDatabaseNotClear;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Generator {
    private static final int DEFAULT_ALBUMS_COUNT = 1000;
    private static final int DEFAULT_CHARTS_COUNT = 50;
    private static final int DEFAULT_MAX_ON_CHART = 100;
    private static final int DEFAULT_MIN_ON_CHART = 50;
    private static final int DEFAULT_ARTISTS_COUNT = 500;

    private int albumsCount;
    private int chartsCount;
    private int maxOnChart;
    private int minOnChart;
    private int artistsCount;

    private boolean artistsGenerated;
    private boolean albumsGenerated;
    private boolean chartsGenerated;
    private boolean databaseClear;

    private Faker faker;

    private List<Artist> artists;
    private List<Album> albums;
    private List<Chart> charts;

    public static class Builder{
        private int albumsCount = Generator.DEFAULT_ALBUMS_COUNT;
        private int chartsCount = Generator.DEFAULT_CHARTS_COUNT;
        private int maxOnChart = Generator.DEFAULT_MAX_ON_CHART;
        private int minOnChart = Generator.DEFAULT_MIN_ON_CHART;
        private int artistsCount = Generator.DEFAULT_ARTISTS_COUNT;

        public Builder withAlbumsCount(int albumsCount){
            this.albumsCount = albumsCount;
            return this;
        }

        public Builder withChartsCount(int chartsCount){
            this.chartsCount = chartsCount;
            return this;
        }

        public Builder withMaxOnChart(int maxOnChart){
            this.maxOnChart = maxOnChart;
            return this;
        }

        public Builder withMinOnChart(int minOnChart){
            this.minOnChart = minOnChart;
            return this;
        }

        public Builder withArtistsCount(int artistsCount){
            this.artistsCount = artistsCount;
            return this;
        }

        public Generator build(){
            Generator generator = new Generator();

            generator.albumsCount = this.albumsCount;
            generator.artistsCount = this.artistsCount;
            generator.chartsCount = this.chartsCount;
            generator.maxOnChart = this.maxOnChart;
            generator.minOnChart = this.minOnChart;

            return generator;
        }
    }

    private Generator(){
        this.albumsGenerated = false;
        this.artistsGenerated = false;
        this.chartsGenerated = false;
        this.databaseClear = false;
        this.faker = new Faker();
    }

    public void clearDatabase() throws ControllerException {
        ChartController.clearTable();
        ArtistController.clearTable();
        AlbumController.clearTable();

        this.databaseClear = true;
    }

    public void generateArtists() throws GeneratorDatabaseNotClear {
        if(!this.databaseClear){
            throw new GeneratorDatabaseNotClear();
        }

        System.out.println("GENERATING ARTISTS : \n");

        this.artists = new ArrayList<>();

        for(int i = 0; i < this.artistsCount; i++){
            System.out.println("\tArtist " + (i+1) + " out of " + this.artistsCount);
            Artist artist = new Artist(this.faker.name().fullName(), this.faker.country().name());
            try {
                ArtistController.create(artist.getName(), artist.getCountry());
                artist.setID(ArtistController.findByName(artist.getName()).getID());
            }
            catch (ControllerException exception){
                //exception.printStackTrace();
                System.out.println("Artist duplicate");
                this.artistsCount--;
                i--;
                continue;
            }
            this.artists.add(artist);
        }

        this.artistsGenerated = true;
    }

    public void generateAlbums() throws GeneratorArtistsNotGenerated {
        if(!this.artistsGenerated){
            throw new GeneratorArtistsNotGenerated();
        }

        System.out.println("GENERATING ALBUMS : \n");

        this.albums = new ArrayList<>();

        for(int i = 0; i < this.albumsCount; i++){
            System.out.println("\tAlbum " + (i+1) + " out of " + this.albumsCount);
            Album album = new Album(
                    this.faker.book().title() + " " + this.faker.name().firstName() + ", " + this.faker.music().genre(),
                    this.artists.get((int)(Math.random()*this.artists.size())).getID(),
                    (int)(Math.random()*60) + 1960
                );

            try {
                AlbumController.create(album.getName(), album.getArtistID(), album.getReleaseYear());
                album.setID(AlbumController.findByName(album.getName()).getID());
            }
            catch(ControllerException exception){
                System.out.println("Album duplicate");
                this.albumsCount--;
                i--;
                continue;
            }
            this.albums.add(album);
        }
        this.albumsGenerated = true;
    }

    public void generateCharts() throws GeneratorAlbumsNotGenerated{
        if(!this.albumsGenerated)
            throw new GeneratorAlbumsNotGenerated();

        System.out.println("GENERATING CHARTS : \n");

        this.charts = new ArrayList<>();

        for(int i = 0; i < this.chartsCount; i++){
            System.out.println("\tChart " + (i+1) + " out of " + this.chartsCount);

            int onChart = (int)(Math.random()*(this.maxOnChart - this.minOnChart + 1) + this.minOnChart);

            Chart chart = new Chart(
                    this.faker.name().fullName() + "'s Top " + onChart + " " + this.faker.music().genre()
            );

            Set<Album> inTop = new HashSet<>();

            for(int j = 1, whichAlbum = (int)(Math.random() * this.albumsCount); j <= onChart; j++){
                while(inTop.contains(this.albums.get(whichAlbum))){
                    whichAlbum = (int)(Math.random() * this.albumsCount);
                }

                inTop.add(this.albums.get(whichAlbum));
                chart.addAlbum(this.albums.get(whichAlbum), j);
            }

            try{
                ChartController.create(chart.getName(), chart.getAlbumList());
                chart.setID(ChartController.findByName(chart.getName()).getID());
            }
            catch(ControllerException exception){
                System.out.println("Chart duplicate");
            }

            this.charts.add(chart);
        }

        this.chartsGenerated = true;
    }
}
