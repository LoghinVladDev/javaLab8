package ro.uaic.info.sql;

import ro.uaic.info.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    private Connection connection;

    private Database(){
        connection = null;
    }

    public PreparedStatement prepareStatement(String statement) throws DatabaseException{
        try{
            return this.connection.prepareStatement(statement);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void connect() throws DatabaseException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/music_albums",
                    "dba",
                    "pass"
            );
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public void disconnect() throws DatabaseException {
        try{
            this.connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }
}
