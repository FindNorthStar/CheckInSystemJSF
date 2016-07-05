package edu.bupt.checkinsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://10.125.103.123:3306/sunyi";
    private static final String USERNAME = "sunyi";
    private static final String PASSWORD = "sunyi";

    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
