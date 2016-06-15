package sample.DBConnection;

import javafx.stage.Stage;
import sample.DialogModalWindow.DialogModalWindow;

import java.sql.*;

/**
 * Created by sillybird on 23.05.2016.
 */
public class DBConnection {
    private Connection connection;
    private Stage stage;

    /**
     * @param stage
     */
    public void setStage (Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @throws Exception
     */
    public void createConnection() throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:h2:file:K:/accRoads/" +
                "accRoads-roads-accounting-/src/" +
                "sample/roads", "user", "user");
    }
    /**
     * метод посылает запрос к базе на выборку дороги по имени основной дороги и двум дорогам, которые
     * ее ограничивают, то есть достаётся определенный участок дороги от перекрестка до перекрестка
     * @param first
     * @param second
     * @param third
     * @return
     * @throws Exception
     */
    public ResultSet getInfo (String first, String second, String third) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CROSSING.STATUS, CROSSING.SPENT_MONEY, " +
                "CROSSING.DATE_BEGIN_OF_REPAIRS, CROSSING.DATE_END_OF_REPAIRS, CROSSING.TECHNICAL_CONDITION," +
                " CROSSING.COSMETIC_CONDITION " +
                "FROM ROADS AS a, ROADS AS b, ROADS AS c, CROSSING "+
                "WHERE a.NAME = '"+first+"' and b.NAME = '"+second+"' and c.NAME = '"+third+"' " +
                "and a.ID = CROSSING.F_ID and b.ID = CROSSING.S_ID and c.ID = CROSSING.T_ID;");
        System.out.println("Запрос выполнен");
        return resultSet;
    }

    /**
     * Метод отправляет запрос к базе на обновление информаиции о дороге
     * @param technicalCondition
     * @param cosmeticCondition
     * @param dateBegin
     * @param dateEnd
     * @param status
     * @param spentMoney
     * @param first
     * @param second
     * @param third
     * @throws SQLException
     */
    public int updateInfo (String technicalCondition, String cosmeticCondition, String dateBegin, String dateEnd, String status, String spentMoney, String first, String second, String third) throws SQLException {
        Statement statement = connection.createStatement();
        int countRow = statement.executeUpdate("UPDATE CROSSING " +
                "SET CROSSING.STATUS = '"+status+"', CROSSING.DATE_BEGIN_OF_REPAIRS = CAST('"+dateBegin+"' AS DATETIME), CROSSING.DATE_END_OF_REPAIRS = CAST('"+dateEnd+"' AS DATETIME), CROSSING.TECHNICAL_CONDITION = '"+technicalCondition+"', CROSSING.COSMETIC_CONDITION = '"+cosmeticCondition+"', CROSSING.SPENT_MONEY = "+Integer.parseInt(spentMoney)+" " +
                "WHERE CROSSING.F_ID = (SELECT ID FROM ROADS WHERE ROADS.NAME = '"+first+"') AND CROSSING.S_ID = (SELECT ID FROM ROADS WHERE ROADS.NAME = '"+second+"') AND CROSSING.T_ID = (SELECT ID FROM ROADS WHERE ROADS.NAME = '"+third+"');");
        return countRow;
    }

    public void setInfo () {
        //TODO
    }


    /**
     *
     * @throws Exception
     */
    public void closeConnection () throws Exception{
        connection.close();
    }
}
