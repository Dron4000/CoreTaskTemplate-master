package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {


    private final Connection connection = Util.getConnection();


    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() throws SQLException {

        String createTable = "CREATE TABLE IF NOT EXISTS new_fuck_table (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `age` tinyint NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }

        }
    }


    public void dropUsersTable() throws SQLException {
        String SQLdropUsersTable = "DROP TABLE IF EXISTS new_fuck_table ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLdropUsersTable)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }


        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {


        String SQLsaveUser = "INSERT INTO new_fuck_table (name, lastName, age)VALUES(?,?,?) ";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(SQLsaveUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String SQLremoveUserById = "DELETE FROM new_fuck_table WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLremoveUserById)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }

        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String SQLgetAllUsers = "SELECT * FROM new_fuck_table";
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLgetAllUsers)) {
            connection.setAutoCommit(false);
            resultSet = preparedStatement.executeQuery(SQLgetAllUsers);
            connection.commit();
            while (resultSet.next()) {
                long id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = (byte) resultSet.getInt(4);
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String SQLcleanUsersTable = "DELETE FROM new_fuck_table "; // TRUNCATE TABLE new_fack_table - не смогу откатить.
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLcleanUsersTable)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }

        }

    }

}
