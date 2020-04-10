package ro.uaic.info.data;

import ro.uaic.info.exception.ControllerException;
import ro.uaic.info.exception.DatabaseException;
import ro.uaic.info.sql.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistController {
    public static void create(String name, String country) throws ControllerException{
        try{
            Database.getInstance().connect();

            String createArtistStatement = "insert into artists " +
                    "(name, country) values " +
                    "(?, ?)";

            PreparedStatement statement = Database.getInstance().prepareStatement(createArtistStatement);

            statement.setString(1, name);
            statement.setString(2, country);

            statement.executeUpdate();

            Database.getInstance().disconnect();
        }
        catch(DatabaseException | SQLException e){
            e.printStackTrace();
            if(e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }

    public static Artist findByName(String name) throws ControllerException {
        try{
            Database.getInstance().connect();

            String findArtistsByNameStatement = "select * from artists " +
                    "where name = ?";

            PreparedStatement statement = Database.getInstance().prepareStatement(findArtistsByNameStatement);

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            Artist artist = new Artist(
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("country")
            );

            Database.getInstance().disconnect();

            return artist;
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

            String deleteArtists = "delete from artists";

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
