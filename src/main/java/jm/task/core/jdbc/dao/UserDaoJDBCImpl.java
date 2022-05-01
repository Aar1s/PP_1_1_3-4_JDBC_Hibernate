package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UserDaoJDBCImpl implements UserDao {
    private static int userCount = 0;

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection();) {
            Statement statement = connection.createStatement();
            String createTableSQLQuery = "create table Users(ID int, NAME varchar(40), LAST_NAME varchar(40), AGE int)";
            statement.execute(createTableSQLQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }


    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection();) {
            Statement statement = connection.createStatement();
            String dropTableSQLQuery = "drop table Users";
            statement.execute(dropTableSQLQuery);
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        String saveUserQuery;
        PreparedStatement statement;
        try (Connection connection = Util.getMySQLConnection()) {
            userCount++;
            saveUserQuery = "Insert into Users (ID, NAME, LAST_NAME, AGE)" +
                    " values (" + userCount + ", '" + name + "', '" +
                    lastName + "', " + age + ")";
            statement = connection.prepareStatement(saveUserQuery);
            statement.execute(saveUserQuery);
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    public void removeUserById(long id)  {
        try (Connection connection = Util.getMySQLConnection()) {
            String removeUserByIDQuery = "DELETE FROM Users WHERE ID=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIDQuery);
            preparedStatement.execute(removeUserByIDQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    public List<User> getAllUsers()  {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection()){
            Statement statement = connection.createStatement();
            String sql = "Select ID, NAME, LAST_NAME, AGE from Users";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                User user = new User();
                user.setId((long) rs.getInt(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge((byte) rs.getInt(4));
                userList.add(user);
            }
        } catch (ClassNotFoundException | SQLException ignored) {
        }
        return userList;
    }

    public void cleanUsersTable()  {
        Connection connection = null;
        try {
            connection = Util.getMySQLConnection();
            Statement statement = connection.createStatement();
            String truncateQuery = "TRUNCATE TABLE users";
            statement.execute(truncateQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }
}
