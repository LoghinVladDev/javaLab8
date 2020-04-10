package ro.uaic.info.source;

import ro.uaic.info.data.AlbumController;
import ro.uaic.info.data.ArtistController;
import ro.uaic.info.exception.ControllerException;

public class Compulsory {
    public static void main(String[] args) {
        try {

            AlbumController.clearTable();
            ArtistController.clearTable();

            ArtistController.create("Iron Maiden", "United Kingdom");
            ArtistController.create("Queen", "United Kingdom");
            ArtistController.create("Accept", "Germany");

            String[] albums = {"Fear of the Dark", "Piece of Mind", "Powerslave"};
            int[] years     = {1992, 1983, 1984};

            for(int i = 0; i < albums.length; i++){
                AlbumController.create(
                        albums[i],
                        ArtistController.findByName("Iron Maiden").getID(),
                        years[i]
                );
            }

            albums = new String[]{"A Night at the Opera", "News of the World"};
            years  = new int[]{1975, 1977};

            for(int i = 0; i < albums.length; i++){
                AlbumController.create(
                        albums[i],
                        ArtistController.findByName("Queen").getID(),
                        years[i]
                );
            }

            albums = new String[]{"Blood of the Nations", "Stalingrad"};
            years  = new int[]{2010, 2012};

            for(int i = 0; i < albums.length; i++){
                AlbumController.create(
                        albums[i],
                        ArtistController.findByName("Accept").getID(),
                        years[i]
                );
            }

            System.out.println(
                    AlbumController.findByArtist(
                            ArtistController
                                    .findByName("Iron Maiden")
                                    .getID()
                    )
            );

            System.out.println(
                    AlbumController.findByArtist(
                            ArtistController
                                    .findByName("Queen")
                                    .getID()
                    )
            );

            System.out.println(
                    AlbumController.findByArtist(
                            ArtistController
                                    .findByName("Accept")
                                    .getID()
                    )
            );
        }
        catch (ControllerException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
