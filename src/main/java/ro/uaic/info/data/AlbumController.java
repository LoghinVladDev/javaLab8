package ro.uaic.info.data;

import ro.uaic.info.exception.ControllerException;
import ro.uaic.info.exception.DatabaseException;
import ro.uaic.info.sql.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumController {
    public static void create(String name, int artistID, int releaseYear) throws ControllerException {
        try {
            Database.getInstance().connect();

            String insertAlbumStatement = "insert into albums (name, artist_id, release_year)" +
                    " value" +
                    " (?, ?, ?)";

            PreparedStatement statement = Database.getInstance().prepareStatement(insertAlbumStatement);

            statement.setString(1, name);
            statement.setInt(2, artistID);
            statement.setInt(3, releaseYear);

            statement.executeUpdate();

            Database.getInstance().disconnect();
        }
        catch (DatabaseException | SQLException e){
            e.printStackTrace();
            if(e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQL Exception");
        }
    }

    public static List<Album> findByArtist(int artistID) throws ControllerException{
        List<Album> albumList = new ArrayList<>();

        try{
            Database.getInstance().connect();

            String findArtistsByNameStatement = "select * from albums " +
                    "where artist_id = ?";

            PreparedStatement statement = Database.getInstance().prepareStatement(findArtistsByNameStatement);

            statement.setInt(1, artistID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                albumList.add(
                        new Album(
                                resultSet.getInt("ID"),
                                resultSet.getString("name"),
                                resultSet.getInt("artist_id"),
                                resultSet.getInt("release_year")
                        )
                );
            }

            Database.getInstance().disconnect();

            return albumList;
        }
        catch (DatabaseException | SQLException e){
            e.printStackTrace();
            if(e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }


    public static void clearTable() throws ControllerException {
        try {
            Database.getInstance().connect();

            String deleteArtists = "delete from albums";

            PreparedStatement statement = Database.getInstance().prepareStatement(deleteArtists);

            statement.executeUpdate();

            Database.getInstance().disconnect();
        }
        catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            if (e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }
}
