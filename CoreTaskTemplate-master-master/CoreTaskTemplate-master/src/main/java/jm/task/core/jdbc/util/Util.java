package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  final class Util {
    // реализуйте настройку соеденения с БД

    static private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mysql?autoReconnect=true&useSSL=false";
    static private final String PASSWORD = "rfrfzrhfcjnf93";
    static private final String USER_NAME = "root";


    static public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);



            if (connection.isClosed()) {
                System.out.println("соединение нет");
            }
        } catch (SQLException e) {
            System.out.println("Что пошло не так, соединения нет и  есть исключение (");
            e.printStackTrace();

        }
        System.out.println("соединение есть");
        return connection;
    }
}
