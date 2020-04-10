package ro.uaic.info.generation;

import com.github.javafaker.Faker;
import ro.uaic.info.data.*;
import ro.uaic.info.exception.ControllerException;
import ro.uaic.info.exception.GeneratorArtistsNotGenerated;
import ro.uaic.info.exception.GeneratorDatabaseNotClear;

import java.util.ArrayList;
import java.util.List;

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

        this.artists = new ArrayList<>();

        for(int i = 0; i < this.artistsCount; i++){
            Artist artist = new Artist(this.faker.name().fullName(), this.faker.country().name());
            try {
                ArtistController.create(artist.getName(), artist.getCountry());
                artist.setID(ArtistController.findByName(artist.getName()).getID());
            }
            catch (ControllerException exception){
                //exception.printStackTrace();
                System.out.println("Artist duplicate");
            }
            this.artists.add(artist);
        }

        this.artistsGenerated = true;
    }

    public void generateAlbums() throws GeneratorArtistsNotGenerated {
        if(!this.artistsGenerated){
            throw new GeneratorArtistsNotGenerated();
        }

        this.albums = new ArrayList<>();
/*
        for(int i = 0; i < this.albumsCount; i++){
            Album album = new Album(this.faker.)
        }*/
    }
}
