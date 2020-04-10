package ro.uaic.info.source;

import javafx.util.Pair;
import ro.uaic.info.data.*;
import ro.uaic.info.exception.ControllerException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Optional {
    public static void main(String[] args) {
        try {
            AtomicInteger position = new AtomicInteger(1);

            List<Pair<Album, Integer>> chartAlbumList = new ArrayList<>();

            List<Album> albumList = AlbumController.findByArtist(
                    ArtistController.findByName("Queen").getID()
            );

            albumList.forEach(e-> chartAlbumList.
                    add(
                            new Pair<>(e, position.getAndIncrement())
                    )
            );

            albumList = AlbumController.findByArtist(
                    ArtistController.findByName("Iron Maiden").getID()
            );

            albumList.forEach(e-> chartAlbumList.
                    add(
                            new Pair<>(e, position.getAndIncrement())
                    )
            );

            albumList = AlbumController.findByArtist(
                    ArtistController.findByName("Accept").getID()
            );

            albumList.forEach(e-> chartAlbumList.
                    add(
                            new Pair<>(e, position.getAndIncrement())
                    )
            );

            ChartController.clearTable();

            ChartController.create("Chart Example", chartAlbumList);

            System.out.println(chartAlbumList);

            System.out.println(ChartController.findByName("Chart Example"));
        }
        catch (ControllerException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
