package ro.uaic.info.data;

import javafx.util.Pair;
import ro.uaic.info.exception.ControllerException;
import ro.uaic.info.exception.DatabaseException;
import ro.uaic.info.sql.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChartController {
    public static void create(String name) throws ControllerException {
        try{
            Database.getInstance().connect();

            String createChartStatement = "insert into charts (name) value (?)";

            PreparedStatement statement = Database.getInstance().prepareStatement(createChartStatement);

            statement.setString(1, name);

            statement.executeUpdate();

            Database.getInstance().disconnect();
        }
        catch (DatabaseException | SQLException e){
            e.printStackTrace();
            if (e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }

    public static void create(String name, List<Pair<Album, Integer>> albumList) throws ControllerException {
        ChartController.create(name);
        ChartController.addToChart(ChartController.findByName(name).getID(), albumList);
    }

    private static void addToChart(int chartID, List<Pair<Album, Integer>> albumList) throws ControllerException{
        try{
            Database.getInstance().connect();

            StringBuilder insertIntoChartPositionsStatement = new StringBuilder().append(
                "INSERT INTO chart_positions (chart_ID, album_ID, position) VALUES "
            );

            albumList.forEach(e ->
                        insertIntoChartPositionsStatement
                                .append("( ?, ?, ?),")
            );

            insertIntoChartPositionsStatement.deleteCharAt(insertIntoChartPositionsStatement.lastIndexOf(","));
            PreparedStatement statement = Database.getInstance().prepareStatement(insertIntoChartPositionsStatement.toString());

            AtomicInteger paramIndex = new AtomicInteger(1);

            albumList.forEach(e-> {
                        try {
                            statement.setInt(paramIndex.getAndIncrement(), chartID);
                            statement.setInt(paramIndex.getAndIncrement(), e.getKey().getID());
                            statement.setInt(paramIndex.getAndIncrement(), e.getValue());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
            );

            statement.executeUpdate();

            Database.getInstance().disconnect();
        }
        catch (DatabaseException | SQLException e){
            e.printStackTrace();
            if (e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }

    public static Chart findByName(String name) throws ControllerException{
        try{
            Database.getInstance().connect();

            String getChartStatement = "select * from charts where name = ?";

            PreparedStatement statement = Database.getInstance().prepareStatement(getChartStatement);

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            Chart chart = new Chart(
                resultSet.getInt("ID"),
                resultSet.getString("name")
            );

            chart.setAlbumList(AlbumController.findByChart(chart.getID()));

            Database.getInstance().disconnect();

            return chart;
        }
        catch (DatabaseException | SQLException e){
            e.printStackTrace();
            if (e.getClass().getName().contains("DatabaseException"))
                throw new ControllerException("Database Connection Failed");
            else
                throw new ControllerException("SQLException");
        }
    }

    public static void clearTable() throws ControllerException{
        try {
            Database.getInstance().connect();

            String deleteCharts = "delete from charts";
            String deleteChartPositions = "delete from chart_positions";

            PreparedStatement statement = Database.getInstance().prepareStatement(deleteCharts);

            statement.executeUpdate();

            statement = Database.getInstance().prepareStatement(deleteChartPositions);

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
