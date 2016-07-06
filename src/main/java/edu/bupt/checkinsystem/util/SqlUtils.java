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
    /**
     * 执行 SQL 类 SELECT 型查询
     * @param query SQL 语句
     * @return 数据列
     * @throws Exception
     */
    public static List<Map<String, Object>> executeSqlQuery(String query) throws Exception {
        Connection connection = Config.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        getHashMap(rows, resultSet);

        preparedStatement.close();
        connection.close();
        return rows;
    }

    /**
     * 执行 SQL 类 SELECT 型查询
     * @param query SQL 语句
     * @param args 查询参数, 第一个参数从 1 开始
     * @return 数据列
     * @throws Exception
     */
    public static List<Map<String, Object>> executeSqlQuery(String query, Map<Integer, Object> args) throws Exception {
        Connection connection = Config.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (Map.Entry<Integer, Object> column : args.entrySet()) {
            preparedStatement.setObject(column.getKey(), column.getValue());
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        getHashMap(rows, resultSet);

        preparedStatement.close();
        connection.close();
        return rows;
    }

    /**
     * 执行单行插入/更新
     * @param query SQL 语句
     * @param row 存数据的行, 第一个参数从 1 开始
     * @return 状态, 见 https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#SUCCESS_NO_INFO
     * @throws Exception
     */
    public static int executeSqlUpdate(String query, Map<Integer, Object> row) throws Exception {
        Connection connection = Config.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (Map.Entry<Integer, Object> column : row.entrySet()) {
            preparedStatement.setObject(column.getKey(), column.getValue());
        }
        preparedStatement.addBatch();
        int[] batch = preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
        return batch[0];
    }

    /**
     * 执行多行插入/更新
     * @param query SQL 语句
     * @param rows 存数据的行, Map 内第一个参数从 1 开始
     * @return 状态, 与 List 顺序相同。见 https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#SUCCESS_NO_INFO
     * @throws Exception
     */
    public static int[] executeSqlUpdate(String query, List<Map<Integer, Object>> rows) throws Exception {
        Connection connection = Config.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (Map<Integer, Object> row : rows) {
            for (Map.Entry<Integer, Object> column : row.entrySet()) {
                preparedStatement.setObject(column.getKey(), column.getValue());
            }
            preparedStatement.addBatch();
        }
        int[] batchResult = preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
        return batchResult;
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
