package sample.DBConnection;

import java.sql.*;

/**
 * Created by sillybird on 23.05.2016.
 */
public class DBConnection {
    private Connection connection;

    /**
     *
     * @throws Exception
     */
    public DBConnection () throws Exception {
        Class.forName("org.h2.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:h2:file:K:/accRoads/accRoads-roads-accounting-/src/sample/roads", "user", "user");
    }

    /**
     *
     * @param first
     * @param second
     * @param third
     * @return
     * @throws Exception
     */
    public ResultSet sendRequest (String first, String second, String third) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CROSSING.STATUS, CROSSING.SPENT_MONEY, CROSSING.DATE_BEGIN_OF_REPAIRS, CROSSING.DATE_END_OF_REPAIRS, CROSSING.TECHNICAL_CONDITION, CROSSING.COSMETIC_CONDITION " +
                "FROM ROADS AS a, ROADS AS b, ROADS AS c, CROSSING "+
                "WHERE a.NAME = '"+first+"' and b.NAME = '"+second+"' and c.NAME = '"+third+"' and a.ID = CROSSING.F_ID and b.ID = CROSSING.S_ID and c.ID = CROSSING.T_ID;");
        return resultSet;
    }

    /**
     *
     * @throws Exception
     */
    public void closeConnection () throws Exception{
        connection.close();
    }
}
