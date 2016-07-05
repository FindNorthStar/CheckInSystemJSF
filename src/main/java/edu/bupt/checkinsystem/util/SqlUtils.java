package edu.bupt.checkinsystem.util;

import edu.bupt.checkinsystem.Config;

import java.sql.*;
import java.util.*;

/**
 * This is the SqlUtils class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/5 11:13
 */
public class SqlUtils {

    public static List<Map<String, Object>> executeSqlQuery(String query) throws Exception {
        Connection connection = Config.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        getHashMap(rows, resultSet);

        connection.close();
        return rows;
    }

    private static void getHashMap(List<Map<String, Object>> row, ResultSet rs_SubItemType) throws SQLException {

        ResultSetMetaData metaData = rs_SubItemType.getMetaData();
        int colCount = metaData.getColumnCount();

        while (rs_SubItemType.next()) {
            Map<String, Object> columns = new HashMap<String, Object>();
            for (int i = 1; i <= colCount; i++) {
                columns.put(metaData.getColumnLabel(i), rs_SubItemType.getObject(i));
            }

            row.add(columns);
        }

    }

}
