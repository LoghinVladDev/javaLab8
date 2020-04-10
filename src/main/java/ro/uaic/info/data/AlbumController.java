package ro.uaic.info.data;

import javafx.util.Pair;
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

    public static List<Pair<Album, Integer>> findByChart(int chartID) throws ControllerException{
        List<Pair<Album, Integer>> albumList = new ArrayList<>();

        try{
            Database.getInstance().connect();

            String selectAlbumIDFromChartPositionsStatement = "SELECT album_ID, position FROM chart_positions WHERE chart_ID = ? order by position asc";

            PreparedStatement statement = Database.getInstance().prepareStatement(selectAlbumIDFromChartPositionsStatement);

            statement.setInt(1, chartID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                albumList.add(
                        new Pair<>(
                                AlbumController.findByID(resultSet.getInt("album_ID")),
                                resultSet.getInt("position")
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

    public static List<Album> findByArtist(int artistID) throws ControllerException{
        List<Album> albumList = new ArrayList<>();

        try{
            Database.getInstance().connect();

            String findAlbumsByNameStatement = "select * from albums " +
                    "where artist_id = ?";

            PreparedStatement statement = Database.getInstance().prepareStatement(findAlbumsByNameStatement);

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

    public static Album findByID(int ID) throws ControllerException{
        try{
            Database.getInstance().connect();

            String findAlbumByIDStatement = "select * from albums " +
                    "where ID = ?";

            PreparedStatement statement = Database.getInstance().prepareStatement(findAlbumByIDStatement);

            statement.setInt(1, ID);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            Album album = new Album(
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getInt("artist_id"),
                    resultSet.getInt("release_year")
            );

            Database.getInstance().disconnect();

            return album;
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
